import DiseaseQuantityConfig from "./DiseaseQuantityConfig"
import DiseaseQuantityData from "./DiseaseQuantityData"
import DiseaseQuantityFilter from "./DiseaseQuantityFilter"

const DiseaseQuantityChart: {
  config: DiseaseQuantityConfig,
  filter: DiseaseQuantityFilter,
  data: DiseaseQuantityData,
} = {
  filter: DiseaseQuantityFilter
}

export default DiseaseQuantityChart

export const diseaseQuantityFromApi = ({config, filter, data} = {}) => {
  DiseaseQuantityChart.config = new DiseaseQuantityConfig(config.years)
  DiseaseQuantityChart.data = diseaseQuantityDataFromApi(data)
  DiseaseQuantityChart.filter.year = filter.year
  DiseaseQuantityChart.filter.month = DiseaseQuantityChart.data.lastMonth
  DiseaseQuantityChart.filter.diseaseId = filter.diseaseId
  return DiseaseQuantityChart
}

export const diseaseQuantityDataFromApi = ({quantities} = {}) => {
  return new DiseaseQuantityData(quantities)
}
