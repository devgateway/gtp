import {BoxLegendSvg} from "@nivo/legends"
import {line} from "d3-shape"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {Tooltip} from 'react-svg-tooltip'
import * as sccJS from "../../css"
import {INNER_PADDING} from "./RainfallGraphicConstants"

class ReferenceLine extends Component {
  static propTypes = {
    bars: PropTypes.array.isRequired,
    xScale: PropTypes.func.isRequired,
    yScale: PropTypes.func.isRequired,
    rgba: PropTypes.string.isRequired,
  }

  render() {
    const {bars, barsGroupSize, xScale, yScale, rgba, intl} = this.props
    const undefinedToZero = (value) => value === undefined ? 0 : value
    const skipXBarWidthRatio = barsGroupSize * 0.5
    const innerPadding = (barsGroupSize - 1) * INNER_PADDING * 0.5
    const xScaleByBar = b => xScale(b.data.indexValue) + b.width * skipXBarWidthRatio + innerPadding
    const yScaleByBar = b => yScale(undefinedToZero(b.data.data.lineValues.get(b.data.id)))
    const lineGenerator = line().x(xScaleByBar).y(yScaleByBar)

    return (
      <g>
        <g>
          <path d={lineGenerator(bars)} fill="none" stroke={rgba} strokeWidth="1"/>
        </g>
        <g stroke={rgba} strokeWidth="3" fill={rgba}>
          {bars.map((b, index) => {
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
                  <filter id="f1" x="0" y="0" width="200%" height="200%">
                    <feOffset result="offOut" in="SourceAlpha" dx="1" dy="1" />
                    <feGaussianBlur result="blurOut" in="offOut" stdDeviation="3" />
                    <feBlend in="SourceGraphic" in2="blurOut" mode="normal" />
                  </filter>
                  <rect x={2} y={2} width={120} height={35} rx={.5} ry={.5} strokeWidth={1} fill="white" filter="url(#f1)" />
                  <text x={10} y={23} fontSize={12} fill='#747474'>
                    <tspan className="color" style={{backgroundColor: b.data.color, width: 6, fontWeight: 800, fontSize: 20}}>|</tspan>
                    <tspan>{`${b.data.data.indexLabel} : ${xValue}`}</tspan>
                  </text>
                </Tooltip>
              </g>)
          })}
        </g>
      </g>)
  }
}

const rgba = [sccJS.GRAPHIC_COLOR_GRAY1, sccJS.GRAPHIC_COLOR_GRAY2, sccJS.GRAPHIC_COLOR_GRAY3]

export const ReferenceLineLayer = (intl, keysWithRefs) => ({ bars, xScale, yScale, keys }) => {
  let idx = 0
  return (
    <svg>
      {keysWithRefs.length && keysWithRefs.map(([key, ]) => {
        const useBars = bars.filter(b => b.data.id === key)
        return (
          <ReferenceLine
            key={idx} bars={useBars} xScale={xScale} yScale={yScale} rgba={rgba[idx++]} intl={intl}
            barsGroupSize={keys.length}/>)
      })}
  </svg>)
}

export const keysWithRefsToLegendData = (keysWithRefs, intl) => keysWithRefs.map(([k, ref], index) => ({
  color: rgba[index],
  id: k,
  label: `${intl.formatMessage({ id: "all.reference" })} ${ref}`
}))


export const ReferenceLineLegend = (intl, referenceLineLegend) => ({ height, legends, width }) => {
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
