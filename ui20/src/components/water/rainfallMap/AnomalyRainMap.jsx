import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import AnomalyRainMapLayers from "../../../modules/graphic/water/rainfallMap/AnomalyRainMapLayers"
import * as rainfallMapCss from "../../../modules/graphic/water/rainfallMap/rainfallMapCss"
import {RainfallMap} from "./RainfallMap"
import {getRainFeatureStyle} from "./RainfallMapHelper"

class AnomalyRainMap extends Component {

  render() {
    const {polyline, polygon} = this.props
    // TODO process no data
    if (!polyline || !polygon) {
      return "No data"
    }
    const layers = new AnomalyRainMapLayers(polyline, polygon)
    const rainFeatureStyle = getRainFeatureStyle(layers.colorsMap, rainfallMapCss.defaultAnomalyColor)

    return (
      <RainfallMap
        titleId="indicators.map.rainMap.subtitle.anomaly"
        polyline={layers.polyline}
        polygon={layers.polygon}
        rainFeatureStyle={rainFeatureStyle}>
      </RainfallMap>
    )
  }

}

const mapStateToProps = state => {
  return {
    polyline: state.getIn(['water', 'data', 'rainMap', 'data', C.ABNORMAL_POLYLINE]),
    polygon: state.getIn(['water', 'data', 'rainMap', 'data', C.ABNORMAL_POLYGON]),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(AnomalyRainMap))
