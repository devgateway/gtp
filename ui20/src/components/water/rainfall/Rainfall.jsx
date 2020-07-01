import {ResponsiveBar} from "@nivo/bar"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import RainfallDTO from "../../../modules/graphic/water/rainfall/RainfallDTO"
import messages from "../../../translations/messages"
import CustomLegendSymbol, {LEGEND_SYMBOL_LINE} from "../../common/legend/CustomLegendSymbol"
import DefaultBarOrNegativeValueAsZeroBar from "../../common/graphic/DefaultBarOrNegativeValueAsZeroBar"
import * as sccJS from "../../css"
import * as rainfallScc from "./rainfallCSS"
import DecadalTick from "./DecadalTick"
import {INNER_PADDING} from "./RainfallGraphicConstants"
import RainTick from "./RainTick"
import {keysWithRefsToLegendData, ReferenceLineLayer, ReferenceLineLegend} from "./ReferenceLineLayer"

class Rainfall extends Component {
  static propTypes = {
    rainfallDTO: PropTypes.instanceOf(RainfallDTO).isRequired,
    colors: PropTypes.object,
    setting: PropTypes.object.isRequired,
  }

  render() {
    const {intl} = this.props
    const {barData, keys, keysWithRefs, indexBy, monthDecadal, maxValue} = this.props.rainfallDTO
    const {byDecadal, showReferences} = this.props.setting
    const graphicMaxValue = maxValue * 1.1 || 'auto'
    const unit = intl.formatMessage({ id: "water.rainfall.unit"})
    const formatLevel = (s) => {
      let value = s.value
      if (value === C.SMALL_VALUE) value = s.data.actualValue[s.id]
      if (value === C.NA_VALUE) return <FormattedMessage id="all.graphic.value.NA" />
      if (value === C.ZERO_VALUE) {
        value = 0
      }
      return `${intl.formatNumber(value, {minimumFractionDigits: 0, maximumFractionDigits: 1})} ${unit}`
    }

    const referenceLineLegend = {
      data: keysWithRefsToLegendData(keysWithRefs, intl),
      anchor: 'top-left',
      direction: 'row',
      justify: false,
      translateX: rainfallScc.LEGEND_TRANSLATE_X + keys.length * rainfallScc.LEGEND_YEAR_WIDTH,
      translateY: -30,
      itemsSpacing: 2,
      itemWidth: rainfallScc.LEGEND_REF_YEAR_WIDTH + sccJS.LEGEND_SYMBOL_LINE_LENGTH,
      itemHeight: 20,
      itemDirection: 'left-to-right',
      itemOpacity: 1,
      symbolShape: (legendProps) =>
        <CustomLegendSymbol type={LEGEND_SYMBOL_LINE} {...legendProps} />,
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

    return (<div className="graphic-content">
      <ResponsiveBar
        data={barData}
        keys={keys}
        indexBy={indexBy}
        groupMode='grouped'
        colors={[sccJS.GRAPHIC_COLOR_BLUE, sccJS.GRAPHIC_COLOR_RED, sccJS.GRAPHIC_COLOR_YELLOW]}
        barComponent={DefaultBarOrNegativeValueAsZeroBar}
        maxValue={graphicMaxValue}
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
          legendOffset: -45,
          renderTick: (tick) => RainTick(tick)
        }}
        enableLabel={false}

        tooltip={(s)=>{
          return (<div className="tooltips white">
            <div className="color" style={{backgroundColor:s.color}} />
            <div className="label with-x no-separator">{s.data.indexLabel}</div>
            <div className='x'>{s.id}</div>
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
