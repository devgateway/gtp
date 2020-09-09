import Immutable from "immutable"
import LivestockConfig from "../../modules/entities/config/LivestockConfig"
import DiseaseQuantityChart from "../../modules/entities/diseaseSituation/DiseaseQuantityChart"

export const LIVESTOCK = 'LIVESTOCK'
const LIVESTOCK_PENDING = 'LIVESTOCK_PENDING'
const LIVESTOCK_FULFILLED = 'LIVESTOCK_FULFILLED'
const LIVESTOCK_REJECTED = 'LIVESTOCK_REJECTED'
export const CHANGE_DISEASE_QUANTITY_FILTER = 'CHANGE_DISEASE_QUANTITY_FILTER'
export const FILTER_DISEASE_QUANTITY = 'FILTER_DISEASE_QUANTITY'
const FILTER_DISEASE_QUANTITY_PENDING = 'FILTER_DISEASE_QUANTITY_PENDING'
const FILTER_DISEASE_QUANTITY_FULFILLED = 'FILTER_DISEASE_QUANTITY_FULFILLED'
const FILTER_DISEASE_QUANTITY_REJECTED = 'FILTER_DISEASE_QUANTITY_REJECTED'


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
    case CHANGE_DISEASE_QUANTITY_FILTER:
      return state.setIn(['data', 'diseaseQuantityChart', 'filter', ...path], data)
    case FILTER_DISEASE_QUANTITY_PENDING:
      return state.set('isDiseaseQuantityLoading', true).set('isDiseaseQuantityLoaded', false).set('error', null)
    case FILTER_DISEASE_QUANTITY_FULFILLED:
      return state.set('isDiseaseQuantityLoading', false).set('isDiseaseQuantityLoaded', true)
        .setIn(['data', 'diseaseQuantityChart', 'data'], payload)
    case FILTER_DISEASE_QUANTITY_REJECTED:
      return state.set('isDiseaseQuantityLoading', false).set('isDiseaseQuantityLoaded', false).set('error', payload)
    default:
      return state
  }
}
