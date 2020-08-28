import Immutable from "immutable"
import AgricultureConfig from "../../modules/entities/config/AgricultureConfig"
import ProductPriceChart from "../../modules/entities/product/price/ProductPriceChart"
import ProductQuantityChart from "../../modules/entities/product/quantity/ProductQuantityChart"

export const MARKET_AND_AGRICULTURE = 'MARKET_AND_AGRICULTURE'
const MARKET_AND_AGRICULTURE_PENDING = 'MARKET_AND_AGRICULTURE_PENDING'
const MARKET_AND_AGRICULTURE_FULFILLED = 'MARKET_AND_AGRICULTURE_FULFILLED'
const MARKET_AND_AGRICULTURE_REJECTED = 'MARKET_AND_AGRICULTURE_REJECTED'
export const CHANGE_MARKET_PRICE_FILTER = 'CHANGE_MARKET_PRICE_FILTER'
export const FILTER_MARKET_PRICE = 'FILTER_MARKET_PRICE'
const FILTER_MARKET_PRICE_PENDING = 'FILTER_MARKET_PRICE_PENDING'
const FILTER_MARKET_PRICE_FULFILLED = 'FILTER_MARKET_PRICE_FULFILLED'
const FILTER_MARKET_PRICE_REJECTED = 'FILTER_MARKET_PRICE_REJECTED'
export const CHANGE_MARKET_QUANTITY_FILTER = 'CHANGE_MARKET_QUANTITY_FILTER'
export const FILTER_MARKET_QUANTITY = 'FILTER_MARKET_QUANTITY'
const FILTER_MARKET_QUANTITY_PENDING = 'FILTER_MARKET_QUANTITY_PENDING'
const FILTER_MARKET_QUANTITY_FULFILLED = 'FILTER_MARKET_QUANTITY_FULFILLED'
const FILTER_MARKET_QUANTITY_REJECTED = 'FILTER_MARKET_QUANTITY_REJECTED'


const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  data: {
    agricultureConfig: AgricultureConfig,
    productPriceChart: ProductPriceChart,
    productQuantityChart: ProductQuantityChart,
  },
  isPriceDataLoading: false,
  isPriceDataLoaded: false,
  isQuantityDataLoading: false,
  isQuantityDataLoaded: false,
})

export default (state = initialState, action) => {
  const { payload, data, path } = action
  switch (action.type) {
    case MARKET_AND_AGRICULTURE_PENDING:
      return state.set('isLoading', true).set('error', null)
    case MARKET_AND_AGRICULTURE_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', payload)
        .set('isPriceDataLoaded', true).set('isQuantityDataLoaded', true)
    case MARKET_AND_AGRICULTURE_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    case CHANGE_MARKET_PRICE_FILTER:
      return state.setIn(['data', 'productPriceChart', 'filter', ...path], data)
    case FILTER_MARKET_PRICE_PENDING:
      return state.set('isPriceDataLoading', true).set('isPriceDataLoaded', false).set('error', null)
    case FILTER_MARKET_PRICE_FULFILLED:
      return state.set('isPriceDataLoading', false).set('isPriceDataLoaded', true)
        .setIn(['data', 'productPriceChart', 'data'], payload)
    case FILTER_MARKET_PRICE_REJECTED:
      return state.set('isPriceDataLoading', false).set('isPriceDataLoaded', false).set('error', payload)
    case CHANGE_MARKET_QUANTITY_FILTER:
      return state.setIn(['data', 'productQuantityChart', 'filter', ...path], data)
    case FILTER_MARKET_QUANTITY_PENDING:
      return state.set('isQuantityDataLoading', true).set('isQuantityDataLoaded', false).set('error', null)
    case FILTER_MARKET_QUANTITY_FULFILLED:
      return state.set('isQuantityDataLoading', false).set('isQuantityDataLoaded', true)
        .setIn(['data', 'productQuantityChart', 'data'], payload)
    case FILTER_MARKET_QUANTITY_REJECTED:
      return state.set('isQuantityDataLoading', false).set('isQuantityDataLoaded', false).set('error', payload)
    default:
      return state
  }
}
