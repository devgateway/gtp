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

  get isLeapYear() {
    const y = this.year + (this.yearStart.date.getMonth() > 1 ? 1 : 0)
    return y % 4 === 0
  }

  get days() {
    return 365 + (this.isLeapYear ? 1 : 0)
  }

  getMonthDays() {
    let days = this.days
    const mds = []
    for (let date = new Date(this.yearStart.date); days > 0; days--) {
      mds.push(MonthDay.getMonthDayStr(date))
      date.setDate(date.getDate() + 1)
    }
    return mds
  }

}
