import * as C from "../entities/Constants"

export default class MonthDecadal {
  year: number
  #isCurrentYear: boolean
  #months
  #now

  constructor(year: number, months: Array<number> = C.MONTHS) {
    this.year = year
    this.#months = months
    this._init()
  }

  _init() {
    this.#now = new Date()
    this.#isCurrentYear = !!this.year && this.year === this.#now.getFullYear()
    if (this.#isCurrentYear) {
      const maxMonth = this.#now.getMonth() + 1
      this.#months = this.#months.filter(m => m <= maxMonth)
    }
  }

  getMonths() {
    return this.#months
  }

  getDecadals(month) {
    if (this.#isCurrentYear && month === this.#now.getMonth() + 1) {
      const date = this.#now.getDate()
      if (date < 11) return [1]
      if (date < 21) return [1, 2]
    }
    return C.DECADALS
  }
}
