import React, {Component} from "react"
import * as cssJS from "../../css"

export const LEGEND_SYMBOL_LINE = 'line'
export const LEGEND_SYMBOL_CIRCLE = 'circle'
export const LEGEND_SYMBOLS = [LEGEND_SYMBOL_LINE, LEGEND_SYMBOL_CIRCLE]

export interface CustomLegendSymbolProps {
  type: LEGEND_SYMBOL_LINE | LEGEND_SYMBOL_CIRCLE;
  lineLength?: number;
  x: number | string;
  y: number | string;
  fill: string;
  size: number;
  my?: number;
}

export default class CustomLegendSymbol extends Component<CustomLegendSymbolProps> {

  static defaultProps = {
    lineLength: cssJS.LEGEND_SYMBOL_LINE_LENGTH,
  }

  render() {
    if (this.props.type === LEGEND_SYMBOL_LINE) {
      return Line(this.props)
    }
    return Circle(this.props)
  }
}

const Circle = ({ x, y, size, fill }) => {
  const r = size / 2
  return <circle x={x} y={y} cx ={x + r} cy={y + r} r={r} fill={fill}/>
}

const Line = ({ x, y, size, fill, my, lineLength}) =>
  <path d={`M 0 ${my || 10} H ${lineLength}`} x={x} y={y} stroke={fill} strokeWidth={3} fill="none" pathLength={lineLength} />
