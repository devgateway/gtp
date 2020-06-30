import * as api from "../../../modules/api"
import {quantityDataFromApi} from "../../../modules/entities/product/quantity/ProductQuantityChart"
import ProductQuantityFilter from "../../../modules/entities/product/quantity/ProductQuantityFilter"
import ProductQuantityChartBuilder from "../../../modules/graphic/market/productQuantity/ProductQuantityChartBuilder"
import ProductQuantityChartDTO from "../../../modules/graphic/market/productQuantity/ProductQuantityChartDTO"
import {CHANGE_MARKET_QUANTITY_FILTER, FILTER_MARKET_QUANTITY} from "../../reducers/Agriculture"

export const getProductQuantities = (): ProductQuantityChartDTO => (dispatch, getState) => {
  const { productQuantityChart, agricultureConfig } = getState().getIn(['agriculture', 'data'])
  const builder = new ProductQuantityChartBuilder(productQuantityChart, agricultureConfig)

  return {
    data: builder.build(),
  }
}

export const setProductQuantityFilter = (path, data) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_MARKET_QUANTITY_FILTER,
    path,
    data
  })
  const agricultureConfig = getState().getIn(['agriculture', 'data', 'agricultureConfig'])
  const filter: ProductQuantityFilter = getState().getIn(['agriculture', 'data', 'productQuantityChart', 'filter'])
  const {year, productTypeId, marketId} = filter

  return dispatch({
    type: FILTER_MARKET_QUANTITY,
    payload: api.getProductQuantities(year, productTypeId, marketId).then(data => quantityDataFromApi(data, filter, agricultureConfig))
  })
}
