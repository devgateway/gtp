import * as api from '../api'

import Immutable from 'immutable'

const CHANGE_TABLE_FILTER = 'CHANGE_TABLE_FILTER'
const LOAD_DATASET = 'LOAD_DATASET'
const LOAD_DATASET_DONE = 'LOAD_DATASET_DONE'
const LOAD_DATASET_ERROR = 'LOAD_DATASET_ERROR'
const LOAD_SOURCES = 'LOAD_SOURCES'
const LOAD_SOURCES_DONE = 'LOAD_SOURCES_DONE'
const LOAD_SOURCES_ERROR = 'LOAD_SOURCES_ERROR'


const initialState = Immutable.Map()

export const loadDatasets = (locale) => (dispatch, getState) => {
  const filters= getState().getIn(['microdata','filters','datasets']) || new Immutable.Map()

  dispatch({type: LOAD_DATASET})

  api.getDatasets(filters.set('lang',locale)).then((data) => {
    dispatch({type: LOAD_DATASET_DONE, data})
  }).catch(error => {
    dispatch({type: LOAD_DATASET_ERROR})
  })
}


export const loadSources = (locale) => (dispatch, getState) => {
  const filters= getState().getIn(['microdata','filters','sources']) || new Immutable.Map()
  dispatch({type: LOAD_SOURCES})
  api.getSources(filters.set('lang',locale)).then((data) => {
    dispatch({type: LOAD_SOURCES_DONE, data})
  }).catch(error => {
    dispatch({type: LOAD_SOURCES_ERROR})
  })
}


export const changeFilter = (path, value,locale) => (dispatch, getState) => {
  dispatch({type: CHANGE_TABLE_FILTER, path, value})
  dispatch(loadDatasets(locale));
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


      case LOAD_SOURCES:{
          return state.setIn(['status', 'sources', 'loading'], true)
        }

      case LOAD_SOURCES_DONE:{
          const {data} = action
          return state.setIn(['data', 'sources'], Immutable.fromJS(data))
                .setIn(['status', 'sources', 'loading'], false)
        }

      case LOAD_SOURCES_ERROR:{
          const {error} = action
          return state.setIn(['status', 'sources','error'], error)
                .setIn(['status', 'sources', 'loading'], false)
        }


    default:
      return state
  }
}
