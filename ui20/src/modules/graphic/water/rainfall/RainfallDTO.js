import MonthDecadal from "../../../utils/MonthDecadal"

export default class RainfallDTO {
  barData: Array
  keys: Array<string>
  indexBy: string
  monthDecadal: MonthDecadal
  keysWithRefs
  maxValue: number
  hasData: boolean

  constructor(keys: Array<string>, indexBy: string) {
    this.keys = keys
    this.barData = []
    this.indexBy = indexBy
    this.maxValue = 0
    this.hasData = false
  }

}
