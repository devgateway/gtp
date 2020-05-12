import {
  applyMiddleware,
  compose,
  createStore
} from 'redux'
import promise from 'redux-promise-middleware'
import thunk from 'redux-thunk'
import {
  createHashHistory
} from 'history'
import createRootReducer from './reducers/RootReducer'
import {
  routerMiddleware
} from 'connected-react-router/immutable'
import Immutable from 'immutable'
// import indicatorListener from './ipar/listener/IndicatorListener'
export const history = createHashHistory()

const initialState = Immutable.Map()

const rootReducer = createRootReducer(history)
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose

export default function configureStore () {
  const store = createStore(
    rootReducer, // root reducer with router state
    initialState,
    composeEnhancers(
      applyMiddleware(
        routerMiddleware(history),
        // for dispatching history actions
        thunk,
        promise
        // ... other middlewares ...
      )
    )
  )

  store.subscribe(() => {
    // indicatorListener(store)
  })

  return store
}
