import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {CircleMarker, Tooltip} from "react-leaflet"
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
        const marketDays = m.marketDays
        const tooltip = `Marché ${m.name}. Département de ${m.department.name}. Jours de marchés: ${marketDays}`
        const color = MarketUtils.getMarketTypeColor(m.type.name)

        return (
        <CircleMarker
        key={m.id}
        center={[m.latitude, m.longitude]}
        color={color}
        fillOpacity={0.5}
        radius={5} >
          <Tooltip>
            <div className="tooltips white">
              <span className="color" style={{backgroundColor: color}}/>
              <span className="label">{tooltip}</span>
            </div>
          </Tooltip>
        </CircleMarker>
        )
      })}
    </div>
    );
  }
}
