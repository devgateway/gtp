import RainSeasonPrediction from "./RainSeasonPrediction"

export default class RainSeasonData {
  referenceYearStart: number
  referenceYearEnd: number
  predictions: Array<RainSeasonPrediction>

  constructor({referenceYearStart, referenceYearEnd, predictions}, year) {
    if (year) {
      this.referenceYearStart = referenceYearStart
      this.referenceYearEnd = referenceYearEnd
      this.predictions = predictions.map(p => new RainSeasonPrediction(p, year))
    }
  }

  clone() {
    return Object.assign(new RainSeasonData({}), this)
  }

}
