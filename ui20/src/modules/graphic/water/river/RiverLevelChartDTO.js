import RiverLevelLine from "./RiverLevelLine"

export default class RiverLevelChartDTO {
  lines: Array<RiverLevelLine>

  constructor(lines: Array<RiverLevelLine>) {
    this.lines = lines
  }
}
