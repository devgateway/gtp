import RiverLevelLine from "./RiverLevelLine"

export default class RiverLevelChartDTO {
  lines: Array<RiverLevelLine>
  alertLevel: number
  hasData: boolean

  constructor(lines: Array<RiverLevelLine>, alertLevel: number) {
    this.lines = lines
    this.alertLevel = alertLevel
    this.hasData = lines.some(({riverLevelYear}) => !riverLevelYear.isReference)
  }
}
