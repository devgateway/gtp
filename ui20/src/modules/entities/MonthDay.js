/* --mm-dd format, e.g. --05-12 for 12th of May */
export const MONTH_DAY_FORMAT = '--mm-dd'

export default class MonthDay {
  monthDay: string
  date: Date

  constructor(monthDay: string, year: number, yearStart: MonthDay) {
    this.monthDay = monthDay

    const [,,monthStr, dayStr] = monthDay.split('-')
    const month = +monthStr
    const day = +dayStr
    if (yearStart) {
      const startMonth = yearStart.date.getMonth()
      if (startMonth > month || (startMonth === month && day < yearStart.date.getDate())) {
        year += 1
      }
    }
    this.date = new Date(Date.UTC(year, month - 1, day))
  }

  toLocaleString() {
    return this.date.toLocaleDateString('fr-SN', {month: 'long', day: 'numeric'})
  }

}
