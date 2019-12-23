import * as api from '../api'

import Immutable from 'immutable'

const CHANGE_TABLE_FILTER = 'CHANGE_TABLE_FILTER'
const CHANGE_PAGE = 'CHANGE_PAGE'
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



export const changePage = (path, value,locale, updates=[]) => (dispatch, getState) => {

  dispatch({type: CHANGE_PAGE, path, value})

  if(updates.indexOf('DATASETS') > -1){
    dispatch(loadDatasets(locale));
  }
  if(updates.indexOf('SOURCES') > -1){
    dispatch(loadSources(locale));
  }

}




export const changeFilter = (path, value,locale, updates=[]) => (dispatch, getState) => {

  dispatch({type: CHANGE_TABLE_FILTER, path, value})

  if(updates.indexOf('DATASETS') > -1){
    dispatch(loadDatasets(locale));
  }
  if(updates.indexOf('SOURCES') > -1){
    dispatch(loadSources(locale));
  }

}

export default(state = initialState, action) => {
  switch (action.type) {
    case CHANGE_TABLE_FILTER:{
        const {path, value} = action
        const pagePath=path.slice(0,2)
              pagePath.push('pageNumber')
        return state.setIn(path, value).setIn(pagePath,0);
      }
      case CHANGE_PAGE:{
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
