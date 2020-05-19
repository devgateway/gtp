import {BoxLegendSvg} from "@nivo/legends"
import {line} from "d3-shape"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Tooltip} from 'react-svg-tooltip'
import * as C from "../../../modules/entities/Constants"

class ReferenceLine extends Component {
  static propTypes = {
    bars: PropTypes.array.isRequired,
    xScale: PropTypes.func.isRequired,
    yScale: PropTypes.func.isRequired,
    rgba: PropTypes.string.isRequired,
  }

  render() {
    const {bars, xScale, yScale, rgba, intl} = this.props
    const undefinedToZero = (value) => value === undefined ? 0 : value
    const xScaleByBar = b => xScale(b.data.indexValue) + b.width / 2
    const yScaleByBar = b => yScale(undefinedToZero(b.data.data.lineValues.get(b.data.id)))
    const lineGenerator = line().x(xScaleByBar).y(yScaleByBar)

    return (
      <g>
        <g>
          <path d={lineGenerator(bars)} fill="none" stroke={rgba} strokeWidth="2"/>
        </g>
        <g stroke={rgba} strokeWidth="3" fill={rgba}>
          {bars.map(b => {
            const circleRef = React.createRef()
            const value = b.data.data.lineValues.get(b.data.id)
            const xValue = value === undefined ? intl.formatMessage({ id: "all.graphic.value.NA" }) :
              intl.formatNumber(value, {minimumFractionDigits: 0, maximumFractionDigits: 1})
            return (
              <g key={b.data.index}>
                <circle
                  ref={circleRef}
                  id="pointA"
                  cx={xScaleByBar(b)}
                  cy={yScaleByBar(b)}
                  r="3"/>
                <Tooltip triggerRef={circleRef}>
                  <rect x={2} y={2} width={100} height={30} rx={.5} ry={.5} fill='white' stroke={rgba}/>
                  <text x={20} y={20} fontSize={10} fill='#333'>
                    <tspan className="color" style={{backgroundColor:b.data.color}} />
                    <tspan>{`${b.data.data.indexLabel} : ${xValue}`}</tspan>
                  </text>
                </Tooltip>
              </g>)
          })}
        </g>
      </g>)
  }
}

const rgba = ["rgba(150, 3, 15, 1)", "rgba(300, 3, 15, 1)", "rgba(10, 3, 15, 1)"]

export const ReferenceLineLayer = (intl, keysWithRefs) => ({ bars, xScale, yScale }) => {
  let idx = 0
  return (
    <svg>
      {keysWithRefs.length && keysWithRefs.map(([key, ]) => {
        const useBars = bars.filter(b => b.data.id === key)
        return (
          <ReferenceLine
            key={idx} bars={useBars} xScale={xScale} yScale={yScale} rgba={rgba[idx++]} intl={intl} />)
      })}
  </svg>)
}

export const keysWithRefsToLegendData = (keysWithRefs) => keysWithRefs.map(([k, ref], index) => ({
  color: rgba[index],
  id: k,
  label: ref
}))


export const ReferenceLineLegend = (referenceLineLegend) => ({ height, legends, width }) => {
  const keys = JSON.stringify(referenceLineLegend.data.map(({id}) => id))
  return (
    <React.Fragment>
      <BoxLegendSvg
        key={keys}
        {...referenceLineLegend}
        containerHeight={height}
        containerWidth={width}
      />
    </React.Fragment>
  )}
