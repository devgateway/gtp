import YearLevel from "./YearLevel"

export default class RiverLevelData {
  yearlyLevels: Array<YearLevel>
  referenceYearlyLevels: Array<YearLevel>

  constructor({yearlyLevels, referenceYearlyLevels}) {
    this.yearlyLevels = yearlyLevels.map(yl => new YearLevel(yl))
    this.referenceYearlyLevels = referenceYearlyLevels.map(yl => new YearLevel(yl))
  }
}
