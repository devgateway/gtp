import {
  combineReducers
} from 'redux-immutable'

import {
  connectRouter
} from 'connected-react-router/immutable'

import app from './AppReducer'
import water from './Water'
import agriculture from './Agriculture'

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  app,
  water,
  agriculture,
})

export default createRootReducer
