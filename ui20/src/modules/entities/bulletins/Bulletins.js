import {getOrDefaultMap} from "../../utils/DataUtilis"
import {Bulletin} from "./Bulletin"

export default class Bulletins {
  year: number
  bulletinsByDecadalByMonth: Map<number, Map<number, Bulletin>>
  annualReport: Bulletin

  constructor(year: number, annualReport: Bulletin) {
    this.year = year
    if (annualReport && this.isApplicable(annualReport)) {
      this.annualReport = annualReport
    }
    this.bulletinsByDecadalByMonth = new Map()
  }

  addBulletin(b: Bulletin) {
    if (this.isApplicable(b)) {
      getOrDefaultMap(this.bulletinsByDecadalByMonth, b.decadal).set(b.month, b)
    }
  }

  isApplicable(b: Bulletins) {
    if (b.year !== this.year) {
      console.error(`Cannot add bulletin from ${b.year} year to ${this.year} year group`)
      return false
    }
    return true
  }
}
