import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import RiverLevel from "./RiverLevel"
import RiverLevelProperties from "./RiverLevelProperties"

const hasDataFunc = ({data}) => data && data.hasData
const childPropsBuilder = (props) => props.getRiverLevel()
const RainfallGraphicWithFallback = GraphicWithFallback('water', 'isFilteringRiverLevel', 'isFilteredRiverLevel',
  childPropsBuilder, hasDataFunc)


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
    const {apiData, filter} = this.props

    return (<Graphic
      id="anchor.indicator.water.riverlevel" titleId="indicators.chart.riverlevel.title"
      helpId="indicators.chart.riverlevel.help" helpProps={{wide: true}}
      sourceId="indicators.chart.riverlevel.source" className="rainfall">
      {apiData && <RiverLevelProperties filter={filter}/>}
      <RainfallGraphicWithFallback {...this.props}>
        {childProps => <RiverLevel {...childProps} />}
      </RainfallGraphicWithFallback>
    </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    apiData: state.getIn(['water', 'data', 'riverLevelChart', 'data']),
    filter: state.getIn(['water', 'data', 'riverLevelChart', 'filter']),
    isFilteringRiverLevel: state.getIn(['water', 'isFilteringRiverLevel']),
    isFilteredRiverLevel: state.getIn(['water', 'isFilteredRiverLevel']),
  }
}

const mapActionCreators = {
  getRiverLevel: waterActions.getRiverLevel
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevelGraphic))
