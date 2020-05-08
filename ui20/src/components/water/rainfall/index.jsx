import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/Graphic"
import Rainfall from "./Rainfall"

class RainfallGraphic extends Component {
  static propTypes = {
    getRain: PropTypes.func.isRequired,
    setRainPerDecadal: PropTypes.func.isRequired,
  }

  render() {
    const {getRain, intl, setting} = this.props;

    return (<Graphic
      id="anchor.indicator.water.rainfall" titleId="indicators.chart.rainfall.title"
      sourceId="indicators.chart.rainfall.source" className="rainfall">
      <Rainfall {...getRain(intl)} byDecadal={setting.byDecadal} />
    </Graphic>)
  }

}


const mapStateToProps = state => {
  return {
    setting: state.getIn(['water', 'data', 'rainLevelChart', 'setting']),
  }
}

const mapActionCreators = {
  getRain: waterActions.getRain,
  setRainPerDecadal: waterActions.setRainPerDecadal,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallGraphic))
