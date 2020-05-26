import HydrologicalYear from "../HydrologicalYear"
import RiverLevel from "./RiverLevel"
import RiverYearNormalizedData from "./RiverYearNormalizedData"

export default class YearLevel {
  year: HydrologicalYear
  levels: Array<RiverLevel>

  constructor({ year, levels }, normalizedData: RiverYearNormalizedData) {
    this.year = new HydrologicalYear(year)
    this.levels = this._toFullYearLevels(levels, normalizedData)
  }

  _toFullYearLevels(apiYearLevels: Array, normalizedData: RiverYearNormalizedData) {
    const levelsByMonthDay: Map = apiYearLevels.reduce((map, { monthDay, level}) => {
      const rl = new RiverLevel(monthDay, level, this.year, normalizedData.normalizedHY)
      return map.set(rl.monthDay.monthDay, rl)
    }, new Map())

    return normalizedData.monthDays.map(monthDay => levelsByMonthDay.has(monthDay)
      ? levelsByMonthDay.get(monthDay)
      : new RiverLevel(monthDay, null, this.year, normalizedData.normalizedHY)
    )
  }

}
