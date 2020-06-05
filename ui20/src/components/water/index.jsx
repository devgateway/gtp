import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../redux/actions/waterActions"
import GraphicPage, {GraphicDef} from "../common/GraphicPage"
import DrySeasonGraphic from "./drySequence"
import RainfallGraphic from "./rainfall"
import RainSeasonGraphic from "./rainseason"
import RiverLevelGraphic from "./river"
import "./water.scss"

const waterGraphicsDef = [
  new GraphicDef('indicators.chart.rainfall.title', 'chart bar', RainfallGraphic),
  new GraphicDef('indicators.chart.dryseason.title', 'chart area', DrySeasonGraphic),
  new GraphicDef('indicators.table.rainseason.title', 'table', RainSeasonGraphic),
  new GraphicDef('indicators.chart.riverlevel.title', 'chart area', RiverLevelGraphic),
]

class WaterResources extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    isLoaded: PropTypes.bool.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    const {isLoaded} = this.props;
    if (!isLoaded) {
      return <div />
    }
    return <GraphicPage graphicsDefs={waterGraphicsDef} />
  }

}

const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['water', 'isLoaded'])
  }
}

const mapActionCreators = {
  onLoadAll: waterActions.loadAllWaterData,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(WaterResources))
