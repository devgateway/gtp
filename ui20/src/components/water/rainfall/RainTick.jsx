import {useTheme} from '@nivo/core'
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedNumber} from "react-intl"

class RainTick extends Component {
  static propTypes = {
    tick: PropTypes.shape({
      number: PropTypes.string
    }).isRequired,
    theme: PropTypes.any.isRequired,
  }

  render() {
    const {tick, theme} = this.props
    return (
      <g transform={`translate(${tick.x},${tick.y})`}>
        <text
          dx={-15}
          textAnchor="middle"
          dominantBaseline="middle"
          style={{
            ...theme.axis.ticks.text,
            fill: '#333',
            fontSize: 10,
          }}>
          <FormattedNumber value={tick.value} maximumFractionDigits={1} minimumFractionDigits={1} />
        </text>
      </g>)
  }
}

export default (tick) => <RainTick tick={tick} theme={useTheme()} />
