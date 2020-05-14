export default class DaysRain {
  withRain: number
  withoutRain: number

  constructor(withRain, withoutRain) {
    this.withRain = withRain
    this.withoutRain = withoutRain
  }

  getRain(isDaysWithRain: boolean) {
    return isDaysWithRain ? this.withRain : this.withoutRain
  }
}

export class MonthDaysRain {
  month: number
  daysRain: DaysRain

  constructor(month, daysWithRain, daysWithoutRain) {
    this.month = month
    this.daysRain = new DaysRain(daysWithRain, daysWithoutRain)
  }
}

export class DecadalDaysRain {
  month: number
  decadal: number
  daysRain: DaysRain

  constructor({month, decadal, daysWithRain, daysWithoutRain}) {
    this.month = month
    this.decadal = decadal
    this.daysRain = new DaysRain(daysWithRain, daysWithoutRain)
  }
}
