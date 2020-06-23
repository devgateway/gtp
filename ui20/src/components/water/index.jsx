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

const waterGraphicsDef = [
  new GraphicDef('indicators.table.rainseason.title', 'masked-icon icon-table', RainSeasonGraphic),
  // 2. Pluviométrie nationale/ Pluviometry at national level as two maps
  new GraphicDef('indicators.chart.rainfall.title', 'masked-icon icon-barchart', RainfallGraphic),
  new GraphicDef('indicators.chart.dryseason.title', 'masked-icon icon-linechart', DrySeasonGraphic),
  new GraphicDef('indicators.chart.riverlevel.title', 'masked-icon icon-linechart', RiverLevelGraphic),
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
