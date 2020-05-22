import RiverLevelLine from "./RiverLevelLine"

export default class RiverLevelChartDTO {
  lines: Array<RiverLevelLine>
  alertLevel: number

  constructor(lines: Array<RiverLevelLine>, alertLevel: number) {
    this.lines = lines
    this.alertLevel = alertLevel
  }
}
