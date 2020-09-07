import Immutable from "immutable"
import BulletinReport from "../../modules/entities/bulletins/BulletinReport"

export const BULLETIN = 'BULLETIN'
const BULLETIN_PENDING = 'BULLETIN_PENDING'
const BULLETIN_FULFILLED = 'BULLETIN_FULFILLED'
const BULLETIN_REJECTED = 'BULLETIN_REJECTED'
export const CHANGE_FILTER_BULLETIN = 'CHANGE_FILTER_BULLETIN'
export const FILTER_BULLETIN = 'FILTER_BULLETIN'
const FILTER_BULLETIN_PENDING = 'FILTER_BULLETIN_PENDING'
const FILTER_BULLETIN_FULFILLED = 'FILTER_BULLETIN_FULFILLED'
const FILTER_BULLETIN_REJECTED = 'FILTER_BULLETIN_REJECTED'

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: null,
  error: null,
  data: BulletinReport,
})

export default (state = initialState, action) => {
  const { payload, data, path } = action
  switch (action.type) {
    case BULLETIN_PENDING:
      return state.set('isLoading', true).set('error', null)
    case BULLETIN_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', payload)
    case BULLETIN_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    case CHANGE_FILTER_BULLETIN:
      return state.setIn(path, data)
    case FILTER_BULLETIN_PENDING:
      return state.set('isLoading', true).set('isLoaded', false).set('error', null).setIn(['data', 'data'], null)
    case FILTER_BULLETIN_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).setIn(['data', 'data'], payload)
    case FILTER_BULLETIN_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    default:
      return state
  }
}
