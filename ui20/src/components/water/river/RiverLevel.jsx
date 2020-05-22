import {ResponsiveLine} from "@nivo/line"
import PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import RiverLevelChartDTO from "../../../modules/graphic/water/river/RiverLevelChartDTO"
import {ALERT_COLOR, AlertLevelLegend} from "./AlertLevelLegend"

class RiverLevel extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(RiverLevelChartDTO).isRequired,
    setting: PropTypes.object.isRequired,
  }

  render() {
    const { data, intl, setting } = this.props

    const alertMarker = {
      axis: 'y',
      value: data.alertLevel,
      lineStyle: {
        stroke: ALERT_COLOR,
        strokeWidth: 2
      },
    }

    return (
      <div className="png exportable chart container">
        <ResponsiveLine
          enableGridY={true}
          enableGridX={true}
          margin={{ top: 50, right: 50, bottom: 110, left: 60 }}

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

          axisLeft={{
            legendPosition: 'middle',
            legend: intl.formatMessage({ id: "water.rainlevel.level"}),
            legendOffset: -50,
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
          }}
          axisBottom={{
            format: "%b",
            tickValues: 'every month',
            legend: intl.formatMessage({ id: "water.rainlevel.period"}),
            legendOffset: 40,
            legendPosition: 'middle'
          }}
          curve="monotoneX"
          enablePoints={false}
          useMesh={true}
          colors={{ scheme: 'category10' }}
          animate={true}

          markers={setting.showAlert ? [alertMarker] : null}

          legends={[
            {
              anchor: 'top-right',
              direction: 'row',
              justify: false,
              translateX: -130,
              translateY: -30,
              itemsSpacing: 0,
              itemWidth: 120,
              itemHeight: 20,
              itemOpacity: 0.75,
              symbolSize: 12,
              symbolShape: 'circle',
              symbolBorderColor: 'rgba(0, 0, 0, .5)',
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
          layers={['grid', 'markers', 'axes', 'areas', 'crosshair', 'lines', 'points', 'slices', 'mesh', 'legends', AlertLevelLegend]}
        />
      </div>);
  }
}


const mapStateToProps = state => {
  return {
    setting: state.getIn(['water', 'data', 'riverLevelChart', 'setting']),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevel))
