import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import AnomalyRainMapLayers from "../../../modules/graphic/water/rainfallMap/AnomalyRainMapLayers"
import * as rainfallMapCss from "../../../modules/graphic/water/rainfallMap/rainfallMapCss"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import RainfallMap from "./RainfallMap"
import {
  getAnomalyPolylineFeatureStyle,
  getRainFeatureStyle,
  onEachPolygonFeature
} from "./RainfallMapHelper"
import {RainfallMapLegend} from "./RainfallMapLegend"

class AnomalyRainMap extends Component {

  render() {
    const {intl} = this.props
    const unit = intl.formatMessage({id: "indicators.map.rainMap.unit.anomaly"})

    return (
      <div className="png exportable">
        <div className="map-title"><FormattedMessage id="indicators.map.rainMap.subtitle.anomaly"/></div>
        <AnomalyRainMapWithFallback {...this.props} unit={unit}>
          {childProps =>
            <div className="map-and-legend-container">
              <RainfallMap {...childProps} />
              <RainfallMapLegend {...childProps} />
            </div>
          }
        </AnomalyRainMapWithFallback>
      </div>
    )
  }

}


const hasDataFunc = ({polyline, polygon} = {}) => !!(polyline && polygon)
const childPropsBuilder = (props) => {
  if (!hasDataFunc(props)) {
    return {}
  }
  const {polyline, polygon, unit} = props
  const layers = new AnomalyRainMapLayers(polyline, polygon)

  return {
    polyline: layers.polyline,
    polygon: layers.polygon,
    colorsMap: rainfallMapCss.anomalyColorsMap,
    onEachPolygonFeature: onEachPolygonFeature(layers.colorsMap, unit),
    onEachPolylineFeature: () => null,
    polygonFeatureStyle: getRainFeatureStyle(layers.colorsMap),
    polylineFeatureStyle: getAnomalyPolylineFeatureStyle(layers.colorsMap),
    unit,
    legendLabelFunc: getAnomalyLegendLabel,
  }
}
const AnomalyRainMapWithFallback = GraphicWithFallback('water',
  [['loadingRainMapLayers', C.ABNORMAL_POLYGON], ['loadingRainMapLayers', C.ABNORMAL_POLYLINE]],
  [['loadedRainMapLayers', C.ABNORMAL_POLYGON], ['loadedRainMapLayers', C.ABNORMAL_POLYLINE]],
  childPropsBuilder, hasDataFunc)

const getAnomalyLegendLabel = (grade, unit, idx, total) => {
  const lastIdx = total - 1
  if (idx === 0) {
    return <div className="legend-label">0{unit}</div>
  } else if (grade === 40) {
    return <div className="legend-label"><FormattedMessage id="indicators.map.rainMap.legend.anomaly.deficient"/></div>
  } else if (grade === 80) {
    return <div className="legend-label">{grade}{unit}</div>
  } else if (grade === 100) {
    return <div className="legend-label"><FormattedMessage id="indicators.map.rainMap.legend.anomaly.normal"/></div>
  } else if (grade === 120) {
    return <div className="legend-label move-right">{grade}{unit}</div>
  } else if (grade === 160) {
    return <div className="legend-label"><FormattedMessage id="indicators.map.rainMap.legend.anomaly.excessive"/></div>
  } else if (idx === lastIdx) {
    return <div className="legend-label">200+{unit}</div>
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
