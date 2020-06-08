import {ResponsiveBar} from "@nivo/bar"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import MonthDecadal from "../../../modules/utils/MonthDecadal"
import messages from "../../../translations/messages"
import DefaultBarOrNegativeValueAsZeroBar from "../../common/DefaultBarOrNegativeValueAsZeroBar"
import * as sccJS from "../../css"
import * as rainfallScc from "./rainfallCSS"
import DecadalTick from "./DecadalTick"
import {INNER_PADDING} from "./RainfallGraphicConstants"
import RainTick from "./RainTick"
import {keysWithRefsToLegendData, ReferenceLineLayer, ReferenceLineLegend} from "./ReferenceLineLayer"

class Rainfall extends Component {
  static propTypes = {
    barData: PropTypes.array,
    keys: PropTypes.array,
    groupMode: PropTypes.string,
    indexBy: PropTypes.string,
    colors: PropTypes.object,
    setting: PropTypes.object.isRequired,
    monthDecadal: PropTypes.instanceOf(MonthDecadal).isRequired,
  }

  componentDidMount() {
  }

  _getMaxValue(barData: Array, keys: Array) {
    return barData.reduce((max, r) => {
      const lineX = Array.from(r.lineValues.values()).map(v => v || 0)
      const barX = keys.map(k => r[k])
      return Math.max(max, ...lineX, ...barX)
    }, 1) * 1.1 || 'auto'
  }

  render() {
    const {barData, keys, indexBy, groupMode, intl, monthDecadal, keysWithRefs} = this.props
    const {byDecadal, showReferences} = this.props.setting
    const maxValue = this._getMaxValue(barData, keys)
    const formatLevel = (s) => {
      const { value } = s;
      if (value === C.ZERO_VALUE) return 0
      if (value === C.NA_VALUE) return <FormattedMessage id="all.graphic.value.NA" />
      return intl.formatNumber(value, {minimumFractionDigits: 0, maximumFractionDigits: 1})
    }

    const referenceLineLegend = {
      data: keysWithRefsToLegendData(keysWithRefs, intl),
      anchor: 'top-left',
      direction: 'row',
      justify: false,
      translateX: rainfallScc.LEGEND_TRANSLATE_X + keys.length * rainfallScc.LEGEND_YEAR_WIDTH,
      translateY: -30,
      itemsSpacing: 2,
      itemWidth: rainfallScc.LEGEND_REF_YEAR_WIDTH + rainfallScc.LEGEND_REF_LINE_LENGTH,
      itemHeight: 20,
      itemDirection: 'left-to-right',
      itemOpacity: 1,
      symbolShape: ({ x, y, size, fill }) =>
        (<path id="lineAB" d={`M 0 10 H ${rainfallScc.LEGEND_REF_LINE_LENGTH}`}
              x={x} y={y} stroke={fill} strokeWidth={3} fill="none" />),
      symbolSize: 14,
      effects: [
        {
          on: 'hover',
          style: {
            itemOpacity: 1
          }
        }
      ]
    }

    const layers = ['grid', 'axes', 'bars', 'legends', 'markers']
    if (showReferences) {
      layers.push(ReferenceLineLayer(intl, keysWithRefs))
      layers.push(ReferenceLineLegend(intl, referenceLineLegend))
    }

    return (<div className="png exportable chart container">
      <ResponsiveBar
        data={barData}
        keys={keys}
        indexBy={indexBy}
        groupMode={groupMode}
        colors={[sccJS.GRAPHIC_COLOR_BLUE, sccJS.GRAPHIC_COLOR_RED, sccJS.GRAPHIC_COLOR_YELLOW]}
        barComponent={DefaultBarOrNegativeValueAsZeroBar}
        maxValue={maxValue}
        minValue={0}
        margin={{ top: 50, bottom: 80, left: 60 }}
        padding={0.3}
        innerPadding={INNER_PADDING}
        fill={[]}
        borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
        axisTop={null}
        axisRight={null}
        axisBottom={{
          tickSize: 0,
          tickPadding: 5,
          tickRotation: 0,
          legend: intl.formatMessage(byDecadal ? messages.decadalsPerMonth : messages.months),
          legendPosition: 'middle',
          // legendOffset: byDecadal ? 62 : 52,
          legendOffset: 52,
          renderTick: (tick) => DecadalTick(tick, monthDecadal)
        }}
        axisLeft={{
          tickSize: 0,
          tickPadding: 5,
          tickRotation: 0,
          legend: intl.formatMessage(messages.rainfall),
          legendPosition: 'middle',
          legendOffset: -40,
          renderTick: (tick) => RainTick(tick)
        }}
        enableLabel={false}

        tooltip={(s)=>{
          return (<div className="tooltips white">
            <div className="color" style={{backgroundColor:s.color}} />
            <div className="label">{s.data.indexLabel}</div>
            <div className='y' style={{'color':s.color}}>{formatLevel(s)}</div>
          </div>)
        }}

        legends={[
          {
            dataFrom: 'keys',
            anchor: 'top-left',
            direction: 'row',
            justify: false,
            translateX: rainfallScc.LEGEND_TRANSLATE_X,
            translateY: -30,
            itemsSpacing: 2,
            itemWidth: rainfallScc.LEGEND_YEAR_WIDTH,
            itemHeight: 20,
            itemDirection: 'left-to-right',
            itemOpacity: 1,
            symbolShape: 'circle',
            symbolSize: 14,
            effects: [
              {
                on: 'hover',
                style: {
                  itemOpacity: 1
                }
              }
            ]
          }
        ]}
        animate={false}
        motionStiffness={90}
        motionDamping={15}
        layers={layers}
        theme={sccJS.NIVO_THEME}
      />
    </div>)
  }
}


const mapStateToProps = state => {
  return {

  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Rainfall))
