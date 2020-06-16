import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
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
    const attribution = intl.formatMessage({ id: "indicators.map.market.source" })
    const workingDaysLabel = intl.formatMessage({ id: "indicators.map.market.tooltip.workingDays" })

    return (
    <div>
      {this.props.agricultureConfig.markets.map((m: Market) => {
        let marketDays = [...m.marketDays].map((v, idx) => v === '1' ? weekDaysTrn[idx] : null).filter(v => v)
        marketDays = marketDays.length === 7 ? permanentText : marketDays.join(' / ')
        const color = MarketUtils.getMarketTypeColor(m.type.name)

        return (
        <CircleMarker
        key={m.id}
        attribution={attribution}
        center={[m.latitude, m.longitude]}
        color={color}
        fillOpacity={1}
        radius={5} >
          <Tooltip className="black">
            <div className="tooltips black">
              <div className="market">
                <FormattedMessage id="indicators.map.market.tooltip.market" values={{
                  market: m.name
                }}/>
              </div>
              <div className="ui divider" />
              <div className="department">
                <FormattedMessage id="indicators.map.market.tooltip.departement" values={{
                  department: m.department.name
                }}/>
              </div>
              <div className="ui divider" />
              <div className="workDays">
                <div className="title">{workingDaysLabel}</div>
                <div>{marketDays}</div>
              </div>
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
