import {
  combineReducers
} from 'redux-immutable'

import {
  connectRouter
} from 'connected-react-router/immutable'

import data from '../ipar/Data'
import analytic from '../ipar/Analytic'
import indicator from '../ipar/Indicator'
import microdata from '../ipar/Microdata'
import gis from '../ipar/Gis'
import national from '../ipar/National'
import newsLetter from '../ipar/newsLetter'

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  data,
  analytic,
  indicator,
  microdata,
  gis,
  national,
  newsLetter
})

export default createRootReducer
