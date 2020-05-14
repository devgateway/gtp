import {ResponsiveBar} from "@nivo/bar"
import React, {Component} from "react"
import PropTypes from "prop-types"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import DrySequenceChartDTO from "../../../modules/graphic/water/drySequence/DrySequenceChartDTO"
import messages from "../../../translations/messages"

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

    return (<div className="png exportable chart container">
        <ResponsiveBar
          data={drySequenceChartDTO.barData}
          keys={drySequenceChartDTO.keys}
          indexBy={drySequenceChartDTO.indexBy}
          groupMode={drySequenceChartDTO.groupMode}
          colors={["#3C7EBB", "#3C7EBB", "#3C7EBB"]}
          minValue={C.NA_VALUE}
          margin={{top: 50, right: 130, bottom: 80, left: 60}}
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
            legend: intl.formatMessage({ id: "all.decadalsPerMonth", defaultMessage: "Decadals per month"}),
            legendPosition: 'middle',
            legendOffset: 52,
          }}
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