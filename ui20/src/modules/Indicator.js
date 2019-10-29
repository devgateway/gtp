import * as api from '../api'
import Immutable from 'immutable'


const initialState = Immutable.fromJS({
  filters: {}
})

export const loadDefaultFilters = () => dispatch => {
  api.getDefaultIndicatorFilters().then(data => {
    dispatch({
      type: 'LOAD_DEFAULT_FILTERS_DONE',
      data
    })
  }).catch(error => dispatch({
    type: 'LOAD_DEFAULT_FILTERS_ERROR',
    error
  }))
}
export const updateGlobalFilter = (name, selection) => dispatch => {
  dispatch({
    type: 'CHANGE_GLOBAL_FILTER',
    name,
    selection
  })
}

export const getGlobalIndicators = () => (dispatch, getState) => {

  const filters=getState().getIn(['indicator','filters','global']).toJS()

  api.getGlobalIndicators(filters).then(data => {
    dispatch({
      type: 'LOAD_GLOBAL_INDICATORS_DONE',
      data
    })
  }).catch(error => dispatch({
    type: 'LOAD_GLOBAL_INDICATORS_ERROR',
    error
  }))

}

export const loadDataSet = name => dispatch => {
  api.getItems(name)
    .then(data => {
      dispatch({
        type: 'LOAD_DATA_ITEM_DONE',
        name,
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
    case 'LOAD_DEFAULT_FILTERS_DONE': {
      const {category,data} = action
      return state.setIn(['filters', 'global'], Immutable.fromJS(data))
    }
    case 'CHANGE_GLOBAL_FILTER': {
      const {name,selection} = action
      return state.setIn(['filters', 'global', name], Immutable.fromJS(selection))
    }

    case 'LOAD_GLOBAL_INDICATORS_DONE': {
      const {data} = action
      console.log( Immutable.fromJS(data))
      return state.set('globalNumbers', Immutable.fromJS(data))
    }

    default:{
      return state
      }
  }
}
