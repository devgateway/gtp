import {useTheme} from '@nivo/core'
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"

class RainTick extends Component {
  static propTypes = {
    tick: PropTypes.shape({
      number: PropTypes.string
    }).isRequired,
    theme: PropTypes.any.isRequired,
  }

  render() {
    const {tick, theme, intl} = this.props
    let valueStr = intl.formatNumber(tick.value, {maximumFractionDigits: 1, minimumFractionDigits: 1 })
    // 160 for &nbsp; breaks svg to img conversion in Safari 12.1, thus using standard 32 space
    valueStr = valueStr.replace(String.fromCharCode(160), ' ')

    return (
      <g transform={`translate(${tick.x},${tick.y})`}>
        <text
          dx={-15}
          textAnchor="middle"
          dominantBaseline="middle"
          style={{
            ...theme.axis.ticks.text,
            whiteSpace: 'nowrap'
          }}>
          <tspan>{valueStr}</tspan>
        </text>
      </g>)
  }
}

const RainTickIntl = injectIntl(RainTick)

export default (tick) => <RainTickIntl tick={tick} theme={useTheme()} />
