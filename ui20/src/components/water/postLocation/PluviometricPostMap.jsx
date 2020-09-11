import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {Map, TileLayer} from 'react-leaflet';
import {connect} from "react-redux"
import PluviometricPostMapDTO from "../../../modules/graphic/water/postMap/PluviometricPostMapDTO"
import CountryBorderLayer from "../../common/map/CountryBorderLayer"
import {SENEGAL_CENTER_LAT_LNG, SENEGAL_ZOOM_LEVEL} from "../../common/map/MapUtils"
import "../../common/map/map.scss"
import PluviometricPostLayer from "./PluviometricPostLayer"
import PluviometricPostMapLegned from "./PluviometricPostMapLegned"

class PluviometricPostMap extends Component {
  static propTypes = {
    postMapDTO: PropTypes.instanceOf(PluviometricPostMapDTO).isRequired,
    worldMapAttribution: PropTypes.string.isRequired,
  }

  render() {
    const {intl, worldMapAttribution} = this.props

    return (
      <div className="png exportable">
        <PluviometricPostMapLegned intl={intl} />
        <div className="map-container">
          <Map className="map black-tooltip" zoom={SENEGAL_ZOOM_LEVEL} center={SENEGAL_CENTER_LAT_LNG} zoomControl={true}>
            <TileLayer
              url="https://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}"
              attribution={worldMapAttribution}/>
            <CountryBorderLayer/>
            <PluviometricPostLayer {...this.props} />
          </Map>
        </div>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    worldMapAttribution: state.getIn(['app', 'data', 'worldMapAttribution']),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(PluviometricPostMap))
