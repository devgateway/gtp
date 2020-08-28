import * as api from "../../modules/api"
import {dataFromApi, fromApi} from "../../modules/entities/bulletins/BulletinReport"
import {BULLETIN, CHANGE_FILTER_BULLETIN, FILTER_BULLETIN} from "../reducers/GTPBulletin"

export const loadAllBulletins = () => (dispatch, getState) =>
  dispatch({
    type: BULLETIN,
    payload: api.getAllBulletins().then(fromApi)
  })

export const setYears = (years) => (dispatch, getState) =>
  dispatch({
    type: CHANGE_FILTER_BULLETIN,
    path: ['data', 'filter', 'years'],
    data: years
  })

export const setLocation = (locationId) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_FILTER_BULLETIN,
    path: ['data', 'filter', 'locationId'],
    data: locationId
  })
  return dispatch({
    type: FILTER_BULLETIN,
    payload: api.getBulletins(locationId).then(dataFromApi)
  })
}
