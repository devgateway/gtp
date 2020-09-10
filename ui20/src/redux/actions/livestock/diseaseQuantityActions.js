import * as api from "../../../modules/api"
import {diseaseQuantityDataFromApi} from "../../../modules/entities/diseaseSituation/DiseaseQuantityChart"
import DiseaseQuantityData from "../../../modules/entities/diseaseSituation/DiseaseQuantityData"
import DiseaseQuantityMapBuilder from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapBuilder"
import DiseaseQuantityMapDTO from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapDTO"
import {CHANGE_DISEASE_QUANTITY_FILTER, FILTER_DISEASE_QUANTITY} from "../../reducers/Livestock"

export const getDiseaseQuantities = (): DiseaseQuantityMapDTO => (dispatch, getState) => {
  const commonConfig = getState().getIn(['app', 'data', 'commonConfig'])
  const diseaseQuantityChart = getState().getIn(['livestock', 'data', 'diseaseQuantityChart'])
  return {
    diseaseMapDTO: new DiseaseQuantityMapBuilder(diseaseQuantityChart, commonConfig).build()
  }
}


export const setDiseaseQuantityFilter = (path, data, isMonth = false) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_DISEASE_QUANTITY_FILTER,
    path,
    data
  })

  const isDiseaseQuantityLoaded = getState().getIn(['livestock', 'data', 'isDiseaseQuantityLoaded'])

  if (!isMonth || !isDiseaseQuantityLoaded) {
    const {year, diseaseId, month} = getState().getIn(['livestock', 'data', 'diseaseQuantityChart', 'filter'])

    return dispatch({
      type: FILTER_DISEASE_QUANTITY,
      payload:
        api.getDiseaseQuantity(year, diseaseId)
        .then(diseaseQuantityDataFromApi)
        .then((data: DiseaseQuantityData) => {
          const now = new Date()
          const nowMonth = now.getMonth() + 1
          if (now.getFullYear() === year && month >= nowMonth) {
            dispatch({
              type: CHANGE_DISEASE_QUANTITY_FILTER,
              path: ['month'],
              data: data.lastMonth
            })
          }
          return data
        })
    })
  }
}
