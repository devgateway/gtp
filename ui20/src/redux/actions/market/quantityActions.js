import ProductQuantityChartBuilder from "../../../modules/graphic/market/productQuantity/ProductQuantityChartBuilder"
import ProductQuantityChartDTO from "../../../modules/graphic/market/productQuantity/ProductQuantityChartDTO"

export const getProductQuantities = (): ProductQuantityChartDTO => (dispatch, getState) => {
  const { productQuantityChart, agricultureConfig } = getState().getIn(['agriculture', 'data'])
  const builder = new ProductQuantityChartBuilder(productQuantityChart, agricultureConfig)

  return {
    data: builder.build(),
  }
}
