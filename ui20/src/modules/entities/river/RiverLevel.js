import HydrologicalYear from "../HydrologicalYear"
import MonthDay from "../MonthDay"

export default class RiverLevel {
  monthDay: MonthDay
  level: number

  constructor(monthDay: string, level: number, hydrologicalYear: HydrologicalYear) {
    this.monthDay = new MonthDay(monthDay, hydrologicalYear.year, hydrologicalYear.yearStart)
    this.level = level
  }

}
