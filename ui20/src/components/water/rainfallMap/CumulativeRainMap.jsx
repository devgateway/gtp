import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import CumulativeRainMapLayers from "../../../modules/graphic/water/rainfallMap/CumulativeRainMapLayers"
import {RainfallMap} from "./RainfallMap"
import {getRainFeatureStyle, onEachRainFeature} from "./RainfallMapHelper"

class CumulativeRainMap extends Component {

  render() {
    const {polyline, polygon} = this.props
    // TODO process no data
    if (!polyline || !polygon) {
      return "No data"
    }
    const layers = new CumulativeRainMapLayers(polyline, polygon)
    const rainFeatureStyle = getRainFeatureStyle(layers.colorsMap)

    return (
      <RainfallMap
        titleId="indicators.map.rainMap.subtitle.cumul"
        polyline={layers.polyline}
        polygon={layers.polygon}
        onEachFeature={onEachRainFeature(layers.colorsMap, "mm")}
        rainFeatureStyle={rainFeatureStyle}>
      </RainfallMap>
    )
  }

}


const mapStateToProps = state => {
  return {
    polyline: state.getIn(['water', 'data', 'rainMap', 'data', C.CUMULATIVE_POLYLINE]),
    polygon: state.getIn(['water', 'data', 'rainMap', 'data', C.CUMULATIVE_POLYGON]),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(CumulativeRainMap))
