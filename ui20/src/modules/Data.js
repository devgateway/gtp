import * as api from '../api'


import Immutable from 'immutable'


const initialState = Immutable.Map()



export const loadDataItems = category => dispatch => {

  api.getItems(category)
  .then(data => {
    dispatch({
      type: 'LOAD_DATA_ITEM_DONE',
      category,
      data
    })
  }).catch(error => {
    dispatch({
      type: 'LOAD_DATA_ITEM_ERROR',
      error
    })
  })
}


export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_DATA_ITEM_DONE':
      const {
        category, data
      } = action
      return state.setIn(["items", category], data)
    default:
      return state
  }
}
