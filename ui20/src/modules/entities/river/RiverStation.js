export default class RiverStation {
  id: number
  name: string
  riverName: string
  alertLevel: number

  constructor({id, name, river, alertLevel}) {
    this.id = id
    this.name = name
    this.riverName = river
    this.alertLevel = alertLevel
  }

}
