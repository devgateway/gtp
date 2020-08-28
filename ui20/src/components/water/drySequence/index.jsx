import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import DrySeason from "./DrySeason"
import DrySequenceProperties from "./DrySequenceProperties"

const hasDataFunc = ({drySequenceChartDTO}) => drySequenceChartDTO && drySequenceChartDTO.hasData
const childPropsBuilder = (props) => props
const DrySeasonGraphicWithFallback = GraphicWithFallback('water', 'isFilteringDrySequence', 'isFilteredDrySequence',
  childPropsBuilder, hasDataFunc)

class DrySeasonGraphic extends Component {
  static propTypes = {
    getDrySequence: PropTypes.func.isRequired,
  }

  render() {
    const {filter, getDrySequence, intl} = this.props;
    const {drySequenceChartDTO} = getDrySequence(intl) || {}
    const isDaysWithRain = drySequenceChartDTO ? drySequenceChartDTO.isDaysWithRain : false

    return (<Graphic
      id="anchor.indicator.water.dryseason" titleId="indicators.chart.dryseason.title"
      helpId="indicators.chart.dryseason.help"
      sourceId="indicators.chart.dryseason.source">
      {filter && <DrySequenceProperties isDaysWithRain={isDaysWithRain} filter={filter} />}
      <DrySeasonGraphicWithFallback {...this.props} drySequenceChartDTO={drySequenceChartDTO}>
        {childProps => <DrySeason {...childProps} />}
      </DrySeasonGraphicWithFallback>
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
