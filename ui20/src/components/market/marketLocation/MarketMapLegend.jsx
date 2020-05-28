import {ThemeProvider} from '@nivo/core'
import {BoxLegendSvg} from "@nivo/legends"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import Market from "../../../modules/entities/market/Market"
import MarketType from "../../../modules/entities/market/MarketType"
import * as MarketUtils from "../MarketUtils"

class MarketMapLegend extends Component {
  static propTypes = {
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
  }

  render() {
    const {markets} = this.props.agricultureConfig
    const usedMarketTypes = Array.from(markets.values()).reduce((set: Set, m: Market) => set.add(m.type), new Set())

    return (
      <React.Fragment>
        <BoxLegendSvg
          data={Array.from(usedMarketTypes).map((mt: MarketType) => ({
            color: MarketUtils.getMarketTypeColor(mt.name),
            id: mt.id,
            label: mt.label
          }))}
          anchor='top-right'
          direction='row'
          justify={false}
          itemsSpacing={2}
          itemWidth={140}
          itemHeight={20}
          itemOpacity={0.75}
          symbolShape='circle'
          symbolSize={12}
          effects={[
            {
              on: 'hover',
              style: {
                itemOpacity: 1
              }
            }
          ]}
        />
      </React.Fragment>
    )
  }

}

export default (props) => {
  return (
    <div>
      <svg className="legend" viewBox="0 0 1266 20">
        <ThemeProvider>
          <MarketMapLegend {...props}/>
        </ThemeProvider>
      </svg>
    </div>)
}
