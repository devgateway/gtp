import RiverLevel from "../../../entities/river/RiverLevel"
import RiverLevelChart from "../../../entities/river/RiverLevelChart"
import YearLevel from "../../../entities/river/YearLevel"
import RiverLevelChartDTO from "./RiverLevelChartDTO"
import RiverLevelLine from "./RiverLevelLine"
import DateLinePoint from "../../common/dto/DateLinePoint"

export default class RiverLevelChartBuilder {
  riverLevelChart: RiverLevelChart
  lines: Array<RiverLevelLine>

  constructor(riverLevelChart: RiverLevelChart) {
    this.riverLevelChart = riverLevelChart
  }

  build(): RiverLevelChartDTO {
    const { yearlyLevels, referenceYearlyLevels } = this.riverLevelChart.data
    const yearlyLines = this._transformYearlyLevels(yearlyLevels, false)
    const refLines = this._transformYearlyLevels(referenceYearlyLevels, true)
    this.lines = yearlyLines.concat(refLines)
    this.lines.forEach((riverLevelLine, index) => riverLevelLine.index = index)
    return new RiverLevelChartDTO(this.lines, this._getAlertLevel())
  }

  _transformYearlyLevels(yearlyLevels: Array<YearLevel>, isReference: boolean) {
    return yearlyLevels.map(({year, levels}) => {
      const points = levels.map((l: RiverLevel) => new DateLinePoint(l.normalizedDate, l.level, l.monthDay.date))
      return new RiverLevelLine(year.year, points, isReference)
    })
  }

  _getAlertLevel() {
    const station = this.riverLevelChart.config.riverStations.get(this.riverLevelChart.filter.riverStationId)
    return station && station.alertLevel
  }

}
