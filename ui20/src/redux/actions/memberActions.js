import * as api from "../../modules/api"
import {fromApi} from "../../modules/entities/member/MemberData"
import {MEMBER} from "../reducers/GTPMember"

export const loadAllMembers = () => (dispatch, getState) =>
  dispatch({
    type: MEMBER,
    payload: api.getAllMembers().then(fromApi)
  })
