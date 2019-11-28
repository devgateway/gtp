import {} from './modules/Indicator'
import {
  loadDataItems
} from './modules/Data'



const flags = []

/*
 *
 *  Analytic loading sequence.
 *
 */

const listener = (store) => {

  const state = store.getState();
  const region = state.getIn(['data', 'items', 'region']);
  const cropType = state.getIn(['data', 'items', 'cropType']);
  const department = state.getIn(['data', 'items', 'market']);
  const market = state.getIn(['data', 'items', 'department']);
  const isDataReady = (region && cropType && department && market)

  if (isDataReady) {
    debugger;
  }
  //this.props.onLoadData(this.props.dataset)

}

export default listener
