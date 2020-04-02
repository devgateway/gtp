import * as api from '../api'

import Immutable from 'immutable'

const LOAD_ITEM_DATA = 'LOAD_ITEM_DATA'
const LOAD_DATA_ITEM_DONE = 'LOAD_DATA_ITEM_DONE'
const LOAD_DATA_ITEM_ERROR = 'LOAD_DATA_ITEM_ERROR'

const LOAD_RAPID_LINKS = 'LOAD_RAPID_LINKS'
const LOAD_RAPID_LINKS_DONE = 'LOAD_RAPID_LINKS_DONE'
const LOAD_RAPID_LINKS_ERROR = 'LOAD_RAPID_LINKS_ERROR'

const LOAD_PARTNERS_DATA = 'LOAD_PARTNERS_DATA'
const LOAD_PARTNERS_DATA_DONE = 'LOAD_PARTNERS_DATA_DONE'
const LOAD_PARTNERS_DATA_ERROR = 'LOAD_PARTNERS_DATA_ERROR'

const LOAD_INITIATIVE_TYPES = 'LOAD_INITIATIVE_TYPES'
const LOAD_INITIATIVE_TYPES_DONE = 'LOAD_INITIATIVE_TYPES_DONE'
const LOAD_INITIATIVE_TYPES_ERROR = 'LOAD_INITIATIVE_TYPES_ERROR'

const LOAD_INITIATIVE_ITEMS = 'LOAD_INITIATIVE_ITEMS'
const LOAD_INITIATIVE_ITEMS_DONE = 'LOAD_INITIATIVE_ITEMS_DONE'
const LOAD_INITIATIVE_ITEMS_ERROR = 'LOAD_INITIATIVE_ITEMS_ERROR'

const initialState = Immutable.Map()

export const loadInitiativesTypes = () => (dispatch, getState) => {
  dispatch({ type: LOAD_INITIATIVE_TYPES })
  api.getInitiativeTypes()
    .then(data => {
      dispatch({ type: LOAD_INITIATIVE_TYPES_DONE, data })
    }).catch(error => {
      dispatch({ type: LOAD_INITIATIVE_TYPES_ERROR, error })
    })
}

export const loadInitiativesItems = (id, locale, page) => (dispatch, getState) => {
  dispatch({ type: LOAD_INITIATIVE_ITEMS, id, locale })
  api.getInitiativeItems(id, locale, page)
    .then(data => {
      dispatch({ type: LOAD_INITIATIVE_ITEMS_DONE, id, data })
    }).catch(error => {
      dispatch({ type: LOAD_INITIATIVE_ITEMS_ERROR, error, id, locale })
    })
}

export const loadPartners = (locale) => (dispatch, getState) => {
  dispatch({ type: LOAD_PARTNERS_DATA })
  api.getPartners(locale)
    .then(data => {
      dispatch({ type: LOAD_PARTNERS_DATA_DONE, ...data })
    }).catch(error => {
      dispatch({ type: LOAD_PARTNERS_DATA_ERROR, error })
    })
}

export const loadRapidLInks = (category, path, filtered) => (dispatch, getState) => {
  dispatch({ type: LOAD_RAPID_LINKS })
  api.getRapidLinks()
    .then(data => {
      dispatch({ type: LOAD_RAPID_LINKS_DONE, data })
    }).catch(error => {
      dispatch({ type: LOAD_RAPID_LINKS_ERROR, category, error })
    })
}

export const loadDataItems = (category, path, filtered) => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  dispatch({ type: LOAD_ITEM_DATA, category })

  api.getItems(category, path, filters)
    .then(data => {
      dispatch({ type: LOAD_DATA_ITEM_DONE, category, data })
    }).catch(error => {
      dispatch({ type: LOAD_DATA_ITEM_ERROR, category, error })
    })
}

export default (state = initialState, action) => {
  switch (action.type) {
    case LOAD_ITEM_DATA: {
      const { category } = action
      return state.setIn(['items', 'status', category, 'loading'], true)
    }
    case LOAD_DATA_ITEM_DONE: {
      const { category, data } = action
      return state.setIn(['items', category], data)
        .setIn(['items', 'status', category, 'loading'], false)
    }
    case LOAD_DATA_ITEM_ERROR: {
      const { category } = action
      return state.setIn(['items', 'status', category, 'loading'], false)
        .setIn(['items', 'status', category, 'error'], action.error)
    }
    case LOAD_RAPID_LINKS: {
      return state.setIn(['links', 'loading'], true)
    }
    case LOAD_RAPID_LINKS_DONE: {
      const { data } = action

      return state.setIn(['links', 'data'], data).deleteIn(['links', 'error'])
    }
    case LOAD_RAPID_LINKS_ERROR: {
      const { error } = action
      return state.setIn(['links', 'error'], error)
    }

    case LOAD_PARTNERS_DATA: {
      const { data } = action
      return state.setIn(['partners', 'loading'], true)
    }

    case LOAD_PARTNERS_DATA_DONE: {
      const { partners, groups } = action

      return state
        .setIn(['partners', 'data'], partners)
        .setIn(['partners', 'groups'], groups)
        .deleteIn(['partners', 'error'])
    }

    case LOAD_PARTNERS_DATA_ERROR: {
      const { error } = action
      return state.setIn(['partners', 'error'], error)
    }

    case LOAD_INITIATIVE_TYPES: {
      const { data } = action
      return state.setIn(['initiatives', 'types', 'loading'], true)
    }

    case LOAD_INITIATIVE_TYPES_DONE: {
      const { data } = action

      return state.setIn(['initiatives', 'types', 'data'], data)
        .deleteIn(['initiatives', 'types', 'loading'])
        .deleteIn(['initiatives', 'types', 'error'])
    }

    case LOAD_INITIATIVE_TYPES_ERROR: {
      const { error } = action
      return state.setIn(['initiatives', 'types', 'error'], error)
    }

    case LOAD_INITIATIVE_ITEMS: {
      const { data, id } = action
      return state.setIn(['initiatives', 'items', 'loading', id], true)
    }

    case LOAD_INITIATIVE_ITEMS_DONE: {
      const { data, id } = action

      return state.setIn(['initiatives', 'items', 'data', id], data)
        .deleteIn(['initiatives', 'items', 'loading'])
        .deleteIn(['initiatives', 'items', 'error'])
    }

    case LOAD_INITIATIVE_ITEMS_ERROR: {
      const { error, id } = action
      return state.setIn(['initiatives', 'items', 'error', id], error)
    }

    default:
      return state
  }
}
