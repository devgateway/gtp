import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../redux/actions/waterActions"
import GraphicPage, {GraphicDef} from "../common/graphic/GraphicPage"
import DrySeasonGraphic from "./drySequence"
import RainfallGraphic from "./rainfall"
import RainSeasonGraphic from "./rainseason"
import RiverLevelGraphic from "./river"
import PluviometricPostLocationGraphic from "./postLocation"

const waterGraphicsDef = [
  new GraphicDef('indicators.table.rainseason.title', 'masked-icon icon-table', RainSeasonGraphic),
  // 2. Pluviométrie nationale/ Pluviometry at national level as two maps
  new GraphicDef('indicators.chart.rainfall.title', 'masked-icon icon-barchart', RainfallGraphic),
  new GraphicDef('indicators.chart.dryseason.title', 'masked-icon icon-linechart', DrySeasonGraphic),
  new GraphicDef('indicators.map.post.title', 'masked-icon icon-barchart', PluviometricPostLocationGraphic),
  new GraphicDef('indicators.chart.riverlevel.title', 'masked-icon icon-linechart', RiverLevelGraphic),
]

class WaterResources extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    return <GraphicPage graphicsDefs={waterGraphicsDef} />
  }

}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  onLoadAll: waterActions.loadAllWaterData,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(WaterResources))
