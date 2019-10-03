import {combineReducers} from 'redux-immutable';

import { connectRouter } from 'connected-react-router/immutable'

import home from './HomeReducer';

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  home
})




export default createRootReducer
