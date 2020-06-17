import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {Map, TileLayer, ZoomControl} from 'react-leaflet';
import {connect} from "react-redux"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import MarketLayer from "./MarketLayer"
import "./marketMap.scss"
import MarketMapLegend from "./MarketMapLegend"

class MarketMap extends Component {
  static propTypes = {
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
    worldMapAttribution: PropTypes.string.isRequired,
  }

  render() {
    const {worldMapAttribution} = this.props

    return (
      <div className="png exportable">
        <MarketMapLegend {...this.props} />
        <div className="map-container">
          <Map className="map" zoom={7} center={[14.4974, -14.4545887]} zoomControl={false} preferCanvas>
            <ZoomControl position="topright" />
            <TileLayer
              url="https://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}"
              attribution={worldMapAttribution} />
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
    worldMapAttribution: state.getIn(['app', 'data', 'worldMapAttribution']),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(MarketMap))
