import React, {Component} from "react"
import * as PropTypes from "prop-types"
import {FormattedMessage, injectIntl} from "react-intl"
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
    const {titleId, polyline, polygon, onEachPolygonFeature, onEachPolylineFeature, polygonFeatureStyle,
      polylineFeatureStyle, showPluviometricPosts, isAllWaterLoaded, getPostLocation, intl} = this.props
    const showPosts = showPluviometricPosts && isAllWaterLoaded

    return (
      <div className="png exportable">
        <div className="map-title"><FormattedMessage id={titleId}/></div>
        <div className="map-container">
          <Map className="map black-tooltip"
               center={[14.4974, -14.4545887]}
               dragging={false}
               zoom={6.6}
               zoomControl={false}
               zoomDelta={0.2}
               zoomSnap={0.2}
               scrollWheelZoom={false}>
            <GeoJSON data={polygon} style={polygonFeatureStyle} onEachFeature={onEachPolygonFeature}/>
            <GeoJSON data={polyline} style={polylineFeatureStyle} onEachFeature={onEachPolylineFeature}/>
            <GeoJSON data={regionGeoJson} style={{
              color: "#7f7f7f",
              fill: false,
              fillOpacity: 0,
              weight: 1
            }} />
            <CountryBorderLayer style={{
              color: "#4e4e4e",
              fill: false,
            }}/>

            {showPosts && <PluviometricPostLayer
              postMapDTO={getPostLocation().postMapDTO}
              circleColor="#364833"
              circleRadius={1}
              intl={intl} />}

            {this.props.children}
          </Map>
        </div>
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
