export default class DrySequenceChartDTO {
  indexBy: string
  keys: Array<number>
  barData: Array
  groupMode: string
  colors: Array<string>

  constructor(indexBy, keys, barData) {
    this.indexBy = indexBy
    this.keys = keys
    this.barData = barData
    this.groupMode = 'grouped'
  }
}
