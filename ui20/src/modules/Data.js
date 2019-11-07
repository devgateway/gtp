import * as api from '../api'


import Immutable from 'immutable'


const initialState = Immutable.Map()

export const loadDataItems = (category, path, filtered) => (dispatch, getState) => {

  const filters = getState().getIn(['indicator', 'filters']).toJS()
  debugger;

  dispatch({type: 'LOAD_ITEM_DATA',category})

  api.getItems(category, path, filters)
    .then(data => {
      dispatch({type: 'LOAD_DATA_ITEM_DONE',category,data})
    }).catch(error => {
      dispatch({type: 'LOAD_DATA_ITEM_ERROR',category,error})
    })
}




export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_ITEM_DATA': {
      const {category} = action
      return state.setIn(['items','status',category,'loading'],true)
    }
    case 'LOAD_DATA_ITEM_DONE': {
      const {category,data} = action
      console.log(category +' set to state' )
      return state.setIn(["items", category], data)
      .setIn(['items','status',category,'loading'],false)
    }
    case 'LOAD_DATA_ITEM_ERROR': {
      const {category} = action
      return state.setIn(['items','status',category,'loading'],false)
      .setIn(['items','status',category,'error'],action.error)
    }

    default:
      return state
  }
}
