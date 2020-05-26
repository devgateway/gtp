import HydrologicalYear from "../HydrologicalYear"
import MonthDay from "../MonthDay"

export default class RiverLevel {
  monthDay: MonthDay
  normalizedDate: Date
  level: number

  constructor(monthDay: string, level: number, hydrologicalYear: HydrologicalYear, normalizedHY: HydrologicalYear) {
    this.monthDay = new MonthDay(monthDay, hydrologicalYear.year, hydrologicalYear.yearStart)
    const normalizedYear = normalizedHY.year + (this.monthDay.date.getFullYear() - hydrologicalYear.year)
    this.normalizedDate = new Date(this.monthDay.date)
    this.normalizedDate.setFullYear(normalizedYear)
    this.level = level
  }

}
