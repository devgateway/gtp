import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import {
  INDICATOR_WATER_DRY_SEASON, INDICATOR_WATER_POST_MAP,
  INDICATOR_WATER_RAIN_MAP,
  INDICATOR_WATER_RAIN_SEASON,
  INDICATOR_WATER_RAINFALL, INDICATOR_WATER_RIVER_LEVEL
} from "../../modules/entities/FMConstants"
import * as waterActions from "../../redux/actions/waterActions"
import GraphicPage, {GraphicDef} from "../common/graphic/GraphicPage"
import DrySeasonGraphic from "./drySequence"
import RainfallGraphic from "./rainfall"
import RainMapsGraphic from "./rainfallMap"
import RainSeasonGraphic from "./rainseason"
import RiverLevelGraphic from "./river"
import PluviometricPostLocationGraphic from "./postLocation"

const waterGraphicsDef = [
  new GraphicDef('indicators.table.rainseason.title', 'masked-icon icon-table', RainSeasonGraphic, INDICATOR_WATER_RAIN_SEASON),
  new GraphicDef('indicators.map.rainMap.title', 'masked-icon icon-barchart', RainMapsGraphic, INDICATOR_WATER_RAIN_MAP),
  new GraphicDef('indicators.chart.rainfall.title', 'masked-icon icon-barchart', RainfallGraphic, INDICATOR_WATER_RAINFALL),
  new GraphicDef('indicators.chart.dryseason.title', 'masked-icon icon-linechart', DrySeasonGraphic, INDICATOR_WATER_DRY_SEASON),
  new GraphicDef('indicators.map.post.title', 'masked-icon icon-barchart', PluviometricPostLocationGraphic, INDICATOR_WATER_POST_MAP),
  new GraphicDef('indicators.chart.riverlevel.title', 'masked-icon icon-linechart', RiverLevelGraphic, INDICATOR_WATER_RIVER_LEVEL),
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
