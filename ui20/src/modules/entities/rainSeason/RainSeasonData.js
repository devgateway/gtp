import RainSeasonPrediction from "./RainSeasonPrediction"

export default class RainSeasonData {
  referenceYearStart: number
  referenceYearEnd: number
  yearStart: number
  yearEnd: number
  predictions: Array<RainSeasonPrediction>

  constructor({referenceYearStart, referenceYearEnd, yearStart, yearEnd, predictions} = {}, year) {
    if (year) {
      this.referenceYearStart = referenceYearStart
      this.referenceYearEnd = referenceYearEnd
      this.yearStart = yearStart
      this.yearEnd = yearEnd
      this.predictions = (predictions || []).map(p => new RainSeasonPrediction(p, year))
    }
  }

  clone() {
    return Object.assign(new RainSeasonData({}), this)
  }

}
