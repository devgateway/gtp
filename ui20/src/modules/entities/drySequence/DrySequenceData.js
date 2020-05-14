import {getOrDefaultMap} from "../../utils/DataUtilis"
import {DecadalDaysRain, MonthDaysRain} from "./DaysRain"

const MISSING_DATA = undefined

export default class DrySequenceData {
  decadalDaysWithRain: Map<number, Map<number, DecadalDaysRain>>
  monthDaysWithRain: Map<number, MonthDaysRain>

  constructor({daysWithRain}) {
    this.decadalDaysWithRain = daysWithRain.reduce((map: Map, d) => {
      const decadalDaysRain = new DecadalDaysRain(d)
      getOrDefaultMap(map, decadalDaysRain.month).set(decadalDaysRain.decadal, decadalDaysRain)
      return map
    }, new Map())

    this.monthDaysWithRain = new Map()
    for (const month of this.decadalDaysWithRain.keys()) {
      let withRain = 0
      let withoutRain = 0
      for (const [, decadalDaysRain] of this.decadalDaysWithRain.get(month)) {
        withRain += decadalDaysRain.daysRain.withRain
        withoutRain += decadalDaysRain.daysRain.withoutRain
      }
      this.monthDaysWithRain.set(month, new MonthDaysRain(month, withRain, withoutRain))
    }
  }

  getMonthLevel(month: number, isDaysWithRain: boolean) {
    const monthDaysRain: MonthDaysRain = this.monthDaysWithRain.get(month)
    return !monthDaysRain ? MISSING_DATA : monthDaysRain.daysRain.getRain(isDaysWithRain)
  }

  getDecadalLevel(month: number, decadal: number, isDaysWithRain: boolean) {
    const decadalDaysRain: DecadalDaysRain = getOrDefaultMap(this.decadalDaysWithRain, month).get(decadal)
    return !decadalDaysRain ? MISSING_DATA : decadalDaysRain.daysRain.getRain(isDaysWithRain)
  }
}


