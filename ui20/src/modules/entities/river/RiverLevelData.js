import HydrologicalYear from "../HydrologicalYear"
import RiverYearNormalizedData from "./RiverYearNormalizedData"
import YearLevel from "./YearLevel"

const normalizedData = new RiverYearNormalizedData()

export default class RiverLevelData {
  yearlyLevels: Array<YearLevel>
  referenceYearlyLevels: Array<YearLevel>

  constructor({yearlyLevels, referenceYearlyLevels}) {
    const hasLeapYear = (yearLevels) => yearLevels.some(({ year }) => new HydrologicalYear(year).isLeapYear)
    normalizedData.isUseLeapYear = false // hasLeapYear(yearlyLevels) || hasLeapYear(referenceYearlyLevels)

    this.yearlyLevels = yearlyLevels.map(yl => new YearLevel(yl, normalizedData))
    this.referenceYearlyLevels = referenceYearlyLevels.map(yl => new YearLevel(yl, normalizedData))
  }

}
