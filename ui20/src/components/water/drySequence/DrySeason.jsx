import {ResponsiveBar} from "@nivo/bar"
import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import DrySequenceChartDTO from "../../../modules/graphic/water/drySequence/DrySequenceChartDTO"
import messages from "../../../translations/messages"
import DefaultBarOrNegativeValueAsZeroBar from "../../common/DefaultBarOrNegativeValueAsZeroBar"
import * as sccJS from "../../css"

class DrySeason extends Component {
  static propTypes = {
    drySequenceChartDTO: PropTypes.instanceOf(DrySequenceChartDTO).isRequired
  }

  render() {
    const {intl} = this.props
    const drySequenceChartDTO: DrySequenceChartDTO = this.props.drySequenceChartDTO
    const formatLevel = (s) => {
      const {value} = s;
      if (value === C.ZERO_VALUE) return 0
      if (value === C.NA_VALUE) return <FormattedMessage id="all.graphic.value.NA"/>
      return intl.formatNumber(value, {minimumFractionDigits: 0, maximumFractionDigits: 0})
    }

    return (<div className="graphic-content">
        <ResponsiveBar
          data={drySequenceChartDTO.barData}
          keys={drySequenceChartDTO.keys}
          indexBy={drySequenceChartDTO.indexBy}
          groupMode={drySequenceChartDTO.groupMode}
          colors={[sccJS.GRAPHIC_COLOR_BLUE, sccJS.GRAPHIC_COLOR_BLUE, sccJS.GRAPHIC_COLOR_BLUE]}
          barComponent={DefaultBarOrNegativeValueAsZeroBar}
          minValue={0}
          margin={{top: 50, bottom: 80, left: 60}}
          padding={0.3}
          innerPadding={3}
          fill={[]}
          borderColor={{from: 'color', modifiers: [['darker', 1.6]]}}
          axisTop={null}
          axisRight={null}
          axisBottom={{
            tickSize: 0,
            tickPadding: 5,
            tickRotation: 0,
            legend: intl.formatMessage(messages.decadalsPerMonth),
            legendPosition: 'middle',
            legendOffset: 52,
          }}
          maxValue={11}
          gridYValues={[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]}
          axisLeft={{
            tickSize: 0,
            tickPadding: 5,
            tickRotation: 0,
            legend: intl.formatMessage(messages.noOfDays),
            legendPosition: 'middle',
            legendOffset: -40,
          }}
          enableLabel={false}

          tooltip={(s) => {
            return (<div className="tooltips white">
              <div className="color" style={{backgroundColor: s.color}}/>
              <div className="label">{s.id}</div>
              <div className='y' style={{'color': s.color}}>{formatLevel(s)}</div>
            </div>)
          }}
          animate={false}
          motionStiffness={90}
          motionDamping={15}
          layers={['grid', 'axes', 'bars', 'legends', 'markers']}
          theme={sccJS.NIVO_THEME}
        />
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DrySeason))
