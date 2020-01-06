import * as api from '../api'


import Immutable from 'immutable'

const LOAD_GIS_DATA = 'LOAD_GIS_DATA'
const LOAD_GIS_DATA_DONE = 'LOAD_GIS_DATA_DONE'
const LOAD_GIS_DATA_ERROR = 'LOAD_GIS_DATA_ERROR'

const initialState = Immutable.Map()


export const loadGISData = () => (dispatch, getState) => {

  dispatch({
    type: LOAD_GIS_DATA
  })
  api.getGISData().then(data => {

      dispatch({type: LOAD_GIS_DATA_DONE, data})
    }).catch(error => {
      dispatch({type: LOAD_GIS_DATA_ERROR,error})
    })
}


export default (state = initialState, action) => {
  switch (action.type) {
    case LOAD_GIS_DATA: {
      return state.setIn(['status', 'loading'], true)
    }
    case LOAD_GIS_DATA_DONE: {
      const {data} = action
      return state.setIn(["data"], data).setIn(['status', 'loading'], false)
    }
    case LOAD_GIS_DATA_ERROR: {
      return state.setIn(['status', 'loading'], false).setIn(['status', 'error'], action.error)
    }

    default:
      return state
  }
}
