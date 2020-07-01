import {getOrDefault, getOrDefaultMap} from "../../utils/DataUtilis"
import {SEASON_MONTHS} from "../Constants"

export default class RainReferenceLevelData {
  referenceYearStart: number
  referenceYearEnd: number
  yearStart: number
  yearEnd: number
  levelsByMonth: Map<number, number>
  levelsByMonthAndDecadal: Map<number, Map<number, number>>

  constructor({referenceYearStart, referenceYearEnd, yearStart, yearEnd, levels}) {
    this.referenceYearStart = referenceYearStart
    this.referenceYearEnd = referenceYearEnd
    this.yearStart = yearStart
    this.yearEnd = yearEnd
    this._init(levels)
  }

  _init(levels: Array) {
    this.levelsByMonthAndDecadal = new Map()
    levels.forEach(rainLevel => {
      const monthLevels = getOrDefaultMap(this.levelsByMonthAndDecadal, rainLevel.month)
      monthLevels.set(rainLevel.decadal, rainLevel.value)
    })

    this.levelsByMonth = new Map()
    for (const month of this.levelsByMonthAndDecadal.keys()) {
      this.levelsByMonth.set(month, this.levelsByMonthAndDecadal.get(month).get(3))
    }
  }

  getMonthLevel(month) {
    return getOrDefault(this.levelsByMonth, month)
  }

  getDecadalLevel(month, decadal) {
    return getOrDefault(getOrDefaultMap(this.levelsByMonthAndDecadal, month), decadal)
  }

  getTotalLevel() {
    return getOrDefault(this.levelsByMonth, SEASON_MONTHS[SEASON_MONTHS.length - 1])
  }

}
