import {
  combineReducers
} from 'redux-immutable';

import {
  connectRouter
} from 'connected-react-router/immutable'

import data from '../modules/Data';
import analytic from '../modules/Analytic';
import indicator from '../modules/Indicator'
import microdata from '../modules/Microdata'
import gis from '../modules/Gis'
import national from '../modules/National'

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  data,
  analytic,
  indicator,
  microdata,
  gis,
  national
})




export default createRootReducer
