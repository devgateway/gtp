import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {Map, TileLayer, ZoomControl} from 'react-leaflet';
import {connect} from "react-redux"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import MarketLayer from "./MarketLayer"
import "./marketMap.scss"

class MarketMap extends Component {
  static propTypes = {
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
  }

  render() {
    return (
      <div className="png exportable">
        <div className="map-container">
          <Map className="map" zoom={7} center={[14.4974, -14.4545887]} zoomControl={false}>
            <ZoomControl position="topright" />
            <TileLayer
              url="https://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}"
              attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors" />
            <MarketLayer {...this.props} />
          </Map>
        </div>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    agricultureConfig: state.getIn(['agriculture', 'data', 'agricultureConfig']),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(MarketMap))
