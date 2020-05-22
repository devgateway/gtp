import HydrologicalYear from "../HydrologicalYear"
import YearLevel from "./YearLevel"

export default class RiverLevelData {
  yearlyLevels: Array<YearLevel>
  referenceYearlyLevels: Array<YearLevel>

  constructor({yearlyLevels, referenceYearlyLevels}) {
    const normalizedHydrologicalYear = new HydrologicalYear(new Date().getFullYear())
    this.yearlyLevels = yearlyLevels.map(yl => new YearLevel(yl, normalizedHydrologicalYear))
    this.referenceYearlyLevels = referenceYearlyLevels.map(yl => new YearLevel(yl, normalizedHydrologicalYear))
  }
}
