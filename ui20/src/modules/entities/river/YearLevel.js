import HydrologicalYear from "../HydrologicalYear"
import RiverLevel from "./RiverLevel"

export default class YearLevel {
  year: HydrologicalYear
  levels: Array<RiverLevel>

  constructor({ year, levels }) {
    this.year = new HydrologicalYear(year)
    this.levels = levels.map(({ monthDay, level}) => new RiverLevel(monthDay, level, this.year))
  }

}
