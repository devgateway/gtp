import {useTheme} from '@nivo/core'
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import MonthDecadal from "../../../modules/utils/MonthDecadal"

class DecadalTick extends Component {
  static propTypes = {
    tick: PropTypes.shape({
      value: PropTypes.string
    }).isRequired,
    theme: PropTypes.any.isRequired,
    monthDecadal: PropTypes.objectOf(MonthDecadal).isRequired,
  }

  renderText(tickLabel, dy: number) {
    const { theme } = this.props
    return (
      <text
        dy={dy}
        textAnchor="middle"
        dominantBaseline="middle"
        style={{
          ...theme.axis.ticks.text,
          fill: '#333',
          fontSize: 10,
        }}>
        {tickLabel}
      </text>)
  }

  _isShowMonth(month: number, decadal: number) {
    return (this.props.monthDecadal.getDecadals(month).length < 3 && decadal === 1) || decadal === 2
  }

  render() {
    const { tick } = this.props
    const [month, decadal] = tick.value.split(',')
    const showDecadal = !!decadal
    const showMonth = !showDecadal || this._isShowMonth(+month, +decadal)

    return (
      <g transform={`translate(${tick.x},${tick.y + 22})`}>
        {showDecadal && this.renderText(decadal, 0)}
        {showMonth && this.renderText(<FormattedMessage id={`all.month.${month}`} />, showDecadal ? 20 : 0)}
      </g>
    )
  }
}

export default (tick, monthDecadal) => <DecadalTick tick={tick} theme={useTheme()} monthDecadal={monthDecadal} />
