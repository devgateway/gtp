import * as PropTypes from "prop-types"
import React, {Component} from "react"
import MarketType from "../../../modules/entities/market/MarketType"
import MarketDTO from "../../../modules/graphic/market/map/MarketDTO"
import MarketLocationMapDTO from "../../../modules/graphic/market/map/MarketLocationMapDTO"
import CustomLegend from "../../common/legend/CustomLegend"
import CustomLegendItem from "../../common/legend/CustomLegendItem"
import {LEGEND_SYMBOL_CIRCLE} from "../../common/legend/CustomLegendSymbol"
import * as MarketUtils from "../MarketUtils"

export default class MarketMapLegend extends Component {
  static propTypes = {
    marketLocationsDTO: PropTypes.instanceOf(MarketLocationMapDTO).isRequired,
  }

  render() {
    const {markets} = this.props.marketLocationsDTO
    const usedMarketTypes = Array.from(markets.values()).reduce((set: Set, m: MarketDTO) => set.add(m.type), new Set())

    return (
      <CustomLegend>

        {Array.from(usedMarketTypes).map((mt: MarketType) =>
          (<CustomLegendItem
            key={mt.name}
            type={LEGEND_SYMBOL_CIRCLE}
            label={mt.label}
            x={0}
            y={0}
            size={12}
            fill={MarketUtils.getMarketTypeColor(mt.name)}/>))}

      </CustomLegend>)
  }
}
