import DiseaseQuantityData from "../../../entities/diseaseSituation/DiseaseQuantityData"
import Region from "../../../entities/Region"

export default class DiseaseQuantityMapDTO {
  diseaseQuantityData: DiseaseQuantityData
  year: number
  month: number
  regions: Array<Region>
  minQuantity: number
  maxQuantity: number

  constructor(diseaseQuantityData: DiseaseQuantityData, year: number, month: number, regions: Array<Region>,
    minQuantity: number, maxQuantity: number) {
    this.diseaseQuantityData = diseaseQuantityData
    this.year = year
    this.month = month
    this.regions = regions
    this.minQuantity = minQuantity
    this.maxQuantity = maxQuantity
    this.hasData = !!(month && Array.from(diseaseQuantityData.quantityByRegionIdByMonth.values())
      .map((qByM: Map) => qByM.get(month)).filter(q => q !== null && q !== undefined ).length)
  }

  findRegion(name: string = ''): Region {
    const normalizedName = name.trim()
    return this.regions.find(r => r.name.localeCompare(normalizedName, 'fr', { sensitivity: 'base'}) === 0)
  }
}
