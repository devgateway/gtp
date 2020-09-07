import Immutable from "immutable"
import LivestockConfig from "../../modules/entities/config/LivestockConfig"
import DiseaseQuantityChart from "../../modules/entities/diseaseSituation/DiseaseQuantityChart"

export const LIVESTOCK = 'LIVESTOCK'
const LIVESTOCK_PENDING = 'LIVESTOCK_PENDING'
const LIVESTOCK_FULFILLED = 'LIVESTOCK_FULFILLED'
const LIVESTOCK_REJECTED = 'LIVESTOCK_REJECTED'


const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  data: {
    livestockConfig: LivestockConfig,
    diseaseQuantityChart: DiseaseQuantityChart,
  },
  isDiseaseQuantityLoading: null,
  isDiseaseQuantityLoaded: null,
})

export default (state = initialState, action) => {
  const { payload, data, path } = action
  switch (action.type) {
    case LIVESTOCK_PENDING:
      return state.set('isLoading', true).set('isDiseaseQuantityLoading', true).set('error', null)
    case LIVESTOCK_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('isDiseaseQuantityLoading', false)
        .set('isDiseaseQuantityLoaded', true).set('data', payload)
    case LIVESTOCK_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('isDiseaseQuantityLoading', false)
        .set('isDiseaseQuantityLoaded', false).set('error', payload)
    default:
      return state
  }
}
