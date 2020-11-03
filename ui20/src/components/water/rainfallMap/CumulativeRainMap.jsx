import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import CumulativeRainMapLayers from "../../../modules/graphic/water/rainfallMap/CumulativeRainMapLayers"
import {RainfallMap} from "./RainfallMap"
import {getRainFeatureStyle, onEachPolygonFeature} from "./RainfallMapHelper"
import {RainfallMapLegend} from "./RainfallMapLegend"

class CumulativeRainMap extends Component {

  render() {
    const {polyline, polygon, intl} = this.props
    // TODO process no data
    if (!polyline || !polygon) {
      return "No data"
    }
    const layers = new CumulativeRainMapLayers(polyline, polygon)
    const rainFeatureStyle = getRainFeatureStyle(layers.colorsMap)
    const unit = intl.formatMessage({id: "indicators.map.rainMap.unit.cumul"})

    return (
      <RainfallMap
        titleId="indicators.map.rainMap.subtitle.cumul"
        polyline={layers.polyline}
        polygon={layers.polygon}
        onEachPolygonFeature={onEachPolygonFeature(layers.colorsMap, unit)}
        onEachPolylineFeature={() => null}
        polygonFeatureStyle={rainFeatureStyle}
        polylineFeatureStyle={rainFeatureStyle}>
        <RainfallMapLegend colorsMap={layers.colorsMap} unit={unit} legendLabelFunc={getCumulativeLegendLabel} />
      </RainfallMap>
    )
  }

}

const getCumulativeLegendLabel = (grade, unit, idx, total) => {
  if (idx === 0) {
    return <div className="legend-label">{grade}{unit}</div>
  } else if (idx === total - 1) {
    return <div className="legend-label">{grade}{unit}</div>
  } else if (idx === Math.trunc(total / 2)) {
    return <div className="legend-label">{grade}{unit}</div>
  }
  return ""
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
