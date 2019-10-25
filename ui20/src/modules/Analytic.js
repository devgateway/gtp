import {
  getDataSet
} from '../api'

import Immutable from 'immutable'
import productionConfigurator from './states/ProductionConf'
import consumptionConfigurator from './states/ConsumptionConf'
import marketPriceConfigurator from './states/MarketPriceConf'

import {
  loadDataItems
} from '../modules/Data'

const initialState = Immutable.fromJS({})


const mapFields = (data, fields, extraFields, dataItems) => {

  return data.map(r => {
    let nr = {}
    Object.keys(r).forEach(k => {
      const name = fields[k];
      nr[name] = r[k]
    })

    Object.keys(extraFields).forEach(ex => {
      const extra = extraFields[ex];

      nr[extra.label] = extra.extractor(nr)(dataItems)
    })

    return nr
  });
}

export const configure = (intl) => (dispatch, getState) => {
  const production = productionConfigurator(intl)
  const consumption = consumptionConfigurator(intl)
  const marketPrice = marketPriceConfigurator(intl)
  const language=intl.locale
  dispatch({
    type: 'LOAD_CONFIG_OK',
    production,
    consumption,
    marketPrice,
    language
  })
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
      return state.setIn([action.name, 'data'], Immutable.List(action.data))
    case 'LOAD_DATASET_DATA_ERROR':
      return state
    case 'LOAD_CONFIG_OK':

      return state.setIn(['production', 'config'], Immutable.fromJS(action.production))
        .setIn(['consumption', 'config'], Immutable.fromJS(action.consumption))
        .setIn(['marketPrice', 'config'], Immutable.fromJS(action.marketPrice))
        .setIn(['config','language'],action.language)


    default:
      return state
  }
}
