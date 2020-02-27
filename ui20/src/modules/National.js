import * as api from '../api'


import Immutable from 'immutable'

const LOAD_NATIONAL_DATA = 'LOAD_NATIONALL_DATA'
const LOAD_NATIONAL_DATA_DONE = 'LOAD_NATIONAL_DATA_DONE'
const LOAD_NATIONAL_DATA_ERROR = 'LOAD_NATIONAL_DATA_ERROR'

const initialState = Immutable.Map()


export const loadNationalIndicators = () => (dispatch, getState) => {
  
  dispatch({
    type: LOAD_NATIONAL_DATA
  })
  api.getNationalIndicators().then(data => {

      dispatch({type: LOAD_NATIONAL_DATA_DONE, data})
    }).catch(error => {
      dispatch({type: LOAD_NATIONAL_DATA_ERROR,error})
    })
}


export default (state = initialState, action) => {
  switch (action.type) {
    case LOAD_NATIONAL_DATA: {
      return state.setIn(['status', 'loading'], true)
    }
    case LOAD_NATIONAL_DATA_DONE: {
      const {data} = action
      return state.setIn(["data"], Immutable.fromJS(data)).setIn(['status', 'loading'], false)
    }
    case LOAD_NATIONAL_DATA_ERROR: {
      return state.setIn(['status', 'loading'], false).setIn(['status', 'error'], action.error)
    }

    default:
      return state
  }
}
