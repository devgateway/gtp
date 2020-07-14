import {getOrDefault, getOrDefaultMap} from "../../utils/DataUtilis"
import {DECADALS, SEASON_MONTHS} from "../Constants"

export default class RainReferenceLevelData {
  referenceYearStart: number
  referenceYearEnd: number
  yearStart: number
  yearEnd: number
  levelsByMonth: Map<number, number>
  levelsByMonthAndDecadal: Map<number, Map<number, number>>
  // normally rain reference level is cumulative, however data may be inconsistent (until ANACIM-277)
  // => these max values are only to help spot the chart max level
  maxMonthLevel: number
  maxDecadalLevel: number

  constructor({referenceYearStart, referenceYearEnd, yearStart, yearEnd, levels}) {
    this.referenceYearStart = referenceYearStart
    this.referenceYearEnd = referenceYearEnd
    this.yearStart = yearStart
    this.yearEnd = yearEnd
    this.maxMonthLevel = 0
    this.maxDecadalLevel = 0
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
      let monthLevel
      DECADALS.forEach(d => {
        let decadalValue = getOrDefault(getOrDefaultMap(this.levelsByMonthAndDecadal, month), d)
        // normally all decadals must have values and last one (decadal 3) should be used as monthLevel
        // however some data may be inconsistent, for which last decadal with value is used as month value
        monthLevel = decadalValue ? decadalValue : monthLevel
        // max decadal & month values are only for tackling inconsistent data, when it doesn't provide cumulative rain
        this.maxDecadalLevel = Math.max(decadalValue || 0, this.maxDecadalLevel);
      })
      this.maxMonthLevel = Math.max(monthLevel || 0, this.maxMonthLevel)
      this.levelsByMonth.set(month, monthLevel)
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
