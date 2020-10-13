import * as api from "../../modules/api"
import CommonConfig from "../../modules/entities/config/CommonConfig"
import {CNSC_HEADER, COMMON_CONFIG_UPDATE, MENU_TOGGLE, WORLD_MAP_ATTRIBUTION} from "../reducers/AppReducer"

export const loadCNSCHeader = () => (dispatch, getState) =>
  dispatch({
    type: CNSC_HEADER,
    payload: api.getCNSCHeader()
  })

export const updateCommonConfig = (apiCommonConfig) => (dispatch, getState) => {
  const commonConfig = new CommonConfig(apiCommonConfig)
  dispatch({
    type: COMMON_CONFIG_UPDATE,
    data: commonConfig,
  })
  return commonConfig
}

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
