import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as rainMapActions from "../../../redux/actions/water/rainMapActions"
import Graphic from "../../common/graphic/Graphic"
import AnomalyRainMap from "./AnomalyRainMap"
import CumulativeRainMap from "./CumulativeRainMap"
import "./rainfalMap.scss"
import RainfallMapProperties from "./RainfallMapProperties"

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
    const {rainMap, isFilteredRainMap} = this.props

    return (
      <Graphic
        id="anchor.indicator.water.rainMap" titleId="indicators.map.rainMap.title"
        sourceId="indicators.map.rainMap.source" className="map-graphic rainfall-map">
        {isFilteredRainMap && <RainfallMapProperties filter={rainMap.filter} setting={rainMap.setting} />}
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
