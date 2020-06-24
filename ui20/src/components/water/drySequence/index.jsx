import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/graphic/Graphic"
import DrySeason from "./DrySeason"
import DrySequenceProperties from "./DrySequenceProperties"


class DrySeasonGraphic extends Component {
  static propTypes = {
    getDrySequence: PropTypes.func.isRequired,
  }

  render() {
    const {filter, getDrySequence, intl} = this.props;
    const builderResult = getDrySequence(intl)

    return (<Graphic
      id="anchor.indicator.water.dryseason" titleId="indicators.chart.dryseason.title"
      sourceId="indicators.chart.dryseason.source">
      <DrySequenceProperties isDaysWithRain={builderResult.drySequenceChartDTO.isDaysWithRain} filter={filter} />
      <DrySeason {...builderResult} />
    </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    settings: state.getIn(['water', 'data', 'drySequenceChart', 'settings']),
    filter: state.getIn(['water', 'data', 'drySequenceChart', 'filter']),
    data: state.getIn(['water', 'data', 'drySequenceChart', 'data']),
  }
}

const mapActionCreators = {
  getDrySequence: waterActions.getDrySequence,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DrySeasonGraphic))
