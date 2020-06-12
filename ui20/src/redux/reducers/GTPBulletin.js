import Immutable from "immutable"
import BulletinReport from "../../modules/entities/bulletins/BulletinReport"

export const BULLETIN = 'BULLETIN'
const BULLETIN_PENDING = 'BULLETIN_PENDING'
const BULLETIN_FULFILLED = 'BULLETIN_FULFILLED'
const BULLETIN_REJECTED = 'BULLETIN_REJECTED'
export const FILTER_BULLETIN = 'FILTER_BULLETIN'

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  data: BulletinReport,
})

export default (state = initialState, action) => {
  const { payload, data } = action
  switch (action.type) {
    case BULLETIN_PENDING:
      return state.set('isLoading', true).set('error', null)
    case BULLETIN_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', payload)
    case BULLETIN_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    case FILTER_BULLETIN:
      return state.setIn(['data', 'filter', 'years'], data)
    default:
      return state
  }
}
