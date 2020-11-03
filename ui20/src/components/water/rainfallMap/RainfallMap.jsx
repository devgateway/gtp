import React, {Component} from "react"
import * as PropTypes from "prop-types"
import {FormattedMessage} from "react-intl"
import {GeoJSON, Map} from "react-leaflet"
import "../../market/marketLocation/marketMap.scss"
import CountryBorderLayer from "../../common/map/CountryBorderLayer"

const regionGeoJson = require('../../../json/regions.json')

export class RainfallMap extends Component {
  static propTypes = {
    titleId: PropTypes.string.isRequired,
    polyline: PropTypes.object.isRequired,
    polygon: PropTypes.object.isRequired,
    onEachPolygonFeature: PropTypes.func.isRequired,
    polygonFeatureStyle: PropTypes.func.isRequired,
    polylineFeatureStyle: PropTypes.func.isRequired,
  }

  render() {
    const {titleId, polyline, polygon, onEachPolygonFeature, polygonFeatureStyle, polylineFeatureStyle} = this.props


    return (
      <div className="png exportable">
        <div className="map-title"><FormattedMessage id={titleId}/></div>
        <div className="map-container">
          <Map className="map"
               center={[14.4974, -14.4545887]}
               dragging={false}
               zoom={6.5}
               zoomControl={false}
               zoomDelta={0.5}
               zoomSnap={0.5}
               scrollWheelZoom={false}>
            <GeoJSON data={polygon} style={polygonFeatureStyle} onEachFeature={onEachPolygonFeature}/>
            <GeoJSON data={polyline} style={polylineFeatureStyle}/>
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

            {this.props.children}
          </Map>
        </div>
      </div>
    )
  }

}
