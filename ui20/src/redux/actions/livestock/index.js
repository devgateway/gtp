import * as api from "../../../modules/api"
import LivestockConfig from "../../../modules/entities/config/LivestockConfig"
import {diseaseQuantityFromApi} from "../../../modules/entities/diseaseSituation/DiseaseQuantityChart"
import {LIVESTOCK} from "../../reducers/Livestock"
import {updateCommonConfig} from "../appActions"

export const loadAllLivestockData = () => (dispatch, getState) => {
  dispatch({
    type: LIVESTOCK,
    payload: api.getAllLivestock().then(result =>
      transformAll(result, updateCommonConfig(result.commonConfig)(dispatch, getState)))
  })
}

const transformAll = (allData) => {
  const livestockConfig = new LivestockConfig(allData.livestockConfig)
  return {
    livestockConfig,
    diseaseQuantityChart: diseaseQuantityFromApi(allData.diseaseQuantityChart),
  }
}
