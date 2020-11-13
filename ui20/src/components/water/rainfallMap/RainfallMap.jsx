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
             zoom={12}
             bounds={
               [[12.306804, -17.532738],
                 [16.693480, -11.345555]]
             }
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
