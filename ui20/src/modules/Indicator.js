import * as api from '../api'
import Immutable from 'immutable'

const LOAD_DEFAULT_FILTERS_DONE = 'LOAD_DEFAULT_FILTERS_DONE'
const LOAD_DEFAULT_FILTERS_ERROR = 'LOAD_DEFAULT_FILTERS_ERROR'
const LOAD_DEFAULT_POVERTY_FILTERS_DONE = 'LOAD_DEFAULT_POVERTY_FILTERS_DONE'
const LOAD_DEFAULT_WOMEN_FILTERS_DONE = 'LOAD_DEFAULT_WOMEN_FILTERS_DONE'
const LOAD_DEFAULT_FOOD_FILTERS_DONE = 'LOAD_DEFAULT_FOOD_FILTERS_DONE'
const LOAD_DEFAULT_AOI_FILTERS_DONE = 'LOAD_DEFAULT_AOI_FILTERS_DONE'
const APPLY_FILTER_FLAG_ON = 'APPLY_FILTER_FLAG_ON'
const RESET_FILTER_FLAG_ON = 'RESET_FILTER_FLAG_ON'
const LOAD_AGRICUTURAL_POPULATION_DATA_DONE = 'LOAD_AGRICUTURAL_POPULATION_DATA_DONE'
const LOAD_AGRICUTURAL_POPULATION_DATA_ERROR = 'LOAD_AGRICUTURAL_POPULATION_DATA_ERROR'

const LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE = 'LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE'
const LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR = 'LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR'

const LOAD_POVERTY_CHART_DATA_DONE = 'LOAD_POVERTY_CHART_DATA_DONE'
const POVERTY_CHART_DATA_ERROR = 'POVERTY_CHART_DATA_ERROR'

const LOAD_FOOD_LOSS_DATA_DONE = 'LOAD_FOOD_LOSS_DATA_DONE'
const LOAD_FOOD_LOSS_ERROR = 'LOAD_FOOD_LOSS_ERROR'

const CHANGE_GLOBAL_FILTER = 'CHANGE_GLOBAL_FILTER'
const CHANGE_CHART_FILTER = 'CHANGE_CHART_FILTER'

const LOAD_GLOBAL_INDICATORS_DONE = 'LOAD_GLOBAL_INDICATORS_DONE'
const LOAD_GLOBAL_INDICATORS_ERROR = 'LOAD_GLOBAL_INDICATORS_ERROR'


const LOAD_AOI_SUBSIDIES_DONE = 'LOAD_AOI_SUBSIDIES_DONE'
const LOAD_AOI_SUBSIDIES_ERROR = 'LOAD_AOI_SUBSIDIES_ERROR'

const LOAD_AOI_TOTAL_BUDGET_DONE = 'LOAD_AOI_TOTAL_BUDGET_DONE'
const LOAD_AOI_TOTAL_BUDGET_ERROR = 'LOAD_AOI_TOTAL_BUDGET_ERROR'


const LOAD_METADATA_DONE = 'LOAD_METADATA_DONE'
const LOAD_METADATA_ERROR = 'LOAD_METADATA_ERROR'

const initialState = Immutable.fromJS({
  filters: {}
})

/*
Get default selected filters
*/

export const exportData = (what, format,language, options) => (dispatch, getState)  => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()


  api.exportIndicators(what, format, language, filters,options)
}

export const loadDefaultFilters = () => dispatch => {
  api.getDefaultIndicatorFilters().then(data => {
    dispatch({
      type: LOAD_DEFAULT_FILTERS_DONE,
      data
    })
  }).catch(error => dispatch({
    type: LOAD_DEFAULT_FILTERS_ERROR,
    error
  }))
}


export const loadMetadata= (lang)=> (dispatch, getState) => {

    api.getIndicatorsMetadata(lang).then(data=>{
        dispatch({type: LOAD_METADATA_DONE,data})
    }).catch(error=>dispatch({type:LOAD_METADATA_ERROR}))
}

//Set here initial selected poverty filters if needed
export const loadDefaultPovertyFilters = () => (dispatch, getState) => {

  console.log("loadDefaultPovertyFilters")
  //const filters = getState().getIn(['filters'])
  //const gender = getState().getIn(['data', 'items', 'gender']).map(a => a.id);
  //const professionalActivity = getState().getIn(['data', 'items', 'professionalActivity']).map(a => a.id);
  //const ageGroup = getState().getIn(['data', 'items', 'ageGroup']).map(a => a.id);
  //default values
  const ranges = getState().getIn(['data', 'items', 'range']);

  const minAge = ranges.age ? ranges.age.min : 0
  const maxAge = ranges.age ? ranges.age.max : 0

  const minScore = ranges.score ? ranges.score.min : 0
  const maxScore = ranges.score ? ranges.score.max : 0

  let povertyFilters = Immutable.Map()
  povertyFilters = povertyFilters
    .setIn(['gender'], Immutable.List())
    .setIn(['activity'], Immutable.List())
    .setIn(['ageGroup'], Immutable.List())
    .setIn(['minAge'], minAge)
    .setIn(['maxAge'], maxAge)
    .setIn(['minScore'], minScore)
    .setIn(['maxScore'], maxScore)

  dispatch({
    type: LOAD_DEFAULT_POVERTY_FILTERS_DONE,
    povertyFilters
  })
}

//Set here initial selected women filters if needed
export const loadDefaultWomenFilters = () => (dispatch, getState) => {
  console.log("loadDefaultWomenFilters")
  const ageGroups = getState().getIn(['data', 'items', 'ageGroup']).map(a => a.id);
  const methodOfEnforcements = getState().getIn(['data', 'items', 'methodOfEnforcement']).map(a => a.id);
  const genders = getState().getIn(['data', 'items', 'gender']).map(a => a.id);
  const womenFilters = Immutable.Map()
    .setIn(['gender'], Immutable.List())
    .setIn(['ageGroup'], Immutable.List())
    .setIn(['methodOfEnforcement'], Immutable.List())
  dispatch({
    type: LOAD_DEFAULT_WOMEN_FILTERS_DONE,
    womenFilters
  })
}

//Set here initial selected food loss filters if needed
export const loadDefaultFoodFilters = () => (dispatch, getState) => {
  console.log("loadDefaultFoodFilters")
  const lossType = getState().getIn(['data', 'items', 'lossType']).map(a => a.id);
  const foodFilters = Immutable.Map().setIn(['lossType'], Immutable.List())
  dispatch({
    type: LOAD_DEFAULT_FOOD_FILTERS_DONE,
    foodFilters
  })
}

//Set here initial selected AOI  filters if needed
export const loadDefaultAOIFilters = () => (dispatch, getState) => {
  console.log("loadDefaultAOIFilters")
  const indexType1 = getState().getIn(['data', 'items', 'indexType/1']).map(a => a.id);
  const indexType2 = getState().getIn(['data', 'items', 'indexType/2']).map(a => a.id);
  const aoiFilters = Immutable.Map().setIn(['budget', 'indexType'], Immutable.List()).setIn(['subsidies', 'indexType'], Immutable.List())

  dispatch({
    type: LOAD_DEFAULT_AOI_FILTERS_DONE,
    aoiFilters
  })
}

//Set apply flag on (see IndicatorListener)
export const apply = () => (dispatch, getState) => {
  dispatch({
    type: APPLY_FILTER_FLAG_ON
  })
}

//Reload all datasets (called from IndicatorListener whenever it i needed)
export const refresh = () => (dispatch, getState) => {
  dispatch(loadGlobalIndicators())
  dispatch(loadPovertyChartData())
  dispatch(loadAgricuturalPopulationData())
  dispatch(loadAgricuturalDistribution())
  dispatch(loadFoodLossData())
  dispatch(loadAOIsubsidies())
  dispatch(loadAOItotalbudget())
}

export const reset = () => (dispatch, getState) => {
  dispatch({
    type: RESET_FILTER_FLAG_ON
  })
}

//Agricutural Population Distribution by gender and age
export const loadAgricuturalPopulationData = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getAgricuturalPopulation(filters).then(data => {

    dispatch({
      type: LOAD_AGRICUTURAL_POPULATION_DATA_DONE,
      data
    })

  }).catch(error => dispatch({
    type: LOAD_AGRICUTURAL_POPULATION_DATA_ERROR,
    error
  }))
}

//Agricutural Population Distribution by method and age
export const loadAgricuturalDistribution = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getAgricuturalDistribution(filters).then(data => {
    dispatch({
      type: LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE,
      data
    })
  }).catch(error => dispatch({
    type: LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR,
    error
  }))
}

//Proportion of population under poverty line
export const loadPovertyChartData = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.loadPovertyChartData(filters).then(data => {
    dispatch({
      type: LOAD_POVERTY_CHART_DATA_DONE,
      data
    })
  }).catch(error => dispatch({
    type: POVERTY_CHART_DATA_ERROR,
    error
  }))
}

//Food Loss Index Kg and %
export const loadFoodLossData = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getFoodLoss(filters).then(data => {
    dispatch({
      type: LOAD_FOOD_LOSS_DATA_DONE,
      data
    })

  }).catch(error => dispatch({
    type: LOAD_FOOD_LOSS_ERROR,
    error
  }))
}


export const loadAOIsubsidies = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getAOIsubsidies(filters).then(data => {

    dispatch({
      type: LOAD_AOI_SUBSIDIES_DONE,
      data
    })

  }).catch(error => dispatch({
    type: LOAD_AOI_SUBSIDIES_ERROR,
    error
  }))
}


export const loadAOItotalbudget = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getAOItotalBudget(filters).then(data => {
    dispatch({
      type: LOAD_AOI_TOTAL_BUDGET_DONE,
      data
    })

  }).catch(error => dispatch({
    type: LOAD_AOI_TOTAL_BUDGET_ERROR,
    error
  }))
}
/*
Update values at global filter level
*/
export const updateGlobalFilter = (name, selection) => dispatch => {

  dispatch({
    type: CHANGE_GLOBAL_FILTER,
    name,
    selection
  })
}
/*
Update values at chart level indicated by path
*/
export const updateFilter = (path, selection, updates) => dispatch => {
  console.log(selection)
  dispatch({
    type: CHANGE_CHART_FILTER,
    path,
    selection
  })

  if (updates && updates.indexOf('POVERTY') > -1) {
    dispatch(loadPovertyChartData())
  }

  if (updates && updates.indexOf('WOMEN') > -1) {
    dispatch(loadAgricuturalPopulationData())
    dispatch(loadAgricuturalDistribution())
  }

  if (updates && updates.indexOf('FOOD') > -1) {
    dispatch(loadFoodLossData())
  }

  if (updates && updates.indexOf('BUDGET') > -1) {
    dispatch(loadAOItotalbudget());

  }
  if (updates && updates.indexOf('SUBSIDIES') > -1) {
    dispatch(loadAOIsubsidies());
  }

}
/*
Get global indicators values (responsive to filters)
*/
export const loadGlobalIndicators = () => (dispatch, getState) => {

  const filters = getState().getIn(['indicator', 'filters']).toJS()

  api.getGlobalIndicators(filters).then(data => {
    dispatch({
      type: LOAD_GLOBAL_INDICATORS_DONE,
      data
    })
  }).catch(error => dispatch({
    type: LOAD_GLOBAL_INDICATORS_ERROR,
    error
  }))

}

export default (state = initialState, action) => {
  switch (action.type) {

    case LOAD_METADATA_DONE: {
      const {data} = action
      return state.setIn(['metadata'], Immutable.fromJS(data.content))
    }


    case LOAD_DEFAULT_FILTERS_DONE: {
      const {
        category,
        data
      } = action
      return state.setIn(['filters', 'global'], Immutable.fromJS(data))
    }

    case CHANGE_GLOBAL_FILTER: {
      const {
        name,
        selection
      } = action
      return state.setIn(['filters', 'global', name], Immutable.fromJS(selection))
    }

    case LOAD_GLOBAL_INDICATORS_DONE: {
      const {
        data
      } = action
      return state.setIn(['globalNumbers', 'data'], Immutable.fromJS(data))
        .deleteIn(['globalNumbers', 'error'])
    }

    case LOAD_GLOBAL_INDICATORS_ERROR: {
      const {
        error
      } = action
      return state.setIn(['globalNumbers', 'data'], Immutable.fromJS([])).setIn(['globalNumbers', 'error'], error)
    }

    case CHANGE_CHART_FILTER: {
      const {
        path,
        selection
      } = action
      return state.setIn(path, Immutable.fromJS(selection))
    }

    case LOAD_DEFAULT_POVERTY_FILTERS_DONE: {
      const {
        povertyFilters
      } = action
      return state.setIn(['filters', 'poverty'], povertyFilters)
    }

    case LOAD_DEFAULT_WOMEN_FILTERS_DONE: {
      const {
        womenFilters
      } = action
      return state.setIn(['filters', 'women'], womenFilters)
    }

    case LOAD_DEFAULT_FOOD_FILTERS_DONE: {
      const {
        foodFilters
      } = action
      return state.setIn(['filters', 'food'], foodFilters)
    }
    case LOAD_DEFAULT_AOI_FILTERS_DONE: {
      const {
        aoiFilters
      } = action
      return state.setIn(['filters', 'aoi'], aoiFilters)
    }

    case LOAD_POVERTY_CHART_DATA_DONE: {
      const {
        data
      } = action
      return state.setIn(['poverty', 'data'], Immutable.fromJS(data))
    }

    case LOAD_AGRICUTURAL_POPULATION_DATA_DONE: {
      const {
        data
      } = action
      return state.setIn(['women', 'population', 'data'], data)
    }
    case LOAD_AGRICUTURAL_POPULATION_DATA_ERROR: {
      const {
        error
      } = action
      return state.setIn(['women', 'population', 'error'], error).setIn(['women', 'population', 'data'], null)
    }

    case LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE: {
      const {
        data
      } = action
      return state.setIn(['women', 'distribution', 'data'], data)
    }
    case LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR: {
      const {
        error
      } = action
      return state.setIn(['women', 'distribution', 'error'], error).setIn(['women', 'distribution', 'data'], null)
    }

    case LOAD_FOOD_LOSS_DATA_DONE: {
      const {
        data
      } = action
      return state.setIn(['food', 'data'], data)
    }

    case LOAD_FOOD_LOSS_ERROR: {
      const {
        error
      } = action
      return state.setIn(['food', 'data', 'error'], error)
        .setIn(['food', 'data'], null)
    }
    case LOAD_AOI_SUBSIDIES_DONE: {
      const {
        data
      } = action

      return state.setIn(['aoi', 'data', 'subsidies'], data)
    }

    case LOAD_AOI_SUBSIDIES_ERROR: {
      const {
        error
      } = action
      return state.setIn(['aoi', 'data', 'subsidies', 'error'], error)
        .setIn(['food', 'data'], null)
    }

    case LOAD_AOI_TOTAL_BUDGET_DONE: {
      const {
        data
      } = action
      return state.setIn(['aoi', 'data', 'budget'], data)
    }

    case LOAD_AOI_TOTAL_BUDGET_ERROR: {
      const {
        error
      } = action
      return state.setIn(['aoi', 'data', 'budget', 'error'], error)
        .setIn(['food', 'data'], null)
    }

    case APPLY_FILTER_FLAG_ON: {
      const {
        data
      } = action
      return state.setIn(['applyFlag'], true)
    }
    case 'APPLY_FILTER_FLAG_OFF': {
      const {
        data
      } = action
      return state.deleteIn(['applyFlag'])
    }

    case RESET_FILTER_FLAG_ON: {
      const {
        data
      } = action
      return state.setIn(['resetFlag'], true)
    }
    case 'RESET_FILTER_FLAG_OFF': {
      const {
        data
      } = action
      return state.deleteIn(['resetFlag'])
    }

    default: {
      return state
    }
  }
}
