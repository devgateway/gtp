export default class Market {
  id: number
  name: string
  marketDays
  latitude: number
  longitude: number
  departmentId: number
  typeId: number

  constructor(props) {
    Object.assign(this, props)
  }

  static localeCompare(m1: Market, m2: Market) {
    return m1.name.localeCompare(m2.name)
  }
}
