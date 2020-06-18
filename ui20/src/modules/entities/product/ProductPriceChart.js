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

export const fromApi = ({config, filter, data}) => {
  ProductPriceChart.config = new ProductPriceConfig(config)
  Object.assign(ProductPriceChart.filter, filter)
  ProductPriceChart.data = new ProductPriceData(data, filter.year)
  return ProductPriceChart
}


