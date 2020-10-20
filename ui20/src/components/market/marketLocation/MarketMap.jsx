import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {Map, TileLayer} from 'react-leaflet';
import {connect} from "react-redux"
import MarketLocationMapDTO from "../../../modules/graphic/market/map/MarketLocationMapDTO"
import CountryBorderLayer from "../../common/map/CountryBorderLayer"
import {SENEGAL_CENTER_LAT_LNG, SENEGAL_ZOOM_LEVEL} from "../../common/map/MapUtils"
import MarketLayer from "./MarketLayer"
import "./marketMap.scss"
import MarketMapLegend from "./MarketMapLegend"

class MarketMap extends Component {
  static propTypes = {
    marketLocationsDTO: PropTypes.instanceOf(MarketLocationMapDTO).isRequired,
    topoMapAttribution: PropTypes.string.isRequired,
  }

  render() {
    const {topoMapAttribution} = this.props

    return (
      <div className="png exportable">
        <MarketMapLegend {...this.props} />
        <div className="map-container">
          <Map className="map black-tooltip" zoom={SENEGAL_ZOOM_LEVEL} center={SENEGAL_CENTER_LAT_LNG} zoomControl={true}>
            <TileLayer
              url="https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}"
              attribution={topoMapAttribution}/>
            <CountryBorderLayer/>
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
    topoMapAttribution: state.getIn(['app', 'data', 'mapAttribution', 'topo']),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(MarketMap))
