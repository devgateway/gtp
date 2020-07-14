import {ResponsiveLine} from "@nivo/line"
import {TableTooltip} from '@nivo/tooltip'
import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import RiverLevelChartDTO from "../../../modules/graphic/water/river/RiverLevelChartDTO"
import Chip from "../../common/graphic/Chip"
import * as cssJS from "../../css"
import * as sccRiverLevel from "./cssRiverLevel"
import {ALERT_COLOR, AlertLevelLegend} from "./AlertLevelLegend"
import CustomLegendSymbol, {LEGEND_SYMBOL_CIRCLE, LEGEND_SYMBOL_LINE} from "../../common/legend/CustomLegendSymbol"

class RiverLevel extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(RiverLevelChartDTO).isRequired,
    setting: PropTypes.object.isRequired,
    filter: PropTypes.object.isRequired,
  }

  constructor(props) {
    super(props);
    this.state = {
      isLocalStateChange: false
    }
  }

  static getDerivedStateFromProps(props, state) {
    const {isLocalStateChange, riverStationId} = state
    if (isLocalStateChange) {
      return {
        isLocalStateChange: false
      }
    }
    if (riverStationId !== props.filter.riverStationId) {
      return {
        hideReferenceYears: new Set(),
        riverStationId: props.filter.riverStationId,
      }
    }
    return null
  }

  levelLineToggle({riverLevelYear}) {
    const {year, isReference} = riverLevelYear
    if (isReference) {
      const {hideReferenceYears} = this.state
      if (!hideReferenceYears.delete(year)) {
        hideReferenceYears.add(year)
      }
      this.setState({
        hideReferenceYears,
        isLocalStateChange: true
      })
    }
  }


  render() {
    const { data, intl, setting } = this.props
    const { hideReferenceYears } = this.state

    const colors = getColors(data.lines)
    const lineColor = ({riverLevelYear, index}) => {
      const isHide = hideReferenceYears.has(riverLevelYear.year)
      return isHide ? 'rgba(0, 0, 0, .0)' : colors[index]
    }

    const showAlert = setting.showAlert && data.alertLevel
    const showAlertLegend = !!data.alertLevel

    const alertMarker = {
      axis: 'y',
      value: data.alertLevel,
      lineStyle: {
        stroke: ALERT_COLOR,
        strokeWidth: 2
      },
    }

    return (
      <div className="graphic-content">
        <ResponsiveLine
          enableGridY={true}
          enableGridX={false}
          margin={{ top: 50, right: 50, bottom: 75, left: 60 }}

          data={data.lines}
          xScale={{
            type: 'time',
            format: '%Y-%m-%d',
            precision: 'day',
          }}
          xFormat="time:%Y-%m-%d"
          yScale={{
            type: 'linear',
            stacked: false,
          }}
          enableSlices='x'
          sliceTooltip={CustomSliceTooltip}

          axisLeft={{
            legendPosition: 'middle',
            legend: intl.formatMessage({ id: "water.rainlevel.level"}),
            legendOffset: -45,
            tickSize: 0,
            tickPadding: 5,
            tickRotation: 0,
          }}
          axisBottom={{
            format: (date: Date) => intl.formatMessage({ id: `all.month.${date.getMonth() + 1}`}),
            tickValues: 'every month',
            tickSize: 10,
            legendOffset: 40,
            legendPosition: 'middle'
          }}
          curve="monotoneX"
          enablePoints={false}
          useMesh={true}
          colors={lineColor}
          animate={true}

          markers={showAlert ? [alertMarker] : null}

          legends={[
            {
              data: data.lines.map(({id, riverLevelYear}, index) => ({
                id: index,
                label: id,
                color: colors[index],
                riverLevelYear,
              })),
              anchor: 'top-left',
              direction: 'row',
              justify: false,
              translateX: -sccRiverLevel.LEGEND_TRANSLATE_X,
              translateY: -30,
              itemsSpacing: 0,
              itemWidth: sccRiverLevel.LEGEND_ITEM_WIDTH,
              itemHeight: 20,
              itemOpacity: 0.75,
              symbolSize: 12,
              symbolShape: (legendProps) => {
                const {isReference} = data.lines[legendProps.id].riverLevelYear
                const type = isReference ? LEGEND_SYMBOL_LINE : LEGEND_SYMBOL_CIRCLE
                return <CustomLegendSymbol type={type} {...legendProps}
                                    lineLength={cssJS.LEGEND_SYMBOL_LINE_LENGTH}/>
              },
              symbolBorderColor: 'rgba(0, 0, 0, .5)',
              onClick: this.levelLineToggle.bind(this),
              effects: [
                {
                  on: 'hover',
                  style: {
                    itemBackground: 'rgba(0, 0, 0, .03)',
                    itemOpacity: 1
                  }
                }
              ]
            }
          ]}
          layers={['grid', 'markers', 'axes', 'areas', 'crosshair', 'lines', 'points', 'slices', 'mesh', 'legends',
            showAlertLegend ? AlertLevelLegend(data.lines.length) : null]}
          theme={cssJS.NIVO_THEME}
        />
      </div>);
  }
}

const getColors = (lines) => {
  let refIndex = 0
  let levelIndex = 0
  return lines.map(({riverLevelYear}) => {
    if (riverLevelYear.isReference) {
      return cssJS.REFERENCE_COLORS[refIndex++]
    }
    const index = levelIndex++ % cssJS.PALLET_COLORS.length
    return cssJS.PALLET_COLORS[index]
  })
}

const CustomSliceTooltip = ({slice, axis}) => {
  const otherAxis = axis === 'x' ? 'y' : 'x';
  const dataProp = "".concat(otherAxis, "Formatted")
  const rows = slice.points.map((point) => {
    return [
      <Chip key="chip" color={point.serieColor}/>,
      point.serieId,
      <span><strong key="value">{point.data[dataProp]}</strong> cm</span>,
    ];
  })
  const date:Date = slice.points[0].data.actualDate
  const month = date.getMonth() + 1
  const title = <strong>{date.getDate()} <FormattedMessage id={`all.month.${month}`} /></strong>
  return <TableTooltip title={title} rows={rows}/>
}


const mapStateToProps = state => {
  return {
    setting: state.getIn(['water', 'data', 'riverLevelChart', 'setting']),
    filter: state.getIn(['water', 'data', 'riverLevelChart', 'filter']),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevel))
