import React, {Component} from "react"
import * as PropTypes from "prop-types"
import {injectIntl} from "react-intl"
import {GeoJSON, Map} from "react-leaflet"
import "../../common/map/map.scss"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import CountryBorderLayer from "../../common/map/CountryBorderLayer"
import {SENEGAL_BOUNDS, SENEGAL_CENTER_LAT_LNG} from "../../common/map/MapUtils"
import PluviometricPostLayer from "../postLocation/PluviometricPostLayer"

const regionGeoJson = require('../../../json/regions.json')

class RainfallMap extends Component {
  static propTypes = {
    polyline: PropTypes.object.isRequired,
    polygon: PropTypes.object.isRequired,
    onEachPolygonFeature: PropTypes.func.isRequired,
    onEachPolylineFeature: PropTypes.func.isRequired,
    polygonFeatureStyle: PropTypes.func.isRequired,
    polylineFeatureStyle: PropTypes.func.isRequired,
    showPluviometricPosts: PropTypes.bool.isRequired,
    isAllWaterLoaded: PropTypes.bool.isRequired,
    getPostLocation: PropTypes.func.isRequired,
  }

  constructor(props) {
    super(props)
    this.ref = React.createRef()
  }

  componentDidMount() {
    this.resizeObserver = new window.ResizeObserver((elements) => {
      setTimeout(() => this.leafletMap && this.leafletMap.fitBounds(SENEGAL_BOUNDS), 400)
    })
    this.resizeObserver.observe(this.ref.current)
  }

  componentWillUnmount() {
    this.resizeObserver.unobserve(this.ref.current)
  }

  render() {
    const {
      polyline, polygon, onEachPolygonFeature, onEachPolylineFeature, polygonFeatureStyle, polylineFeatureStyle,
      showPluviometricPosts, isAllWaterLoaded, getPostLocation, intl
    } = this.props
    const showPosts = showPluviometricPosts && isAllWaterLoaded

    return (
      <div ref={this.ref} className="map-container">
        <Map className="map black-tooltip"
             ref={(map) => this.leafletMap = map && map.leafletElement}
             center={SENEGAL_CENTER_LAT_LNG}
             dragging={false}
             keyboard={false}
             zoom={12}
             bounds={SENEGAL_BOUNDS}
             zoomControl={false}
             zoomDelta={0.05}
             zoomSnap={0.05}
             touchZoom={false}
             boxZoom={false}
             tap={false}
             doubleClickZoom={false}
             scrollWheelZoom={false}>
          <GeoJSON data={polygon} style={polygonFeatureStyle} onEachFeature={onEachPolygonFeature}/>
          <GeoJSON data={polyline} style={polylineFeatureStyle} onEachFeature={onEachPolylineFeature}/>
          <GeoJSON data={regionGeoJson} style={{
            color: "#7f7f7f",
            fill: false,
            fillOpacity: 0,
            weight: 1
          }}/>
          <CountryBorderLayer style={{
            color: "#4e4e4e",
            fill: false,
          }}/>

          {showPosts && <PluviometricPostLayer
            postMapDTO={getPostLocation().postMapDTO}
            circleColor="#364833"
            circleRadius={1}
            intl={intl}/>}

        </Map>
      </div>
    )
  }
}


const mapStateToProps = state => {
  return {
    isAllWaterLoaded: state.getIn(['water', 'isLoaded']),
    showPluviometricPosts: state.getIn(['water', 'data', 'rainMap', 'setting', 'showPluviometricPosts']),
  }
}

const mapActionCreators = {
  getPostLocation: waterActions.getPostLocation
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallMap))
