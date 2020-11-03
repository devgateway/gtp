import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import AnomalyRainMapLayers from "../../../modules/graphic/water/rainfallMap/AnomalyRainMapLayers"
import * as rainfallMapCss from "../../../modules/graphic/water/rainfallMap/rainfallMapCss"
import {RainfallMap} from "./RainfallMap"
import {getAnomalyPolylineFeatureStyle, getRainFeatureStyle, onEachPolygonFeature} from "./RainfallMapHelper"
import {RainfallMapLegend} from "./RainfallMapLegend"

class AnomalyRainMap extends Component {

  render() {
    const {polyline, polygon, intl} = this.props
    // TODO process no data
    if (!polyline || !polygon) {
      return "No data"
    }
    const layers = new AnomalyRainMapLayers(polyline, polygon)
    const unit = intl.formatMessage({id: "indicators.map.rainMap.unit.anomaly"})

    return (
      <RainfallMap
        titleId="indicators.map.rainMap.subtitle.anomaly"
        polyline={layers.polyline}
        polygon={layers.polygon}
        onEachPolygonFeature={onEachPolygonFeature(layers.colorsMap, unit)}
        polygonFeatureStyle={getRainFeatureStyle(layers.colorsMap)}
        polylineFeatureStyle={getAnomalyPolylineFeatureStyle(layers.colorsMap)}>
        <RainfallMapLegend colorsMap={rainfallMapCss.anomalyColorsMap} unit={unit} legendLabelFunc={getAnomalyLegendLabel} />
      </RainfallMap>
    )
  }

}


const getAnomalyLegendLabel = (grade, unit, idx, total) => {
  if (idx === 0) {
    return <div className="legend-label">0{unit}</div>
  } else if (idx === total - 1) {
    return <div className="legend-label">200+{unit}</div>
  } else if (idx === Math.trunc(total / 2)) {
    return <div className="legend-label move-left"><FormattedMessage id="indicators.map.rainMap.legend.anomaly.normal"/></div>
  } else if (idx === Math.trunc(total / 4)) {
    return <div className="legend-label move-left"><FormattedMessage id="indicators.map.rainMap.legend.anomaly.deficient"/></div>
  } else if (idx === total - 1 - Math.trunc(total / 4)) {
    return <div className="legend-label move-left"><FormattedMessage id="indicators.map.rainMap.legend.anomaly.excessive"/></div>
  }
  return ""
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
