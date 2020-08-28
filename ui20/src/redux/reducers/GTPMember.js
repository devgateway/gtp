import Immutable from "immutable"
import MemberData from "../../modules/entities/member/MemberData"

export const MEMBER = 'MEMBER'
const MEMBER_PENDING = 'MEMBER_PENDING'
const MEMBER_FULFILLED = 'MEMBER_FULFILLED'
const MEMBER_REJECTED = 'MEMBER_REJECTED'


const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: undefined,
  error: null,
  data: MemberData,
})

export default (state = initialState, action) => {
  const { payload } = action
  switch (action.type) {
    case MEMBER_PENDING:
      return state.set('isLoading', true).set('error', null)
    case MEMBER_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', payload)
    case MEMBER_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    default:
      return state
  }
}
