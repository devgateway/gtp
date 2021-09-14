import * as api from "../../modules/api"
import CommonConfig from "../../modules/entities/config/CommonConfig"
import FMConfig from "../../modules/entities/config/FMConfig"
import {CNSC_HEADER, COMMON_CONFIG_UPDATE, MENU_TOGGLE, MAP_ATTRIBUTION, FM_CONFIG} from "../reducers/AppReducer"

export const loadCNSCHeader = () => (dispatch, getState) =>
  dispatch({
    type: CNSC_HEADER,
    payload: api.getCNSCHeader()
  })

export const loadFMConfig = () => (dispatch, getState) =>
  dispatch({
    type: FM_CONFIG,
    payload: api.getFMConfig().then(featuresList => new FMConfig(featuresList))
  })

export const updateCommonConfig = (apiCommonConfig) => (dispatch, getState) => {
  const commonConfig = new CommonConfig(apiCommonConfig)
  dispatch({
    type: COMMON_CONFIG_UPDATE,
    data: commonConfig,
  })
  return commonConfig
}

const mapAttribution = {
  "world": api.getWorldMapAttribution,
  "topo": api.getTopoMapAttribution,
}

export const loadMapAttribution = (type) => (dispatch, getState) =>
  dispatch({
    type: MAP_ATTRIBUTION,
    payload: mapAttribution[type]().then((result) => ({
      type,
      copyrightText :result.copyrightText}))
  })

export const toggleMenu = (isOpened) => (dispatch, getState) =>
  dispatch({
    type: MENU_TOGGLE,
    data: isOpened
  })

export const connectionCheck = () => (dispatch, getState) => api.doTest()
