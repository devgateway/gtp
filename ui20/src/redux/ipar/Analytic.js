import { getDataSet } from '../../api'
import Immutable from 'immutable'

const initialState = Immutable.fromJS({})

export const loadDataSet = (name) => (dispatch, getState) => {
  dispatch({
    type: 'LOAD_DATASET'

  })
  getDataSet(name).then(data => {
    dispatch({
      type: 'LOAD_DATASET_DATA_OK',
      name,
      data: data
    })
  }).catch(error => {
    dispatch({
      type: 'LOAD_DATASET_DATA_ERROR',
      error
    })
  })
}

export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_DATASET':
      return state.deleteIn([action.name, 'data'])

    case 'LOAD_DATASET_DATA_OK':
      return state.setIn([action.name, 'data'], Immutable.List(action.data))
    case 'LOAD_DATASET_DATA_ERROR':
      return state
    default:
      return state
  }
}
