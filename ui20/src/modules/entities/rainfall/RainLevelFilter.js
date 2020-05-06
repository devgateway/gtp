export default class RainLevelFilter {
  years: Array<number>
  pluviometricPostId: number

  constructor({ years, pluviometricPostId }) {
    this.years = years
    this.pluviometricPostId = pluviometricPostId
  }
}
