import MonthDay from "../MonthDay"

export default class RainSeasonPrediction {
  pluviometricPostId: number
  planned: MonthDay
  actual: MonthDay
  difference: number

  constructor({pluviometricPostId, planned, actual, difference}, year) {
    this.pluviometricPostId = pluviometricPostId
    this.planned = new MonthDay(planned, year)
    this.actual = new MonthDay(actual, year)
    this.difference = difference
  }
}
