import * as api from '../api'
import Immutable from 'immutable'

const	LOAD_DEFAULT_FILTERS_DONE	=	'LOAD_DEFAULT_FILTERS_DONE'
const	LOAD_DEFAULT_FILTERS_ERROR	=	'LOAD_DEFAULT_FILTERS_ERROR'
const	LOAD_DEFAULT_POVERTY_FILTERS_DONE	=	'LOAD_DEFAULT_POVERTY_FILTERS_DONE'
const	LOAD_DEFAULT_WOMEN_FILTERS_DONE	=	'LOAD_DEFAULT_WOMEN_FILTERS_DONE'
const	LOAD_DEFAULT_FOOD_FILTERS_DONE	=	'LOAD_DEFAULT_FOOD_FILTERS_DONE'
const	LOAD_DEFAULT_AOI_FILTERS_DONE	=	'LOAD_DEFAULT_AOI_FILTERS_DONE'
const	APPLY_FILTER_FLAG_ON	=	'APPLY_FILTER_FLAG_ON'
const	RESET_FILTER_FLAG_ON	=	'RESET_FILTER_FLAG_ON'
const	LOAD_AGRICUTURAL_POPULATION_DATA_DONE	=	'LOAD_AGRICUTURAL_POPULATION_DATA_DONE'
const	LOAD_AGRICUTURAL_POPULATION_DATA_ERROR	=	'LOAD_AGRICUTURAL_POPULATION_DATA_ERROR'

const	LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE	=	'LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE'
const	LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR	=	'LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR'

const	LOAD_POVERTY_CHART_DATA_DONE	=	'LOAD_POVERTY_CHART_DATA_DONE'
const	POVERTY_CHART_DATA_ERROR	=	'POVERTY_CHART_DATA_ERROR'

const	LOAD_FOOD_LOSS_DATA_DONE	=	'LOAD_FOOD_LOSS_DATA_DONE'
const	LOAD_FOOD_LOSS_ERROR	=	'LOAD_FOOD_LOSS_ERROR'

const	CHANGE_GLOBAL_FILTER	=	'CHANGE_GLOBAL_FILTER'
const	CHANGE_CHART_FILTER	=	'CHANGE_CHART_FILTER'

const	LOAD_GLOBAL_INDICATORS_DONE	=	'LOAD_GLOBAL_INDICATORS_DONE'
const	LOAD_GLOBAL_INDICATORS_ERROR	=	'LOAD_GLOBAL_INDICATORS_ERROR'

const initialState = Immutable.fromJS({
  filters: {}
})

/*
Get default selected filters
*/

export const loadDefaultFilters = () => dispatch => {
  api.getDefaultIndicatorFilters().then(data => {
    dispatch({type: LOAD_DEFAULT_FILTERS_DONE,data})
  }).catch(error => dispatch({type: LOAD_DEFAULT_FILTERS_ERROR,error}))
}


export const loadDefaultPovertyFilters = () => (dispatch, getState) => {
  console.log("loadDefaultPovertyFilters")
  const filters = getState().getIn('filters')
  const gender = getState().getIn(['data', 'items', 'gender']).map(a => a.id);
  const professionalActivity = getState().getIn(['data', 'items', 'professionalActivity']).map(a => a.id);
  const ageGroup = getState().getIn(['data', 'items', 'ageGroup']).map(a => a.id);

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


//Default Filters


export const loadDefaultFoodFilters = () => (dispatch, getState) => {
  console.log("loadDefaultFoodFilters")
  const lossType = getState().getIn(['data', 'items', 'lossType']).map(a => a.id);
  const foodFilters = Immutable.Map().setIn(['lossType'], Immutable.List(lossType))
  dispatch({type: LOAD_DEFAULT_FOOD_FILTERS_DONE,foodFilters})
}

export const loadDefaultAOIFilters = () => (dispatch, getState) => {
  console.log("loadDefaultAOIFilters")

  const indexType = getState().getIn(['data', 'items', 'indexType']).map(a => a.id);
  const aoiFilters = Immutable.Map().setIn(['indexType'], Immutable.List(indexType))

  dispatch({
    type: LOAD_DEFAULT_AOI_FILTERS_DONE,
    aoiFilters
  })
}



export const apply = () => (dispatch, getState) => {
  dispatch({
    type: APPLY_FILTER_FLAG_ON
  })
}



export const refresh = () => (dispatch, getState) => {
  dispatch(loadGlobalIndicators())
  dispatch(loadPovertyChartData())
  dispatch(loadAgricuturalPopulationData())
  dispatch(loadAgricuturalDistribution())
}


export const reset = () => (dispatch, getState) => {
  dispatch({
    type: RESET_FILTER_FLAG_ON
  })
}


//Data loaders

export const loadAgricuturalPopulationData = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getAgricuturalPopulation(filters).then(data => {

    dispatch({type: LOAD_AGRICUTURAL_POPULATION_DATA_DONE,data})

  }).catch(error => dispatch({type: LOAD_AGRICUTURAL_POPULATION_DATA_ERROR,error}))
}



export const loadAgricuturalDistribution = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getAgricuturalDistribution(filters).then(data => {
    dispatch({
      type: LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE,
      data
    })
  }).catch(error => dispatch({type: LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR,error}))
}


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




export const loadFoodLossData = () => (dispatch, getState) => {
  const filters = getState().getIn(['indicator', 'filters']).toJS()
  api.getFoodLoss(filters).then(data => {
    dispatch({type: LOAD_FOOD_LOSS_DATA_DONE,data})

  }).catch(error => dispatch({type: LOAD_FOOD_LOSS_ERROR,error}))
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
    case LOAD_DEFAULT_FILTERS_DONE: {
      const {category,data} = action
      return state.setIn(['filters', 'global'], Immutable.fromJS(data))
    }

    case CHANGE_GLOBAL_FILTER: {
      const {name,selection} = action
      return state.setIn(['filters', 'global', name], Immutable.fromJS(selection))
    }

    case LOAD_GLOBAL_INDICATORS_DONE: {
      const {data} = action
      return state.setIn(['globalNumbers', 'data'], Immutable.fromJS(data))
        .deleteIn(['globalNumbers', 'error'])
    }

    case LOAD_GLOBAL_INDICATORS_ERROR: {
      const {error} = action
      return state.setIn(['globalNumbers', 'data'], Immutable.fromJS([])).setIn(['globalNumbers', 'error'], error)
    }

    case CHANGE_CHART_FILTER: {
      const {path,selection} = action
      return state.setIn(path, Immutable.fromJS(selection))
    }

    case LOAD_DEFAULT_POVERTY_FILTERS_DONE: {
      const {povertyFilters} = action
      return state.setIn(['filters', 'poverty'], povertyFilters)
    }

    case LOAD_DEFAULT_WOMEN_FILTERS_DONE: {
      const {womenFilters} = action
      return state.setIn(['filters', 'women'], womenFilters)
    }

    case LOAD_DEFAULT_FOOD_FILTERS_DONE: {
      const {foodFilters} = action
      return state.setIn(['filters', 'food'], foodFilters)
    }
    case LOAD_DEFAULT_AOI_FILTERS_DONE: {
      const {aoiFilters} = action
      return state.setIn(['filters', 'aoi'], aoiFilters)
    }

    case LOAD_POVERTY_CHART_DATA_DONE: {
      const {data} = action
      return state.setIn(['poverty', 'data'], data)
    }

    case LOAD_AGRICUTURAL_POPULATION_DATA_DONE: {
      const {data} = action
      return state.setIn(['women', 'population', 'data'], data)
    }
    case LOAD_AGRICUTURAL_POPULATION_DATA_ERROR: {
      const {error} = action
      return state.setIn(['women', 'population', 'error'], error).setIn(['women', 'population', 'data'], null)
    }

    case LOAD_AGRICUTURAL_DISTRIBUTION_DATA_DONE: {
      const {data} = action
      return state.setIn(['women', 'distribution', 'data'], data)
    }
    case LOAD_AGRICUTURAL_DISTRIBUTION_DATA_ERROR: {
      const {error} = action
      return state.setIn(['women', 'distribution', 'error'], error).setIn(['women', 'distribution', 'data'], null)
    }

    case LOAD_FOOD_LOSS_DATA_DONE: {
      const {data} = action
      return state.setIn(['food', 'data'], data)
    }
    case LOAD_FOOD_LOSS_ERROR: {
      const {error} = action
      return state.setIn(['food', 'data', 'error'], error)
      .setIn(['food', 'data'], null)
    }



    case APPLY_FILTER_FLAG_ON: {
      const {data} = action
      return state.setIn(['applyFlag'], true)
    }
    case 'APPLY_FILTER_FLAG_OFF': {
      const {data} = action
      return state.deleteIn(['applyFlag'])
    }

    case RESET_FILTER_FLAG_ON: {
      const {data} = action
      return state.setIn(['resetFlag'], true)
    }
    case 'RESET_FILTER_FLAG_OFF': {
      const {data} = action
      return state.deleteIn(['resetFlag'])
    }

    default: {
      return state
    }
  }
}
