import {
  applyMiddleware,
  compose,
  createStore
} from 'redux'
import thunk from 'redux-thunk'
import {
  createHashHistory
} from 'history'
import createRootReducer from './reducers/RootReducer'
import {
  routerMiddleware
} from 'connected-react-router/immutable'
import Immutable from 'immutable'
import indicatorListener from './IndicatorListener'
import analyticListener from './AnalyticListener'
export const history = createHashHistory()

const initialState = Immutable.Map()

const rootReducer = createRootReducer(history)

export default function configureStore () {
  const store = createStore(
    rootReducer, // root reducer with router state
    initialState,
    compose(
      applyMiddleware(
        routerMiddleware(history), thunk // for dispatching history actions
        // ... other middlewares ...
      )
    )
  )

  store.subscribe(() => {
    indicatorListener(store)
  })

  return store
}
