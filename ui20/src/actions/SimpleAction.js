export const simpleAction = () => dispatch => {
  debugger;
  dispatch({
    type: 'LOAD_HOME_STATE',
    payload: 'result_of_simple_action'
  })
}
