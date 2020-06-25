import AgricultureConfig from "../../config/AgricultureConfig"
import ProductQuantityConfig from "./ProductQuantityConfig"
import ProductQuantityData from "./ProductQuantityData"
import ProductQuantityFilter from "./ProductQuantityFilter"

const ProductQuantityChart: {
  config: ProductQuantityConfig,
  filter: ProductQuantityFilter,
  data: ProductQuantityData,
} = {
  filter: ProductQuantityFilter,
}

export default ProductQuantityChart

export const quantityFromApi = ({config, filter, data} = {}, agricultureConfig: AgricultureConfig) => {
  ProductQuantityChart.config = new ProductQuantityConfig(config)
  Object.assign(ProductQuantityChart.filter, filter || {})
  ProductQuantityChart.data = quantityDataFromApi(data, filter, agricultureConfig)
  return ProductQuantityChart
}

export const quantityDataFromApi = (data, filter, agricultureConfig: AgricultureConfig) => {
  return  new ProductQuantityData(data, filter.year, agricultureConfig.productsById)
}

