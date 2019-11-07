import * as api from '../api'
import Immutable from 'immutable'
import {
  loadDataItems
} from './Data'

const initialState = Immutable.fromJS({
  filters: {}
})





export const loadDefaultPovertyFilters = () => (dispatch, getState) => {
  console.log("loadDefaultPovertyFilters")
  const filters = getState().getIn('filters')
  const gender = getState().getIn(['data', 'items', 'gender']).map(a => a.id);
  const professionalActivity = getState().getIn(['data', 'items', 'professionalActivity']).map(a => a.id);
  const ageGroup = getState().getIn(['data', 'items', 'ageGroup']).map(a => a.id);

  //default values
  const ranges=getState().getIn(['data', 'items', 'range']);


  const minAge = ranges.age?ranges.age.min:0
  const maxAge = ranges.age?ranges.age.max:0


  const minScore = ranges.score?ranges.score.min:0
  const maxScore = ranges.score?ranges.score.max:0

  let povertyFilters = Immutable.Map()
  povertyFilters = povertyFilters
    .setIn(['gender'], Immutable.List())
    .setIn(['activity'], Immutable.List())
    .setIn(['ageGroup'], Immutable.List())
    .setIn(['minAge'], minAge)
    .setIn(['maxAge'], maxAge)
    .setIn(['minScore'], minScore)
    .setIn(['maxScore'], maxScore)

  dispatch({
    type: 'LOAD_DEFAULT_POVERTY_FILTERS_DONE',
    povertyFilters
  })
}

export const apply = () => (dispatch, getState) => {
  dispatch({
    type: 'APPLY_FILTER_FLAG_ON'
  })
}



export const refresh = () => (dispatch, getState) => {
  dispatch(loadGlobalIndicators())
  dispatch(loadPovertyChartData())
}


export const reset = () => (dispatch, getState) => {

  dispatch({
    type: 'RESET_FILTER_FLAG_ON'
  })
}




export const loadPovertyChartData = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.loadPovertyChartData(filters).then(data => {
    dispatch({
      type: 'LOAD_POVERTY_CHART_DATA_DONE',
      data
    })
  }).catch(error => dispatch({
    type: 'POVERTY_CHART_DATA_ERROR',
    error
  }))
}


/*
Get default selected filters
*/

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

/*
Update values at global filter level
*/
export const updateGlobalFilter = (name, selection) => dispatch => {

  dispatch({
    type: 'CHANGE_GLOBAL_FILTER',
    name,
    selection
  })
}


/*
Update values at chart level indicated by path
*/
export const updateFilter = (path, selection, updates) => dispatch => {
  console.log(selection)
  dispatch({
    type: 'CHANGE_CHART_FILTER',
    path,
    selection
  })

  if(updates&&updates.indexOf('POVERTY')>-1){
    dispatch(loadPovertyChartData())
  }

}

/*
Get global indicators values (responsive to filters)
*/
export const loadGlobalIndicators = () => (dispatch, getState) => {

  const filters = getState().getIn(['indicator', 'filters']).toJS()

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



export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_DEFAULT_FILTERS_DONE': {
      const {
        category,
        data
      } = action
      return state.setIn(['filters', 'global'], Immutable.fromJS(data))
    }
    case 'CHANGE_GLOBAL_FILTER': {
      const {
        name,
        selection
      } = action
      return state.setIn(['filters', 'global', name], Immutable.fromJS(selection))
    }

    case 'LOAD_GLOBAL_INDICATORS_DONE': {
      const {
        data
      } = action

      return state.setIn(['globalNumbers', 'data'], Immutable.fromJS(data))
        .deleteIn(['globalNumbers', 'error'])
    }

    case 'LOAD_GLOBAL_INDICATORS_ERROR': {
      const {
        error
      } = action

      return state.setIn(['globalNumbers', 'data'], Immutable.fromJS([])).setIn(['globalNumbers', 'error'], error)
    }

    case 'CHANGE_CHART_FILTER': {
      const {
        path,
        selection
      } = action
      return state.setIn(path, Immutable.fromJS(selection))
    }

    case 'LOAD_DEFAULT_POVERTY_FILTERS_DONE': {
      const {
        povertyFilters
      } = action
      return state.setIn(['filters', 'poverty'], povertyFilters)
    }
    case 'LOAD_POVERTY_CHART_DATA_DONE': {
      const {
        data
      } = action

      return state.setIn(['poverty', 'data'], data)
    }

    case 'APPLY_FILTER_FLAG_ON': {
      const {
        data
      } = action

      return state.setIn(['applyFlag'], true)
    }
    case 'APPLY_FILTER_FLAG_OFF': {
      const {
        data
      } = action
      return state.deleteIn(['applyFlag'])
    }


    case 'RESET_FILTER_FLAG_ON': {
      const {
        data
      } = action

      return state.setIn(['resetFlag'], true)
    }
    case 'RESET_FILTER_FLAG_OFF': {
      const {
        data
      } = action
      return state.deleteIn(['resetFlag'])
    }



    default: {
      return state
    }
  }
}
