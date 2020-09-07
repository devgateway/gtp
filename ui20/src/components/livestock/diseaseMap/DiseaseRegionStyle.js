import Region from "../../../modules/entities/Region"
import DiseaseQuantityMapDTO from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapDTO"
import {ADM_BASE_STYLE} from "./DiseaseStyle"

export default class DiseaseRegionStyle {
  diseaseMapDTO: DiseaseQuantityMapDTO
  qRange: number

  constructor(diseaseMapDTO: DiseaseQuantityMapDTO) {
    this.diseaseMapDTO = diseaseMapDTO
    this.qRange = diseaseMapDTO.maxQuantity - diseaseMapDTO.minQuantity
    this.getStyle = this.getStyle.bind(this)
    this.getRegionPopup = this.getRegionPopup.bind(this)
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

  getRegionPopup(feature, layer) {
    layer.bindTooltip(feature.properties.NAME_1,
      {
        permanent: false,
        direction:"center",
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
