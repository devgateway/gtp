import * as api from "../../../modules/api"
import {priceDataFromApi} from "../../../modules/entities/product/price/ProductPriceChart"
import ProductPriceFilter from "../../../modules/entities/product/price/ProductPriceFilter"
import ProductPriceChartBuilder from "../../../modules/graphic/market/productPrice/ProductPriceChartBuilder"
import ProductPriceChartDTO from "../../../modules/graphic/market/productPrice/ProductPriceChartDTO"
import {CHANGE_MARKET_PRICE_FILTER, FILTER_MARKET_PRICE} from "../../reducers/Agriculture"

export const getProductPrices = (): ProductPriceChartDTO => (dispatch, getState) => {
  const { productPriceChart, agricultureConfig } = getState().getIn(['agriculture', 'data'])
  const builder = new ProductPriceChartBuilder(productPriceChart, agricultureConfig)

  return {
    data: builder.build(),
  }
}

export const setProductPriceFilter = (path, data) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_MARKET_PRICE_FILTER,
    path,
    data
  })
  const agricultureConfig = getState().getIn(['agriculture', 'data', 'agricultureConfig'])
  const filter: ProductPriceFilter = getState().getIn(['agriculture', 'data', 'productPriceChart', 'filter'])
  const {year, productId, marketId} = filter

  return dispatch({
    type: FILTER_MARKET_PRICE,
    payload: api.getProductPrices(year, productId, marketId).then(data => priceDataFromApi(data, filter, agricultureConfig))
  })
}
