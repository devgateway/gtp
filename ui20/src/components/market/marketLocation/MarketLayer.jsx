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
    const {intl} = this.props
    const weekDaysTrn = weekDays.map(id => intl.formatMessage({ id }))
    const permanentText = intl.formatMessage({ id: "indicators.map.market.permanent" })

    return (
    <div>
      {this.props.agricultureConfig.markets.map((m: Market) => {
        let marketDays = [...m.marketDays].map((v, idx) => v === '1' ? weekDaysTrn[idx] : null).filter(v => v)
        marketDays = marketDays.length === 7 ? permanentText : marketDays.join(', ')
        const tooltip = intl.formatMessage({ id: "indicators.map.market.tooltip" }, {
          market: m.name,
          department: m.department.name,
          workingDays: marketDays
        })
        const color = MarketUtils.getMarketTypeColor(m.type.name)

        return (
        <CircleMarker
        key={m.id}
        center={[m.latitude, m.longitude]}
        color={color}
        fillOpacity={1}
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

const weekDays = [1,2,3,4,5,6,7].map(d => `all.weekdays.${d}`)
