export default class DrySequenceChartDTO {
  indexBy: string
  keys: Array<number>
  barData: Array
  groupMode: string
  colors: Array<string>
  isDaysWithRain: boolean
  hasData: boolean

  constructor(indexBy, keys, barData, isDaysWithRain, hasData: boolean) {
    this.indexBy = indexBy
    this.keys = keys
    this.barData = barData
    this.groupMode = 'grouped'
    this.isDaysWithRain = isDaysWithRain
    this.hasData = hasData
  }
}
