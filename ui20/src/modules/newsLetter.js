import * as api from '../api'


import Immutable from 'immutable'

const NEWS_LETTER_SUBSCRIBE = 'NEWS_LETTER_SUBSCRIBE'
const NEWS_LETTER_SUBSCRIBE_DONE = 'NEWS_LETTER_SUBSCRIBE_DONE'
const NEWS_LETTER_SUBSCRIBE_ERROR = 'NEWS_LETTER_SUBSCRIBE_ERROR'

const initialState = Immutable.Map()


export const doSubscribe = (email) => (dispatch, getState) => {

  dispatch({ type: NEWS_LETTER_SUBSCRIBE })

    api.subscribeToNewsLetter(email).then(data => {

        dispatch({type: NEWS_LETTER_SUBSCRIBE_DONE, data})

    }).catch(error => {
      dispatch({type: NEWS_LETTER_SUBSCRIBE_ERROR,error})
    })
}


export default (state = initialState, action) => {
  switch (action.type) {
    case NEWS_LETTER_SUBSCRIBE: {
      return state.setIn(['status', 'loading'], true)
    }
    case NEWS_LETTER_SUBSCRIBE_DONE: {
      const {data} = action
      return state.setIn(['status', 'loading'], false).setIn(["response"], "OK")
    }
    case NEWS_LETTER_SUBSCRIBE_ERROR: {
      return state.setIn(['status', 'loading'], false).setIn(['status', 'error'], action.error)
    }

    default:
      return state
  }
}
