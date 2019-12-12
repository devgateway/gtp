import * as api from '../api'

import Immutable from 'immutable'

const CHANGE_TABLE_FILTER = 'CHANGE_TABLE_FILTER'
const LOAD_DATASET = 'LOAD_DATASET'
const LOAD_DATASET_DONE = 'LOAD_DATASET_DONE'
const LOAD_DATASET_ERROR = 'LOAD_DATASET_ERROR'

const initialState = Immutable.Map()

export const loadDatasets = () => (dispatch, getState) => {

  const filters= getState().getIn(['microdata','filters','datasets'])

  dispatch({type: LOAD_DATASET})

  api.getDatasets(filters?filters.toJS():{}).then((data) => {
    dispatch({type: LOAD_DATASET_DONE, data})
  }).catch(error => {
    dispatch({type: LOAD_DATASET_ERROR})
  })
}

export const changeFilter = (path, value) => (dispatch, getState) => {
  dispatch({type: CHANGE_TABLE_FILTER, path, value})
  dispatch(loadDatasets());
}

export default(state = initialState, action) => {
  switch (action.type) {
    case CHANGE_TABLE_FILTER:{
        const {path, value} = action
        return state.setIn(path, value)
      }

    case LOAD_DATASET:{
        return state.setIn(['status', 'datasets', 'loading'], true)
      }

    case LOAD_DATASET_DONE:{
        const {data} = action
        return state.setIn(['data', 'datasets'], Immutable.fromJS(data))
              .setIn(['status', 'datasets', 'loading'], false)
      }

    case LOAD_DATASET_ERROR:{
        const {error} = action
        return state.setIn(['status', 'datasets','error'], error)
              .setIn(['status', 'datasets', 'loading'], false)
      }

    default:
      return state
  }
}
