import {getOrDefault, getOrDefaultMap} from "../../utils/DataUtilis"
import RainLevel from "./RainLevel"
import RainReferenceLevelData from "./RainReferenceLevelData"


export default class RainLevelData {
  levels: Array<RainLevel>
  levelsByYearMonthDecadal: Map<number, Map<number, Map<number, number>>>
  levelsByYearMonth: Map<number, Map<number, number>>
  referenceLevels: Array<RainReferenceLevelData>
  totalLevelByYear: Map<number, number>

  constructor({ levels, referenceLevels } = {}) {
    this.levels = (levels || []).map(level => new RainLevel(level))
    this.referenceLevels = (referenceLevels || []).map(refLevel => new RainReferenceLevelData(refLevel))
    this.totalLevelByYear = new Map()
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
      let yearTotal = 0
      for (const month of yearDecadalLevels.keys()) {
        let monthLevel = 0
        for (const decadalValue of yearDecadalLevels.get(month).values()) {
          monthLevel += decadalValue
          yearTotal += decadalValue
        }
        yearLevels.set(month, monthLevel)
      }
      this.levelsByYearMonth.set(year, yearLevels)
      this.totalLevelByYear.set(year, yearTotal)
    }
  }

  getMonthLevel(year, month) {
    return getOrDefault(getOrDefaultMap(this.levelsByYearMonth, year), month)
  }

  getDecadalLevel(year, month, decadal) {
    return getOrDefault(getOrDefaultMap(getOrDefaultMap(this.levelsByYearMonthDecadal, year), month), decadal)
  }

  getYearTotal(year) {
    return getOrDefault(this.totalLevelByYear, year, 0)
  }

}
