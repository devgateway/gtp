import {getOrDefault, getOrDefaultMap} from "../../utils/DataUtilis"
import RainLevel from "./RainLevel"
import RainReferenceLevelData from "./RainReferenceLevelData"


export default class RainLevelData {
  levels: Array<RainLevel>
  levelsByYearMonthDecadal: Map<number, Map<number, Map<number, number>>>
  levelsByYearMonth: Map<number, Map<number, number>>
  referenceLevels: Array<RainReferenceLevelData>
  maxMonthLevelByYear: Map<number, number>
  maxDecadalLevelByYear: Map<number, number>

  constructor({ levels, referenceLevels } = {}) {
    this.levels = (levels || []).map(level => new RainLevel(level))
    this.referenceLevels = (referenceLevels || []).map(refLevel => new RainReferenceLevelData(refLevel))
    this.maxMonthLevelByYear = new Map()
    this.maxDecadalLevelByYear = new Map()
    this._init();
  }

  _init() {
    this.levelsByYearMonthDecadal = new Map()
    this.levels.forEach(rainLevel => {
      const yearLevels = getOrDefaultMap(this.levelsByYearMonthDecadal, rainLevel.year)
      const monthLevels = getOrDefaultMap(yearLevels, rainLevel.month)
      monthLevels.set(rainLevel.decadal, rainLevel.value)
    })

    this.levelsByYearMonth = new Map()
    for (const year of this.levelsByYearMonthDecadal.keys()) {
      const yearDecadalLevels = this.levelsByYearMonthDecadal.get(year)
      const yearLevels = new Map()
      let maxMonthLevel = 0
      let maxDecadalLevel = 0
      for (const month of yearDecadalLevels.keys()) {
        let monthLevel = 0
        for (const decadalValue of yearDecadalLevels.get(month).values()) {
          monthLevel += decadalValue
          maxDecadalLevel = Math.max(maxDecadalLevel, decadalValue)
        }
        yearLevels.set(month, monthLevel)
        maxMonthLevel = Math.max(maxMonthLevel, monthLevel)
      }
      this.levelsByYearMonth.set(year, yearLevels)
      this.maxMonthLevelByYear.set(year, maxMonthLevel)
      this.maxDecadalLevelByYear.set(year, maxDecadalLevel)
    }
  }

  getMonthLevel(year, month) {
    return getOrDefault(getOrDefaultMap(this.levelsByYearMonth, year), month)
  }

  getDecadalLevel(year, month, decadal) {
    return getOrDefault(getOrDefaultMap(getOrDefaultMap(this.levelsByYearMonthDecadal, year), month), decadal)
  }

  getMaxMonthLevel(year) {
    return getOrDefault(this.maxMonthLevelByYear, year, 0)
  }

  getMaxDecadalLevel(year) {
    return getOrDefault(this.maxDecadalLevelByYear, year, 0)
  }

}
