import {BarDefaultProps, ResponsiveBar} from "@nivo/bar"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import MonthDecadal from "../../../modules/utils/MonthDecadal"
import messages from "../../../translations/messages"
import DecadalTick from "./DecadalTick"
import "./rainfall.scss"
import RainTick from "./RainTick"
import ReferenceLineLayer from "./ReferenceLineLayer"

class Rainfall extends Component {
  static propTypes = {
    barData: PropTypes.array,
    keys: PropTypes.array,
    groupMode: PropTypes.string,
    indexBy: PropTypes.string,
    colors: PropTypes.object,
    byDecadal: PropTypes.bool,
    monthDecadal: PropTypes.instanceOf(MonthDecadal).isRequired,
  }

  componentDidMount() {
  }

  _getMaxValue(barData: Array, keys: Array) {
    return barData.reduce((max, r) => {
      const lineX = Array.from(r.lineValues.values())
      const barX = keys.map(k => r[k])
      return Math.max(max, ...lineX, ...barX)
    }, 0) * 1.1 || 'auto'
  }

  render() {
    const {barData, keys, indexBy, groupMode, colors, intl, byDecadal, monthDecadal, keysWithRefs} = this.props
    const maxValue = this._getMaxValue(barData, keys)
    const formatLevel = (s) => {
      const { value } = s;
      if (value === C.ZERO_VALUE) return 0
      if (value === C.NA_VALUE) return <FormattedMessage id="all.graphic.value.NA" />
      return intl.formatNumber(value, {minimumFractionDigits: 0, maximumFractionDigits: 1})
    }

    return (<div className="png exportable chart container">
      <ResponsiveBar
        data={barData}
        keys={keys}
        indexBy={indexBy}
        groupMode={groupMode}
        colors={colors}
        barComponent={CustomBarComponent}
        maxValue={maxValue}
        minValue={0}
        margin={{ top: 50, right: 130, bottom: 80, left: 60 }}
        padding={0.3}
        innerPadding={3}
        fill={[]}
        borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
        axisTop={null}
        axisRight={null}
        axisBottom={{
          tickSize: 0,
          tickPadding: 5,
          tickRotation: 0,
          legend: intl.formatMessage(byDecadal ? messages.decadals : messages.months),
          legendPosition: 'middle',
          legendOffset: byDecadal ? 62 : 52,
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
            anchor: 'top-right',
            direction: 'row',
            justify: false,
            translateX: 120,
            translateY: -30,
            itemsSpacing: 2,
            itemWidth: 100,
            itemHeight: 20,
            itemDirection: 'right-to-left',
            itemOpacity:1,
            symbolSize: 20,
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
        layers={['grid', 'axes', 'bars', 'markers', 'legends', ReferenceLineLayer(intl, keysWithRefs)]}
      />
    </div>)
  }
}

const CustomBarComponent = (props) => {
  const {height, data} = props
  const { value } = data
  return <BarDefaultProps.barComponent  {...props} height={value > 0 ? height : 1} />
}

const mapStateToProps = state => {
  return {

  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Rainfall))
