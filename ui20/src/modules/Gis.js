import * as api from '../api'


import Immutable from 'immutable'

const LOAD_GIS_DATA = 'LOAD_GIS_DATA'
const LOAD_GIS_DATA_DONE = 'LOAD_GIS_DATA_DONE'
const LOAD_GIS_DATA_ERROR = 'LOAD_GIS_DATA_ERROR'

const initialState = Immutable.Map()


export const loadGISData = (lang) => (dispatch, getState) => {

  dispatch({ type: LOAD_GIS_DATA })
  api.getGISDepartmentData({lang}).then(department => {

      api.getGISRegionData({lang}).then(region=>{
        dispatch({type: LOAD_GIS_DATA_DONE, region,department})
      }).catch(error => {
        dispatch({type: LOAD_GIS_DATA_ERROR,error})
      })

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
      const {department, region} = action
      return state.setIn(['status', 'loading'], false)
              .setIn(["region"], Immutable.fromJS(region))
              .setIn(["department"], Immutable.fromJS(department))
    }
    case LOAD_GIS_DATA_ERROR: {
      return state.setIn(['status', 'loading'], false).setIn(['status', 'error'], action.error)
    }

    default:
      return state
  }
}
