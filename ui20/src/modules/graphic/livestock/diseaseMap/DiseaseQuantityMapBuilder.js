import CommonConfig from "../../../entities/config/CommonConfig"
import DiseaseQuantityChart from "../../../entities/diseaseSituation/DiseaseQuantityChart"
import Region from "../../../entities/Region"
import DiseaseQuantityMapDTO from "./DiseaseQuantityMapDTO"

export default class DiseaseQuantityMapBuilder {
  diseaseQuantityChart: DiseaseQuantityChart
  commonConfig: CommonConfig

  constructor(diseaseQuantityChart: DiseaseQuantityChart, commonConfig: CommonConfig) {
    this.diseaseQuantityChart = diseaseQuantityChart
    this.commonConfig = commonConfig
  }

  build(): DiseaseQuantityMapDTO {
    const data = this.diseaseQuantityChart.data
    const filter = this.diseaseQuantityChart.filter
    let min = undefined
    let max = 0
    this.commonConfig.regions.forEach((r: Region) => {
      const qByM = data.quantityByRegionIdByMonth.get(r.id)
      const q = (qByM && qByM.get(filter.month)) || 0
      min = min ? Math.min(min, q) : q
      max = Math.max(max, q)
    })
    return new DiseaseQuantityMapDTO(
      this.diseaseQuantityChart.data,
      filter.month,
      Array.from(this.commonConfig.regions.values()),
      min,
      max)
  }
}
