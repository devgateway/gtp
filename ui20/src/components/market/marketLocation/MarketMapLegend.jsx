import * as PropTypes from "prop-types"
import React, {Component} from "react"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import Market from "../../../modules/entities/market/Market"
import MarketType from "../../../modules/entities/market/MarketType"
import {LEGEND_SYMBOL_CIRCLE} from "../../common/legend/CustomLegendSymbol"
import CustomLegendItem from "../../common/legend/CustomLegend"
import * as MarketUtils from "../MarketUtils"

export default class MarketMapLegend extends Component {
  static propTypes = {
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
  }

  render() {
    const {markets} = this.props.agricultureConfig
    const usedMarketTypes = Array.from(markets.values()).reduce((set: Set, m: Market) => set.add(m.type), new Set())

    return (
      <div className="legend">

        {Array.from(usedMarketTypes).map((mt: MarketType) =>
          (<CustomLegendItem key={mt.name} type={LEGEND_SYMBOL_CIRCLE} label={mt.label} legendProps={{
            x: 0,
            y: 0,
            size: 12,
            fill: MarketUtils.getMarketTypeColor(mt.name)
          }}/>))}

      </div>)
  }
}
