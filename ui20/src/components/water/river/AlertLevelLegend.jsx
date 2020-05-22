import {BoxLegendSvg} from "@nivo/legends"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"

export const ALERT_COLOR = '#b0413e'

class AlertLegend extends Component {

  render() {
    const {intl} = this.props

    const alertLegendProps = {
      data: [{
        color: ALERT_COLOR,
        id: '1000',
        label: intl.formatMessage({ id: 'all.alertLevel'})
      }],
      anchor: 'top-right',
      direction: 'row',
      justify: false,
      translateX:1004,
      translateY: -30,
      itemsSpacing: 2,
      itemWidth: 100,
      itemHeight: 20,
      itemOpacity: 1,
      symbolShape: 'circle',
      symbolSize: 12,
      effects: [
        {
          on: 'hover',
          style: {
            itemOpacity: 1
          }
        }
      ]
    }
    return (
      <React.Fragment>
        <BoxLegendSvg
          {...alertLegendProps}
          containerHeight={alertLegendProps.itemHeight}
          containerWidth={alertLegendProps.itemWidth}
        />
      </React.Fragment>
    )
  }
}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

const AlertLegendIntl = injectIntl(connect(mapStateToProps, mapActionCreators)(AlertLegend))

export const AlertLevelLegend = () => <AlertLegendIntl />





