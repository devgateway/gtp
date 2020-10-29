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
    rainFeatureStyle: PropTypes.func.isRequired,
  }

  render() {
    const {titleId, polyline, polygon, rainFeatureStyle} = this.props


    return (
      <div className="png exportable">
        <div className="map-title"><FormattedMessage id={titleId}/></div>
        <div className="map-container">
          <Map className="map" zoom={6.5} center={[14.4974, -14.4545887]} zoomControl={true} zoomDelta={0.5} zoomSnap={0.5}>
            <GeoJSON data={polygon} style={rainFeatureStyle}/>
            <GeoJSON data={polyline} style={rainFeatureStyle}/>
            <GeoJSON data={regionGeoJson} style={{
              color: "#7f7f7f",
              fillOpacity: 0,
              weight: 1
            }} />
            <CountryBorderLayer style={{color: "#4e4e4e"}}/>
          </Map>
        </div>
      </div>
    )
  }

}
