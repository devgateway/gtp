import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/entities/Constants"
import CumulativeRainMapLayers from "../../../modules/graphic/water/rainfallMap/CumulativeRainMapLayers"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import RainfallMap from "./RainfallMap"
import {getRainFeatureStyle, onEachPolygonFeature} from "./RainfallMapHelper"
import {RainfallMapLegend} from "./RainfallMapLegend"

class CumulativeRainMap extends Component {

  render() {
    const {intl} = this.props
    const unit = intl.formatMessage({id: "indicators.map.rainMap.unit.cumul"})

    return (
      <div className="png exportable">
        <div className="map-title"><FormattedMessage id="indicators.map.rainMap.subtitle.cumul"/></div>
        <CumulativeRainMapWithFallback {...this.props} unit={unit}>
          {childProps =>
            <RainfallMap {...childProps}>
              <RainfallMapLegend {...childProps} />
            </RainfallMap>
          }
        </CumulativeRainMapWithFallback>
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
  const layers = new CumulativeRainMapLayers(polyline, polygon)
  const rainFeatureStyle = getRainFeatureStyle(layers.colorsMap)

  return {
    ...layers,
    onEachPolygonFeature: onEachPolygonFeature(layers.colorsMap, unit),
    onEachPolylineFeature: () => null,
    polygonFeatureStyle: rainFeatureStyle,
    polylineFeatureStyle: rainFeatureStyle,
    unit,
    legendLabelFunc: getCumulativeLegendLabel,
  }
}
const CumulativeRainMapWithFallback = GraphicWithFallback('water',
  [['loadingRainMapLayers', C.CUMULATIVE_POLYGON], ['loadingRainMapLayers', C.CUMULATIVE_POLYLINE]],
  [['loadedRainMapLayers', C.CUMULATIVE_POLYGON], ['loadedRainMapLayers', C.CUMULATIVE_POLYLINE]],
  childPropsBuilder, hasDataFunc)


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
