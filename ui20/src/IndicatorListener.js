import {
  loadDefaultFilters,
  loadPovertyChartData,
  loadGlobalIndicators,

  loadAgricuturalPopulationData,
  loadAgricuturalDistribution,

  loadDefaultPovertyFilters,
  loadDefaultWomenFilters,

  loadDefaultFoodFilters,
  loadFoodLossData,

  loadDefaultAOIFilters,
  loadAOIsubsidies,
  loadAOItotalbudget,

  refresh
} from './modules/Indicator'
import {
  loadDataItems
} from './modules/Data'


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
  const lossType = state.getIn(['data', 'items', 'lossType']);
  const activities = state.getIn(['data', 'items', 'professionalActivity']);
  const genders = state.getIn(['data', 'items', 'gender']);
  const ageGroups = state.getIn(['data', 'items', 'ageGroup']);
  const range = state.getIn(['data', 'items', 'range']);

  const methodOfEnforcements=state.getIn(['data', 'items', 'methodOfEnforcement']);

  const indexType=state.getIn(['data', 'items', 'indexType']);
  const indexType1=state.getIn(['data', 'items', 'indexType/1']);
  const indexType2=state.getIn(['data', 'items', 'indexType/2']);

  //Main filters
  const itemsLoaded = (regions && crops && years) != null
  const filtersReady = (globalFilters && itemsLoaded) != null

  if (itemsLoaded && !filtersReady && !flags['loadDefaultFiltersCalled']) {
    console.log('Listener -> Loading Default Main Filters Options ')
    flags['loadDefaultFiltersCalled'] = true //avoid loop if error
    store.dispatch(loadDefaultFilters())
  }

  //Specific filters status
  const povertyFiltersItemReady = (activities && ageGroups && range) != null
  const povertyFiltersReady = filters.get('poverty') != null

  const womenFiltersItemReady = (genders && ageGroups && methodOfEnforcements) != null
  const womenFiltersReady = filters.get('women') != null

  const foodFiltersItemReady = (lossType) != null
  const foodFiltersReady = filters.get('food') != null


  const aoiFiltersItemReady = (indexType1&&indexType2) != null
  const aoiFiltersReady = filters.get('aoi') != null


  //main filters are ready let's load poverty filters
  if (filtersReady && !povertyFiltersReady && !flags['loadRangeCalled']) {
    console.log("Listener -> Loading poverty filter options")
    flags['loadRangeCalled'] = true //avoid loop if error
    store.dispatch(loadDataItems('range', 'poverty'))
  }


  //all items were loaded then let's load default selected options
  if (povertyFiltersItemReady && !povertyFiltersReady && !flags['loadDefaultPovertyFiltersCalled']) {

    flags['loadDefaultPovertyFiltersCalled'] = true
    console.log("Listener -> Loading deafult poverty filter options")
    store.dispatch(loadDefaultPovertyFilters())
  }

  if (povertyFiltersReady && !flags['loadPovertyDataCalled']) {
    flags['loadPovertyDataCalled'] = true;
    console.log('Listener -> Loading Poverty Data')
    store.dispatch(loadPovertyChartData())
  }



  //Women initial load
  if (womenFiltersItemReady && !womenFiltersReady && !flags['loadDefaultWomenFiltersCalled']) {

    flags['loadDefaultWomenFiltersCalled'] = true
    console.log("Listener -> Loading deafult women filter options")
    store.dispatch(loadDefaultWomenFilters())
  }

  if (womenFiltersReady && !flags['loadWomenDataCalled']) {
    flags['loadWomenDataCalled'] = true;
    console.log('Listener -> Loading women Data')
    store.dispatch(loadAgricuturalPopulationData())
    store.dispatch(loadAgricuturalDistribution())
  }



  //food initial load
  if (foodFiltersItemReady && !foodFiltersReady && !flags['loadDefaultFoodFiltersCalled']) {
    flags['loadDefaultFoodFiltersCalled'] = true
    console.log("Listener -> Loading deafult food filter options")
    store.dispatch(loadDefaultFoodFilters())
  }

  if (foodFiltersReady && !flags['foodDataCalled']) {
    flags['foodDataCalled'] = true;
    console.log('Listener -> Load Food Data')
    store.dispatch(loadFoodLossData())
  }



  //AOI  inital load
  if (aoiFiltersItemReady && !aoiFiltersReady && !flags['loadDefaultAOIFiltersCalled']) {
    flags['loadDefaultAOIFiltersCalled'] = true
    console.log("Listener -> Loading deafult food filter options")
    store.dispatch(loadDefaultAOIFilters())
  }

  if (aoiFiltersReady && !flags['aoiDataCalled']) {
    flags['aoiDataCalled'] = true;
    console.log('Listener -> Load Food Data')

    store.dispatch(loadAOIsubsidies())
    store.dispatch(loadAOItotalbudget())
  }




  //initial data load
  if (filtersReady && !flags['loadGlobalIndicatorsCalled']) {
    flags['loadGlobalIndicatorsCalled'] = true
    console.log('Listener -> Loading Global Indicators')
      store.dispatch(loadGlobalIndicators())

  }

  apply(store)
  reset(store)
}



let applyFlags = []

/*Apply filters sequence*/
const apply = (store) => {
  const state = store.getState()
  const rangeLoading = state.getIn(['data','items','status','range','loading']);

  if (state.getIn(['indicator', 'applyFlag']) === true) {

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
const reset = (store) => {

  const state = store.getState()
  const filters = state.getIn(['indicator', 'filters'])
  const rangeLoading = state.getIn(['data', 'items', 'status', 'range', 'loading']);
  const povertyFiltersReady = filters.get('poverty') != null

  if (state.getIn(['indicator', 'resetFlag']) == true) {

    if (resetFlags['updatingFilters']) {
      console.log('flag off apply filter')
      dispatchSingleAction(store, "RESET_FILTER_FLAG_OFF")
      store.dispatch(refresh())
      console.log('turn false all flags')
      resetFlags = []

    } else if (!resetFlags['loadDefaultFiltersCalled']) {
      console.log('Loading Default Main Filters Options ')
      resetFlags['loadDefaultFiltersCalled'] = true //avoid loop if error
      store.dispatch(loadDefaultFilters())


    }else if (!resetFlags['loadDefaultWomenFiltersCalled']) {
        console.log('Loading Default loadDefaultWomenFiltersCalled Filters Options ')
        resetFlags['loadDefaultWomenFiltersCalled'] = true //avoid loop if error
        store.dispatch(loadDefaultWomenFilters())

    } else if(!resetFlags['loadDefaultFoodFiltersCalled']){
      console.log('Loading Default loadDefaultFoodFiltersCalled Filters Options ')
      resetFlags['loadDefaultFoodFiltersCalled'] = true //avoid loop if error
      store.dispatch(loadDefaultFoodFilters())

    }else if(!resetFlags['loadDefaultAOIFiltersCalled']){
      console.log('Loading Default loadDefaultAOIFiltersCalled Filters Options ')
      resetFlags['loadDefaultAOIFiltersCalled'] = true //avoid loop if error
      store.dispatch(loadDefaultAOIFilters())

    }else if (rangeLoading == false && resetFlags['reloadRange'] && !resetFlags['updatingFilters']) {
      resetFlags['updatingFilters'] = true
      console.log('Reload filter options')
      store.dispatch(loadDefaultPovertyFilters())

    } else if (!resetFlags['reloadRange']) {
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
