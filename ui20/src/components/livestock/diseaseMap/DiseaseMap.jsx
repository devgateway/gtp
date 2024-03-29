import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {Map} from "react-leaflet"
import {connect} from "react-redux"
import DiseaseQuantityMapDTO from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapDTO"
import "../../market/marketLocation/marketMap.scss"
import CountriesLayer from "../../common/map/CountriesLayer"
import {SENEGAL_CENTER_LAT_LNG, SENEGAL_ZOOM_LEVEL} from "../../common/map/MapUtils"
import RegionLayer from "../../common/map/RegionLayer"
import DiseaseLegend from "./DiseaseLegend"
import DiseaseRegionStyle from "./DiseaseRegionStyle"
import "../../common/map/map.scss"
import "./diseaseMap.scss"
import {ADM_BASE_STYLE} from "./DiseaseStyle"

class DiseaseMap extends Component {
  static propTypes = {
    diseaseMapDTO: PropTypes.instanceOf(DiseaseQuantityMapDTO).isRequired,
  }

  render() {
    const {intl} = this.props
    const diseaseMapDTO: DiseaseQuantityMapDTO = this.props.diseaseMapDTO
    const regionFeatureStyle = new DiseaseRegionStyle(diseaseMapDTO, intl)
    const attribution = intl.formatMessage({ id: "indicators.map.disease.source" })

    return (
      <div className="png exportable disease-map">
        <DiseaseLegend min={diseaseMapDTO.minQuantity} max={diseaseMapDTO.maxQuantity} />
        <div className="map-container">
          <Map className="map black-tooltip" zoom={SENEGAL_ZOOM_LEVEL} center={SENEGAL_CENTER_LAT_LNG} zoomControl={true} >
            <CountriesLayer countryFeatureStyle={countryStyle} />
            <RegionLayer
              regionFeatureStyle={regionFeatureStyle.getStyle}
              onEachFeature={regionFeatureStyle.getRegionPopup}
              attribution={attribution}/>
          </Map>
        </div>
      </div>
    )
  }
}

const countryStyle = {
  ...ADM_BASE_STYLE,
  color: "#e6e7ec",
}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DiseaseMap))
