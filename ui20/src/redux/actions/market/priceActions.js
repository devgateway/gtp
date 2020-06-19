import ProductPriceChartBuilder from "../../../modules/graphic/market/productPrice/ProductPriceChartBuilder"
import ProductPriceChartDTO from "../../../modules/graphic/market/productPrice/ProductPriceChartDTO"

export const getProductPrices = (): ProductPriceChartDTO => (dispatch, getState) => {
  const { productPriceChart, agricultureConfig } = getState().getIn(['agriculture', 'data'])
  const builder = new ProductPriceChartBuilder(productPriceChart, agricultureConfig)

  return {
    data: builder.build(),
  }
}
