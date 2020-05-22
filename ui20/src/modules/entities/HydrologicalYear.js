import {HYDROLOGICAL_YEAR_START_MONTH_DAY} from "./Constants"
import MonthDay from "./MonthDay"

export default class HydrologicalYear {
  // 2020 is the year stored for hydrological year 2020-21
  year: number
  yearStart: MonthDay

  constructor(year: number) {
    this.year = year
    this.yearStart = new MonthDay(HYDROLOGICAL_YEAR_START_MONTH_DAY, year)
  }

}
