import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {useTheme} from '@nivo/core'

class DecadalTick extends Component {
  static propTypes = {
    tick: PropTypes.shape({
      value: PropTypes.string
    }).isRequired,
    theme: PropTypes.any.isRequired,
  }

  renderText(str: string, dy: number) {
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
        {str}
      </text>)
  }

  render() {
    const { tick } = this.props
    const [month, decadal] = tick.value.split(',')

    return (
      <g transform={`translate(${tick.x},${tick.y + 22})`}>
        {this.renderText(decadal, 0)}
        {decadal == 2 ? this.renderText(month, 20) : null}
      </g>
    )
  }
}

export default (tick) => <DecadalTick tick={tick} theme={useTheme()} />
