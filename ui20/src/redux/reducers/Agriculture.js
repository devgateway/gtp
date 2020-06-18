import Immutable from "immutable"
import AgricultureConfig from "../../modules/entities/config/AgricultureConfig"
import ProductPriceChart from "../../modules/entities/product/ProductPriceChart"

export const MARKET_AND_AGRICULTURE = 'MARKET_AND_AGRICULTURE'
const MARKET_AND_AGRICULTURE_PENDING = 'MARKET_AND_AGRICULTURE_PENDING'
const MARKET_AND_AGRICULTURE_FULFILLED = 'MARKET_AND_AGRICULTURE_FULFILLED'
const MARKET_AND_AGRICULTURE_REJECTED = 'MARKET_AND_AGRICULTURE_REJECTED'

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  data: {
    agricultureConfig: AgricultureConfig,
    productPriceChart: ProductPriceChart,
  }
})

export default (state = initialState, action) => {
  const { payload, data, path } = action
  switch (action.type) {
    case MARKET_AND_AGRICULTURE_PENDING:
        return state.set('isLoading', true).set('error', null)
    case MARKET_AND_AGRICULTURE_FULFILLED:
        return state.set('isLoading', false).set('isLoaded', true).set('data', payload)
    case MARKET_AND_AGRICULTURE_REJECTED:
        return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    default:
      return state
  }
}
