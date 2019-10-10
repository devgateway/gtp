import Immutable from 'immutable'

const initialState = Immutable.Map()

export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_HOME_STATE':
      return state.setIn("test", "value")
    default:
      return state
  }
}
