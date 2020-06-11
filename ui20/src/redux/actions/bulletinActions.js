import * as api from "../../modules/api"
import {fromApi} from "../../modules/entities/bulletins/BulletinReport"
import {BULLETIN} from "../reducers/GTPBulletin"

export const loadAllBulletins = () => (dispatch, getState) =>
  dispatch({
    type: BULLETIN,
    payload: api.getAllBulletins().then(fromApi)
  })
