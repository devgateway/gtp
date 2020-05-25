import {FEB_29_MONTH_DAY} from "../Constants"
import HydrologicalYear from "../HydrologicalYear"

export default class RiverYearNormalizedData {
  normalizedHY: HydrologicalYear
  leapYearMonthDays: Array<string>
  nonLeapYearMonthDays: Array<string>
  isUseLeapYear: boolean

  constructor() {
    // using 2019 as leap hydrological year, while year 2019 itself is not directly displayed
    this.normalizedHY = new HydrologicalYear(2019)
    this.leapYearMonthDays = this.normalizedHY.getMonthDays()
    this.nonLeapYearMonthDays = this.leapYearMonthDays.filter(md => md !== FEB_29_MONTH_DAY)
  }

  get monthDays() {
    return this.isUseLeapYear ? this.leapYearMonthDays : this.nonLeapYearMonthDays
  }
}
