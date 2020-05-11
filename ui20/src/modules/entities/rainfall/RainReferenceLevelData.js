import {getOrDefault, getOrDefaultMap} from "../../utils/DataUtilis"

export default class RainReferenceLevelData {
  referenceYearStart: number
  referenceYearEnd: number
  levelsByMonth: Map<number, Map<number, number>>
  levelsByMonthAndDecadal: Map<number, Map<number, number>>

  constructor({referenceYearStart, referenceYearEnd, levels}) {
    this.referenceYearStart = referenceYearStart
    this.referenceYearEnd = referenceYearEnd
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
        let monthLevel = 0
        for (const decadalValue of this.levelsByMonthAndDecadal.get(month).values()) {
          monthLevel += decadalValue
        }
        this.levelsByMonth.set(month, monthLevel)
      }
  }

  getMonthLevel(month) {
    return getOrDefault(this.levelsByMonth, month)
  }

  getDecadalLevel(month, decadal) {
    return getOrDefault(getOrDefaultMap(this.levelsByMonthAndDecadal, month), decadal)
  }

}
