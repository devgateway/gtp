import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {Map} from "react-leaflet"
import {connect} from "react-redux"
import DiseaseQuantityMapDTO from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapDTO"
import "../../market/marketLocation/marketMap.scss"
import RegionLayer from "../../common/map/RegionLayer"
import DiseaseRegionStyle from "./DiseaseRegionStyle"
import "../../common/map/map.scss"

class DiseaseMap extends Component {
  static propTypes = {
    diseaseMapDTO: PropTypes.instanceOf(DiseaseQuantityMapDTO).isRequired,
  }

  render() {
    const {intl, diseaseMapDTO} = this.props
    const regionFeatureStyle = new DiseaseRegionStyle(diseaseMapDTO)
    const attribution = intl.formatMessage({ id: "indicators.map.disease.source" })

    return (
      <div className="png exportable">
        <div className="map-container">
          <Map className="map" zoom={7} center={[14.4974, -14.4545887]} zoomControl={true} >
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

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DiseaseMap))
