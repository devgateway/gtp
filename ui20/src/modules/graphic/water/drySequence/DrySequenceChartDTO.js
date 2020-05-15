export default class DrySequenceChartDTO {
  indexBy: string
  keys: Array<number>
  barData: Array
  groupMode: string
  colors: Array<string>
  isDaysWithRain: boolean

  constructor(indexBy, keys, barData, isDaysWithRain) {
    this.indexBy = indexBy
    this.keys = keys
    this.barData = barData
    this.groupMode = 'grouped'
    this.isDaysWithRain = isDaysWithRain
  }
}
