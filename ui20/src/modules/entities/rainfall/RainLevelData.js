import {getOrDefault} from "../../utils/DataUtilis"
import RainLevel from "./RainLevel"

const defaultMapFunc = () => new Map()
const getOrDefaultMap = (map, key) => getOrDefault(map, key, null, defaultMapFunc)

export default class RainLevelData {
  levels: Array<RainLevel>
  levelsByYearMonthDecadal: Map<number, Map<number, Map<number, number>>>
  levelsByYearMonth: Map<number, Map<number, number>>

  constructor({ levels }) {
    this.levels = levels.map(level => new RainLevel(level))
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
      for (const month of yearDecadalLevels.keys()) {
        let monthLevel = 0
        for (const decadalValue of yearDecadalLevels.get(month).values()) {
          monthLevel += decadalValue
        }
        yearLevels.set(month, monthLevel)
      }
      this.levelsByYearMonth.set(year, yearLevels)
    }
  }

  getMonthLevel(year, month) {
    return getOrDefault(getOrDefaultMap(this.levelsByYearMonth, year), month)
  }

  getDecadalLevel(year, month, decadal) {
    return getOrDefault(getOrDefaultMap(getOrDefaultMap(this.levelsByYearMonthDecadal, year), month), decadal)
  }
}
