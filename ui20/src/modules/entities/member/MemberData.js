import Member from "./Member"

export default class MemberData {
  members: Array<Member>

  constructor(apiMembers = []) {
    this.members = apiMembers.map(m => new Member(m)).sort(Member.localeCompare)
  }
}

export const fromApi = (members) => new MemberData(members)
