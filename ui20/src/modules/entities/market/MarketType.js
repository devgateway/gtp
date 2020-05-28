export default class MarketType {
  id: number
  name: string
  label: string

  constructor({ id, name, label}) {
    this.id = id
    this.name = name
    this.label = label
  }

}
