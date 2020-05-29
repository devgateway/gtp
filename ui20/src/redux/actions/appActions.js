import * as api from "../../modules/api"
import {WORLD_MAP_ATTRIBUTION} from "../reducers/AppReducer"

export const loadWorldMapAttribution = () => (dispatch, getState) =>
  dispatch({
    type: WORLD_MAP_ATTRIBUTION,
    payload: api.getWorldMapAttribution().then((result) => result.copyrightText)
  })

