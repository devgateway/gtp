import {
  getDataSet
} from '../api'

import Immutable from 'immutable'
import production from './states/ProductionConf'
import consumption from './states/ConsumptionConf'
import marketPrice from './states/MarketPriceConf'

import {
  loadDataItems
} from '../modules/Data'

const initialState = Immutable.fromJS({
  production,
  consumption,
  marketPrice
})


const mapFields = (data, fields, extraFields, dataItems) => {

  return data.map(r => {
    let nr = {}
    Object.keys(r).forEach(k => {
      const name = fields[k];
      nr[name] = r[k]
    })

    Object.keys(extraFields).forEach(ex => {
      const extra = extraFields[ex];

      nr[ex] = extra.extractor(nr)(dataItems)
    })
    return nr
  });
}

export const loadDataSet = (name) => (dispatch, getState) => {
  getDataSet(name).then(data => {
    const dataItems = getState().getIn(['data', 'items']).toJS();
    const fields = getState().getIn(['analytic', name, 'config', 'fields']).toJS();
    const extraFields = getState().getIn(['analytic', name, 'config', 'extraFields']).toJS();
    const preparedData = mapFields(data, fields, extraFields, dataItems)

    dispatch({
      type: 'LOAD_DATASET_DATA_OK',
      name,
      data: preparedData
    })

  }).catch(error => {
    dispatch({
      type: 'LOAD_DATASET_DATA_ERROR',
      error
    })
  })
}






export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_DATASET_DATA_OK':
      debugger;

      return state.setIn([action.name, 'data'], Immutable.List(action.data))
    case 'LOAD_DATASET_DATA_ERROR':
      return state
    default:
      return state
  }
}
