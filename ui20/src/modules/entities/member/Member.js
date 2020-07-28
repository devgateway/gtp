export default class Member {
  id: number
  name: string
  description: string
  url: string

  constructor(props) {
    Object.assign(this, props)
  }

  static localeCompare(m1: Member, m2: Member) {
    return m1.name.localeCompare(m2.name)
  }
}
