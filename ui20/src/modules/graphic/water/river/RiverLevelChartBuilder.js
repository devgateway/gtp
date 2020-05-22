import RiverLevel from "../../../entities/river/RiverLevel"
import RiverLevelChart from "../../../entities/river/RiverLevelChart"
import RiverStation from "../../../entities/river/RiverStation"
import YearLevel from "../../../entities/river/YearLevel"
import {hydrologicalYearToString} from "../CommonDTO"
import RiverLevelChartDTO from "./RiverLevelChartDTO"
import RiverLevelLine from "./RiverLevelLine"
import RiverLevelPoint from "./RiverLevelPoint"

export default class RiverLevelChartBuilder {
  riverLevelChart: RiverLevelChart
  lines: Array<RiverLevelLine>

  constructor(riverLevelChart: RiverLevelChart) {
    this.riverLevelChart = RiverLevelChart
  }

  build(): RiverLevelChartDTO {
    const { yearlyLevels, referenceYearlyLevels } = this.riverLevelChart.data
    const yearlyLines = this._transformYearlyLevels(yearlyLevels, false)
    const refLines = this._transformYearlyLevels(referenceYearlyLevels, true)
    this.lines = yearlyLines.concat(refLines)
    return new RiverLevelChartDTO(this.lines, this._getAlertLevel())
  }

  _transformYearlyLevels(yearlyLevels: Array<YearLevel>, isReference: boolean) {
    return yearlyLevels.map(({year, levels}) => {
      const points = levels.map((l: RiverLevel) => new RiverLevelPoint(l.normalizedDate, l.level, l.monthDay.date))
      return new RiverLevelLine(hydrologicalYearToString(year.year), points, isReference)
    })
  }

  _getAlertLevel() {
    const riverStation:RiverStation = this.riverLevelChart.config.riverStations
      .find(r => r.id === this.riverLevelChart.filter.riverStationId)
    return riverStation.alertLevel
  }

}
