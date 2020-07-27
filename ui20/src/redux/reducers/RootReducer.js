import {
  combineReducers
} from 'redux-immutable'

import {
  connectRouter
} from 'connected-react-router/immutable'

import app from './AppReducer'
import water from './Water'
import agriculture from './Agriculture'
import bulletin from './GTPBulletin'
import member from './GTPMember'

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  app,
  water,
  agriculture,
  bulletin,
  member,
})

export default createRootReducer
