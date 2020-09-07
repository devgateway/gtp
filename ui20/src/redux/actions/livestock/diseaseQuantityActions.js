import DiseaseQuantityMapBuilder from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapBuilder"
import DiseaseQuantityMapDTO from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapDTO"

export const getDiseaseQuantities = (): DiseaseQuantityMapDTO => (dispatch, getState) => {
  const commonConfig = getState().getIn(['app', 'data', 'commonConfig'])
  const diseaseQuantityChart = getState().getIn(['livestock', 'data', 'diseaseQuantityChart'])
  return {
    diseaseMapDTO: new DiseaseQuantityMapBuilder(diseaseQuantityChart, commonConfig).build()
  }
}
