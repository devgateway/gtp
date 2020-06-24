import MonthDecadal from "../../../utils/MonthDecadal"

export default class RainfallDTO {
  barData: Array
  keys: Array<string>
  indexBy: string
  monthDecadal: MonthDecadal
  keysWithRefs

  constructor(keys: Array<string>, indexBy: string) {
    this.keys = keys
    this.barData = []
    this.indexBy = indexBy
  }

}
