import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import Rainfall from "./Rainfall"
import RainfallProperties from "./RainfallProperties"

const hasDataFunc = ({rainfallDTO}) => rainfallDTO && rainfallDTO.hasData
const childPropsBuilder = (props) => props.getRain(props.intl)
const RainfallGraphicWithFallback = GraphicWithFallback('water', 'isFilteringRainfall', 'isFilteredRainfall',
  childPropsBuilder, hasDataFunc)

class RainfallGraphic extends Component {
  static propTypes = {
    getRain: PropTypes.func.isRequired,
  }

  render() {
    const {getRain, setting} = this.props;


    return (<Graphic
      id="anchor.indicator.water.rainfall" titleId="indicators.chart.rainfall.title"
      helpId="indicators.chart.rainfall.help"
      sourceId="indicators.chart.rainfall.source">
      <RainfallProperties />
      <RainfallGraphicWithFallback setting={setting} getRain={getRain}>
        {childProps => <Rainfall {...childProps} />}
      </RainfallGraphicWithFallback>
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
