import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/Graphic"
import Rainfall from "./Rainfall"
import RainfallProperties from "./RainfallProperties"


class RainfallGraphic extends Component {
  static propTypes = {
    getRain: PropTypes.func.isRequired,
  }

  render() {
    const {getRain, intl, setting} = this.props;

    return (<Graphic
      id="anchor.indicator.water.rainfall" titleId="indicators.chart.rainfall.title"
      sourceId="indicators.chart.rainfall.source">
      <RainfallProperties />
      <Rainfall {...getRain(intl)} setting={setting} />
    </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    setting: state.getIn(['water', 'data', 'rainLevelChart', 'setting']),
    data: state.getIn(['water', 'data', 'rainLevelChart', 'data']),
  }
}

const mapActionCreators = {
  getRain: waterActions.getRain,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallGraphic))
