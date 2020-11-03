import * as api from "../../../modules/api/index"
import RainMap, {rainMapFromApi} from "../../../modules/entities/rainfallMap/RainMap"
import {
  CHANGE_RAINFALL_MAP_FILTER,
  RAINFALL_MAP_LAYER_FULFILLED,
  RAINFALL_MAP_LAYER_PENDING,
  RAINFALL_MAP_LAYER_REJECTED
} from "../../reducers/Water"

export const transformRainMapFromApi = (apiRainMap) => rainMapFromApi(apiRainMap)

export const loadRainMapData = () => (dispatch, getState) => {
  const rainMap: RainMap = getState().getIn(['water', 'data', 'rainMap'])
  rainMap.config.layerTypes.forEach(layerType => loadRainMapLayer(rainMap.filter, layerType)(dispatch, getState))
}

export const loadRainMapLayer = (filter, layerType) => (dispatch, getState) => {
  dispatch({
    type: RAINFALL_MAP_LAYER_PENDING,
    layerType,
  })
  api.getRainfallMap(filter, layerType)
    .then(result => dispatch({
      type: RAINFALL_MAP_LAYER_FULFILLED,
      layerType,
      data: result,
    }))
    .catch(error => dispatch({
      type: RAINFALL_MAP_LAYER_REJECTED,
      layerType,
      data: error,
    }))
}

export const setRainMapFilter = (path, data) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_RAINFALL_MAP_FILTER,
    path,
    data
  })
  loadRainMapData()(dispatch, getState)
}
