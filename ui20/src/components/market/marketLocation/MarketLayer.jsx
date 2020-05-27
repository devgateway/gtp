import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {CircleMarker} from "react-leaflet"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import Market from "../../../modules/entities/market/Market"
import * as MarketUtils from "../MarketUtils"

export default class MarketLayer extends Component {
  static propTypes = {
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
  }

  render() {
    return (
    <div>
      {this.props.agricultureConfig.markets.map((m: Market) => {
        return (
        <CircleMarker
        key={m.id}
        center={[m.latitude, m.longitude]}
        color={MarketUtils.getMarketTypeColor(m.type.name)}
        fillOpacity={0.5}
        radius={5} />
        )
      })}
    </div>
    );
  }
}
