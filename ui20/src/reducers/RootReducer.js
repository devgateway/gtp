import {combineReducers} from 'redux-immutable';

import { connectRouter } from 'connected-react-router/immutable'

import home from '../modules/Home';
import analytic from '../modules/Analytic';


const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  home,
  analytic
})




export default createRootReducer
