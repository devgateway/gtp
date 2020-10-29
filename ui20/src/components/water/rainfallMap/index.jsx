import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import RainMap from "../../../modules/entities/rainfallMap/RainMap"
import * as rainMapActions from "../../../redux/actions/water/rainMapActions"
import Graphic from "../../common/graphic/Graphic"
import AnomalyRainMap from "./AnomalyRainMap"
import CumulativeRainMap from "./CumulativeRainMap"
import "./rainfalMap.scss"

class RainMapsGraphic extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
  }

  constructor(props) {
    super(props)

    this.state = {
      isLocalStateChange: false,
      isReloadMapLayers: true,
    }
  }

  static getDerivedStateFromProps(props, state) {
    const {isLocalStateChange, isReloadMapLayers} = state
    if (isLocalStateChange) {
      return {
        isLocalStateChange: false
      }
    }

    const {isFilteredRainMap, onLoadAll} = props
    if (isFilteredRainMap && isReloadMapLayers) {
      onLoadAll()
      return {
        isReloadMapLayers: false,
      }
    }
    return null
  }

  render() {
    const rainMap: RainMap = this.props.rainMap

    return (
      <Graphic
        id="anchor.indicator.water.rainMap" titleId="indicators.map.rainMap.title"
        sourceId="indicators.map.rainMap.source" className="map-graphic rainfall-map">
        <div className="two-maps">
          <CumulativeRainMap />
          <AnomalyRainMap />
        </div>
      </Graphic>
    )
  }
}


const mapStateToProps = state => {
  return {
    isFilteringRainMap: state.getIn(['water', 'isFilteringRainMap']),
    isFilteredRainMap: state.getIn(['water', 'isFilteredRainMap']),
    rainMap: state.getIn(['water', 'data', 'rainMap']),
  }
}

const mapActionCreators = {
  onLoadAll: rainMapActions.loadRainMapData,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainMapsGraphic))
