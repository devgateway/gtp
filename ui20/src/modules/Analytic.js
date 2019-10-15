import {
  getDataSet
} from '../api'

import Immutable from 'immutable'

export const loadDataSet= (name) => dispatch => {
  getDataSet(name).then(data => {
    dispatch({
      type: 'LOAD_DATASET_DATA_OK',
      name,
      data
    })
  }).catch(error => {
    dispatch({
      type: 'LOAD_DATASET_DATA_ERROR',
      error
    })
  })
}






const initialState = Immutable.Map()

export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_DATASET_DATA_OK':
      
      return state.setIn([action.name], Immutable.List(action.data))
    case 'LOAD_DATASET_DATA_ERROR':
      return state
    default:
      return state
  }
}
