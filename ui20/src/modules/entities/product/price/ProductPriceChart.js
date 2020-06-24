import AgricultureConfig from "../../config/AgricultureConfig"
import ProductPriceConfig from "./ProductPriceConfig"
import ProductPriceData from "./ProductPriceData"
import ProductPriceFilter from "./ProductPriceFilter"

const ProductPriceChart: {
  config: ProductPriceConfig,
  filter: ProductPriceFilter,
  data: ProductPriceData,
} = {
  filter: ProductPriceFilter,
}

export default ProductPriceChart

export const fromApi = ({config, filter, data}, agricultureConfig) => {
  ProductPriceChart.config = new ProductPriceConfig(config)
  Object.assign(ProductPriceChart.filter, filter)
  ProductPriceChart.data = dataFromApi(data, filter, agricultureConfig)
  return ProductPriceChart
}

export const dataFromApi = (data, filter, agricultureConfig: AgricultureConfig) => {
  return  new ProductPriceData(data, filter.year, agricultureConfig.priceTypes)
}


