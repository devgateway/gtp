import {
  applyMiddleware,
  compose,
  createStore
} from 'redux'
import thunk from 'redux-thunk';
import {
  createBrowserHistory
} from 'history'
import createRootReducer from './reducers/RootReducer';
import {
  routerMiddleware
} from 'connected-react-router/immutable'
import Immutable from 'immutable'


export const history = createBrowserHistory()

const initialState = Immutable.Map()

const rootReducer = createRootReducer(history)

export default function configureStore() {
  const store = createStore(
    rootReducer, // root reducer with router state
    initialState,
    compose(
      applyMiddleware(
        routerMiddleware(history), thunk // for dispatching history actions
        // ... other middlewares ...
      ),
    ),
  )

  return store
}