import {Layer} from "leaflet/dist/leaflet-src.esm"
import React from "react"
import ReactDOM from "react-dom"
import Region from "../../../modules/entities/Region"
import DiseaseQuantityMapDTO from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapDTO"
import DiseaseMapRegionPopup from "./DiseaseMapRegionPopup"
import {ADM_BASE_STYLE} from "./DiseaseStyle"

export default class DiseaseRegionStyle {
  diseaseMapDTO: DiseaseQuantityMapDTO
  qRange: number

  constructor(diseaseMapDTO: DiseaseQuantityMapDTO, intl) {
    this.diseaseMapDTO = diseaseMapDTO
    this.qRange = diseaseMapDTO.maxQuantity - diseaseMapDTO.minQuantity
    this.getStyle = this.getStyle.bind(this)
    this.getRegionPopup = this.getRegionPopup.bind(this, intl, this.diseaseMapDTO)
  }

  getStyle(feature) {
    const featureRegionName = feature.properties.NAME_1
    const region: Region = this.diseaseMapDTO.findRegion(featureRegionName)
    if (!region) {
      console.error(`No matching region found for feature: '${featureRegionName}'`)
      return ADM_BASE_STYLE
    }
    const qByMonth = this.diseaseMapDTO.diseaseQuantityData.quantityByRegionIdByMonth.get(region.id)
    const q = qByMonth && qByMonth.get(this.diseaseMapDTO.month)
    if (!q) {
      return ADM_BASE_STYLE
    }
    const color = this._getColor(q)
    return {
      ...ADM_BASE_STYLE,
      fillColor: `rgb(${color.r}, ${color.g}, ${color.b})`
    }
  }

  _getColor(q: number) {
    const pctUpper = (q - this.diseaseMapDTO.minQuantity) / this.qRange
    const pctLower = 1 - pctUpper
    return {
      r: Math.floor(minColor.r * pctLower + maxColor.r * pctUpper),
      g: Math.floor(minColor.g * pctLower + maxColor.g * pctUpper),
      b: Math.floor(minColor.b * pctLower + maxColor.b * pctUpper)
    }
  }

  getRegionPopup(intl, diseaseMapDTO: DiseaseQuantityMapDTO, feature, layer: Layer) {
    layer.bindTooltip(() => {
        const div = document.createElement('div');
        ReactDOM.render(
          <DiseaseMapRegionPopup name={feature.properties.NAME_1} diseaseMapDTO={diseaseMapDTO} intl={intl} />, div)
        return div.innerHTML
      },
      {
        closeButton: false,
        permanent: false,
        direction: "top",
        opactity: 1,
        maxWidth: 500,
      }).openTooltip()
  }
}

const minColor = {
  r: 0xFC,
  g: 0xF3,
  b: 0xE3
}
const maxColor = {
  r: 0xFB,
  g: 0x91,
  b: 0x13
}
