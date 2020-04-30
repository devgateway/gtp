import * as api from "../../modules/api/index";
import {WATER_RESOURCES} from "../reducers/Water";

export const loadAllWaterData = () => (dispatch, getState) => {
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources()
  });
}
