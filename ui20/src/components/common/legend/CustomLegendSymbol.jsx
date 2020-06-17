import PropTypes from "prop-types"
import React, {Component} from "react"

export const LEGEND_SYMBOL_LINE = 'line'
export const LEGEND_SYMBOL_CIRCLE = 'circle'
export const LEGEND_SYMBOLS = [LEGEND_SYMBOL_LINE, LEGEND_SYMBOL_CIRCLE]

export default class CustomLegendSymbol extends Component {
  static propTypes = {
    type: PropTypes.oneOf(LEGEND_SYMBOLS).isRequired,
    lineLength: PropTypes.number,
    legendProps: PropTypes.object.isRequired,
  }

  render() {
    const {legendProps, lineLength, type} = this.props
    if (type === LEGEND_SYMBOL_LINE) {
      return Line(legendProps, lineLength)
    }
    return Circle(legendProps)
  }
}

const Circle = ({ x, y, size, fill }) => {
  const r = size / 2
  return <circle x={x} y={y} cx ={x + r} cy={y + r} r={r} fill={fill}/>
}

const Line = ({ x, y, size, fill }, lineLength) =>
  <path d={`M 0 10 H ${lineLength}`} x={x} y={y} stroke={fill} strokeWidth={3} fill="none" />
