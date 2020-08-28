import * as api from "../../modules/api"
import {MENU_TOGGLE, WORLD_MAP_ATTRIBUTION} from "../reducers/AppReducer"

export const loadWorldMapAttribution = () => (dispatch, getState) =>
  dispatch({
    type: WORLD_MAP_ATTRIBUTION,
    payload: api.getWorldMapAttribution().then((result) => result.copyrightText)
  })

export const toggleMenu = (isOpened) => (dispatch, getState) =>
  dispatch({
    type: MENU_TOGGLE,
    data: isOpened
  })

export const connectionCheck = () => (dispatch, getState) => api.doTest()
