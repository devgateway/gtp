import * as api from "../../modules/api"
import {fromApi} from "../../modules/entities/bulletins/BulletinReport"
import {BULLETIN, FILTER_BULLETIN} from "../reducers/GTPBulletin"

export const loadAllBulletins = () => (dispatch, getState) =>
  dispatch({
    type: BULLETIN,
    payload: api.getAllBulletins().then(fromApi)
  })

export const setYears = (years) => (dispatch, getState) =>
  dispatch({
    type: FILTER_BULLETIN,
    data: years
  })
