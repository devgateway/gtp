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

  getRegionPopup(intl, diseaseMapDTO: DiseaseQuantityMapDTO, feature, layer) {
    const popupOptions = {
      closeButton: false,
      permanent: true,
      direction: "center",
      opacity: 1,
      interactive: true,
      maxWidth: 500,
      closePopupOnClick: false,
      autoClose: false,
    }

    layer.mouseover = { latlng: {}}
    layer.on('mouseover', (e: Event) => {

      if (!this.lastLayer) {
        this.lastLayer = layer
      } else if (this.lastLayer !== layer) {
        closePopup(this.lastLayer)
        this.lastLayer = layer
      }
      if (isEqualLtnLng(layer.mouseover.latlng, e.latlng)) {
        return
      }
      layer.mouseover.latlng = e.latlng

      let popup = layer.getPopup()
      if (!popup) {
        popup = layer.bindPopup((layer) => {
            const div = document.createElement('div');
            ReactDOM.render(
              <DiseaseMapRegionPopup name={feature.properties.NAME_1} diseaseMapDTO={diseaseMapDTO} intl={intl}/>, div)
            return div.innerHTML
          },
          popupOptions
        ).getPopup()
        popup.locked = false
      }
      layer.openPopup()
    })

    layer.on('mouseout', (e) => {
      if (!isEqualLtnLng(layer.mouseover.latlng, e.latlng)) {
        closePopup(layer)
      }
    })
    layer.on('click', (e) => {
      const popup = layer.getPopup()
      if (popup) {
        popup.locked = !popup.locked
        if (popup.locked) {
          if (this.lockedTooltipLayer) {
            closePopup(this.lockedTooltipLayer, true)
          }
          this.lockedTooltipLayer = layer
        } else {
          this.lockedTooltipLayer = null
        }
      }
    })
  }
}

const closePopup = (layer, isForceUnlock = false) => {
  if (layer.isPopupOpen()) {
    const popup = layer.getPopup()
    if (isForceUnlock) {
      popup.locked = false
    }
    if (!popup.locked) {
      layer.closePopup()
    }
  }
}

const isEqualLtnLng = (ltnLgn1, ltnLng2) =>
  ltnLgn1 && ltnLng2 && ltnLgn1.lat === ltnLng2.lat && ltnLgn1.lng === ltnLng2.lng

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
