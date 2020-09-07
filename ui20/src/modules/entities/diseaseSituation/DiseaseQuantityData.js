import {getOrDefaultMap} from "../../utils/DataUtilis"

export default class DiseaseQuantityData {
  quantityByRegionIdByMonth: Map<number, Map<number, number>>
  lastMonth: number

  constructor(apiQuantities = []) {
    this.quantityByRegionIdByMonth = new Map()
    this.lastMonth = 0

    apiQuantities.forEach(({regionId, month, quantity}) => {
      getOrDefaultMap(this.quantityByRegionIdByMonth, regionId).set(month, quantity)
      this.lastMonth = Math.max(this.lastMonth, month)
    })
  }
}

