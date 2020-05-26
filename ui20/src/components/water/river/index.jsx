import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/Graphic"
import RiverLevel from "./RiverLevel"
import RiverLevelProperties from "./RiverLevelProperties"


class RiverLevelGraphic extends Component {
  static propTypes = {
    getRiverLevel: PropTypes.func.isRequired,
    filter: PropTypes.object.isRequired,
    isFilteringRiverLevel: PropTypes.bool.isRequired,
    isFilteredRiverLevel: PropTypes.bool.isRequired,
  }

  shouldComponentUpdate(nextProps, nextState) {
    const {isFilteringRiverLevel} = nextProps
    return !isFilteringRiverLevel;
  }

  render() {
    const {getRiverLevel, filter} = this.props

    return (<Graphic
      id="anchor.indicator.water.riverlevel" titleId="indicators.chart.riverlevel.title"
      sourceId="indicators.chart.riverlevel.source" className="rainfall">
      <RiverLevelProperties filter={filter}/>
      <RiverLevel {...getRiverLevel()} />
    </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    filter: state.getIn(['water', 'data', 'riverLevelChart', 'filter']),
    isFilteringRiverLevel: state.getIn(['water', 'isFilteringRiverLevel']),
    isFilteredRiverLevel: state.getIn(['water', 'isFilteredRiverLevel']),
  }
}

const mapActionCreators = {
  getRiverLevel: waterActions.getRiverLevel
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevelGraphic))
