import {ThemeProvider} from '@nivo/core'
import {BoxLegendSvg} from "@nivo/legends"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import Market from "../../../modules/entities/market/Market"
import MarketType from "../../../modules/entities/market/MarketType"
import * as sccJS from "../../css"
import * as MarketUtils from "../MarketUtils"

const MAX_WIDTH = 1266
const LEGEND_HEIGHT = 30

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
          anchor='top-left'
          direction='row'
          justify={false}
          itemsSpacing={10}
          itemWidth={120}
          containerWidth={MAX_WIDTH}
          itemHeight={LEGEND_HEIGHT}
          containerHeight={LEGEND_HEIGHT}
          translateX={sccJS.GRAPHIC_TITLE_LEFT_PADDING}
          itemOpacity={1}
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

      <svg className="legend" viewBox={`0 0 ${MAX_WIDTH} ${LEGEND_HEIGHT}`}>
        <ThemeProvider theme={sccJS.NIVO_THEME}>
          <MarketMapLegend {...props}/>
        </ThemeProvider>
      </svg>
    )
}
