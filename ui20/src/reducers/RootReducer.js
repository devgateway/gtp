import {
  combineReducers
} from 'redux-immutable';

import {
  connectRouter
} from 'connected-react-router/immutable'

import data from '../modules/Data';
import analytic from '../modules/Analytic';
import indicator from '../modules/Indicator'

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  data,
  analytic,
  indicator
})




export default createRootReducer
