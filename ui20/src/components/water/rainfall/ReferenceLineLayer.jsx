import * as PropTypes from "prop-types"
import React, {Component} from "react"
import { line } from "d3-shape"

class ReferenceLineLayer extends Component {
  static propTypes = {
    bars: PropTypes.array.isRequired,
    xScale: PropTypes.func.isRequired,
    yScale: PropTypes.func.isRequired,
    rgba: PropTypes.string.isRequired,
  }

  render() {
    const {bars, xScale, yScale, rgba} = this.props
    const lineGenerator = line()
      .x(b => xScale(b.data.indexValue) + b.width / 2)
      .y(b => yScale(b.data.data.lineValues.get(b.data.id)))
    const circleRef = React.createRef();

    return (
      <g>
        <g>
          <path ref={circleRef} d={lineGenerator(bars)} fill="none" stroke={rgba} strokeWidth="2"/>
        </g>
        <g stroke={rgba} strokeWidth="3" fill={rgba}>
          {bars.map(b => (
            <circle
              key={b.data.index}
              id="pointA"
              cx={xScale(b.data.indexValue) + b.width / 2}
              cy={yScale(b.data.data.lineValues.get(b.data.id))}
              r="3"/>))}
        </g>
      </g>)
  }
}

const rgba = ["rgba(150, 3, 15, 1)", "rgba(300, 3, 15, 1)", "rgba(10, 3, 15, 1)"]

export default (keysWithRefs) => ({ bars, xScale, yScale }) => {
  let idx = 0
  return (
    <svg>
      {keysWithRefs.length && keysWithRefs.map(key => {
        const useBars = bars.filter(b => b.data.id == key)
        return <ReferenceLineLayer key={idx} bars={useBars} xScale={xScale} yScale={yScale} rgba={rgba[idx++]} />
      })}
  </svg>)
}
