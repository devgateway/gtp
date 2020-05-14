import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/Graphic"
import DrySeason from "./DrySeason"


class DrySeasonGraphic extends Component {
  static propTypes = {
    getDrySequence: PropTypes.func.isRequired,
  }

  render() {
    const {getDrySequence, intl} = this.props;

    return (<Graphic
      id="anchor.indicator.water.dryseason" titleId="indicators.chart.dryseason.title"
      sourceId="indicators.chart.dryseason.source" className="rainfall">
      <DrySeason {...getDrySequence(intl)} />
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
  getDrySequence: waterActions.getDrySequence,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DrySeasonGraphic))
