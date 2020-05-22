import {BoxLegendSvg} from "@nivo/legends"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import RiverLevelSettings from "../../../modules/entities/river/RiverLevelSettings"
import * as waterActions from "../../../redux/actions/waterActions"

export const ALERT_COLOR = '#b0413e'

class AlertLegend extends Component {
  static propTypes = {
    showAlert: PropTypes.func.isRequired,
    setting: PropTypes.object.isRequired,
  }

  render() {
    const {intl, showAlert} = this.props
    const setting: RiverLevelSettings = this.props.setting

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
      itemOpacity: setting.showAlert ? 0.75 : 0.5,
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
          onClick={() => showAlert(!setting.showAlert)}
        />
      </React.Fragment>
    )
  }
}

const mapStateToProps = state => {
  return {
    setting: state.getIn(['water', 'data', 'riverLevelChart', 'setting']),
  }
}

const mapActionCreators = {
  showAlert: waterActions.showAlert
}

const AlertLegendIntl = injectIntl(connect(mapStateToProps, mapActionCreators)(AlertLegend))

export const AlertLevelLegend = () => <AlertLegendIntl />





