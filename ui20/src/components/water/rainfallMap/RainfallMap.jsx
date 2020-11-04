import React, {Component} from "react"
import * as PropTypes from "prop-types"
import {injectIntl} from "react-intl"
import {GeoJSON, Map} from "react-leaflet"
import "../../common/map/map.scss"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import CountryBorderLayer from "../../common/map/CountryBorderLayer"
import PluviometricPostLayer from "../postLocation/PluviometricPostLayer"

const regionGeoJson = require('../../../json/regions.json')

class RainfallMap extends Component {
  static propTypes = {
    titleId: PropTypes.string.isRequired,
    polyline: PropTypes.object.isRequired,
    polygon: PropTypes.object.isRequired,
    onEachPolygonFeature: PropTypes.func.isRequired,
    onEachPolylineFeature: PropTypes.func.isRequired,
    polygonFeatureStyle: PropTypes.func.isRequired,
    polylineFeatureStyle: PropTypes.func.isRequired,
    showPluviometricPosts: PropTypes.bool.isRequired,
    isAllWaterLoaded: PropTypes.bool.isRequired,
    getPostLocation: PropTypes.func.isRequired,
  }

  render() {
    const {
      polyline, polygon, onEachPolygonFeature, onEachPolylineFeature, polygonFeatureStyle, polylineFeatureStyle,
      showPluviometricPosts, isAllWaterLoaded, getPostLocation, intl
    } = this.props
    const showPosts = showPluviometricPosts && isAllWaterLoaded

    return (
      <div className="map-container">
        <Map className="map black-tooltip"
             center={[14.4974, -14.4545887]}
             dragging={false}
             keyboard={false}
             zoom={getZoomLevel()}
             zoomControl={false}
             zoomDelta={0.1}
             zoomSnap={0.1}
             touchZoom={false}
             boxZoom={false}
             tap={false}
             doubleClickZoom={false}
             scrollWheelZoom={false}>
          <GeoJSON data={polygon} style={polygonFeatureStyle} onEachFeature={onEachPolygonFeature}/>
          <GeoJSON data={polyline} style={polylineFeatureStyle} onEachFeature={onEachPolylineFeature}/>
          <GeoJSON data={regionGeoJson} style={{
            color: "#7f7f7f",
            fill: false,
            fillOpacity: 0,
            weight: 1
          }}/>
          <CountryBorderLayer style={{
            color: "#4e4e4e",
            fill: false,
          }}/>

          {showPosts && <PluviometricPostLayer
            postMapDTO={getPostLocation().postMapDTO}
            circleColor="#364833"
            circleRadius={1}
            intl={intl}/>}

          {this.props.children}
        </Map>
      </div>
    )
  }
}

const ZOOM_BY_HEIGHT = [[600, 6], [650, 6.1], [700, 6.2], [750, 6.3], [800, 6.4], [850, 6.5]]
const LARGE_SCREEN_ZOOM = [900, 6.6]
const getZoomLevel = () => {
  const zoomByHeight = ZOOM_BY_HEIGHT.find(([res, ]) => window.innerHeight < res) || LARGE_SCREEN_ZOOM
  return zoomByHeight[1]
}


const mapStateToProps = state => {
  return {
    isAllWaterLoaded: state.getIn(['water', 'isLoaded']),
    showPluviometricPosts: state.getIn(['water', 'data', 'rainMap', 'setting', 'showPluviometricPosts']),
  }
}

const mapActionCreators = {
  getPostLocation: waterActions.getPostLocation
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallMap))
