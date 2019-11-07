import {
  loadDefaultFilters,
  loadDefaultPovertyFilters,
  loadPovertyChartData,
  loadGlobalIndicators,
  refresh
} from './modules/Indicator'
import {
  loadDataItems
} from './modules/Data'

const selectedRegions = []

const selectedCrops = []

const selectedYears = []


const flags = []

/*
Controls loading sequence
*/
const listener = (store) => {

  const state = store.getState()
  const filters = state.getIn(['indicator', 'filters'])
  const globalFilters = filters.getIn(['global']);
  const regions = state.getIn(['data', 'items', 'region']);
  const crops = state.getIn(['data', 'items', 'cropType']);
  const years = state.getIn(['data', 'items', 'year']);

  //check all needed items were loaded
  const itemsLoaded = (regions && crops && years) != null

  //check if default options were loaded
  const filtersReady = (globalFilters && itemsLoaded) != null

  if (itemsLoaded && !filtersReady && !flags['loadDefaultFiltersCalled']) {
    console.log('Loading Default Main Filters Options ')
    flags['loadDefaultFiltersCalled'] = true //avoid loop if error
    store.dispatch(loadDefaultFilters())
  }

  //poverty options items
  const activities = state.getIn(['data', 'items', 'professionalActivity']);
  const genders = state.getIn(['data', 'items', 'gender']);
  const ageGroups = state.getIn(['data', 'items', 'ageGroup']);
  const range = state.getIn(['data', 'items', 'range']);

  const povertyFiltersItemReady = (activities && ageGroups && range) != null
  const povertyFiltersReady = filters.get('poverty') != null
  const poveryData = state.getIn(['indicator', 'poverty', 'data'])


  //main filters are ready let's load poverty filters
  if (filtersReady && !povertyFiltersReady && !flags['loadRangeCalled']) {
    console.log("Loading poverty filter options")
    flags['loadRangeCalled'] = true //avoid loop if error
    store.dispatch(loadDataItems('range', 'poverty'))
  }

  //all items were loaded then let's load default selected options
  if (povertyFiltersItemReady && !povertyFiltersReady && !flags['loadDefaultPovertyFiltersCalled']) {
    flags['loadDefaultPovertyFiltersCalled'] = true
    console.log("Loading deafult poverty filter options")
    store.dispatch(loadDefaultPovertyFilters())
  }

  //initial data load
  if (filtersReady && !flags['loadGlobalIndicatorsCalled']) {
    flags['loadGlobalIndicatorsCalled'] = true
    console.log('Loading Global Indicators')
    store.dispatch(loadGlobalIndicators())
  }

  if (povertyFiltersReady && !flags['loadPovertyDataCalled']) {
    flags['loadPovertyDataCalled'] = true;
    console.log('Loading Poverty Data')
    store.dispatch(loadPovertyChartData())
  }

  apply(store)
  reset(store)
}



let applyFlags = []

/*Apply filters sequence*/
const apply = (store) => {
  const state = store.getState()
  const filters = state.getIn(['indicator', 'filters'])
  const rangeLoading = state.getIn(['data','items','status','range','loading']);
  const povertyFiltersReady = filters.get('poverty') != null

  if (state.getIn(['indicator', 'applyFlag']) == true) {

    if(applyFlags['updatingFilters']){
        console.log('flag off apply filter')
        dispatchSingleAction(store, "APPLY_FILTER_FLAG_OFF")
        store.dispatch(refresh())
        console.log('turn false all flags')
        applyFlags=[]

    }else  if (rangeLoading==false && applyFlags['reloadRange'] && !applyFlags['updatingFilters']) {
      applyFlags['updatingFilters'] = true
      console.log('Reload filter options')
      store.dispatch(loadDefaultPovertyFilters())

    }else if (!applyFlags['reloadRange']) {
      applyFlags['reloadRange'] = true
      console.log('reload range')
      store.dispatch(loadDataItems('range', 'poverty'))

    }

  }
}


/*Reset filters sequence*/
let resetFlags = []
const reset=(store)=>{

  const state = store.getState()
  const filters = state.getIn(['indicator', 'filters'])
  const rangeLoading = state.getIn(['data','items','status','range','loading']);
  const povertyFiltersReady = filters.get('poverty') != null

  if (state.getIn(['indicator', 'resetFlag']) == true) {

    if(resetFlags['updatingFilters']){
        console.log('flag off apply filter')
        dispatchSingleAction(store, "RESET_FILTER_FLAG_OFF")
        store.dispatch(refresh())
        console.log('turn false all flags')
        resetFlags=[]

    }else  if (!resetFlags['loadDefaultFiltersCalled']) {
      console.log('Loading Default Main Filters Options ')
      resetFlags['loadDefaultFiltersCalled'] = true //avoid loop if error
      store.dispatch(loadDefaultFilters())

    }

    else  if (rangeLoading==false && resetFlags['reloadRange'] && !resetFlags['updatingFilters']) {
      resetFlags['updatingFilters'] = true
      console.log('Reload filter options')
      store.dispatch(loadDefaultPovertyFilters())

    }else if (!resetFlags['reloadRange']) {
      resetFlags['reloadRange'] = true
      console.log('reload range')
      store.dispatch(loadDataItems('range', 'poverty'))

    }

  }
}


const dispatchSingleAction = (store, type) => {
  store.dispatch({
    type
  })
}
export default listener
