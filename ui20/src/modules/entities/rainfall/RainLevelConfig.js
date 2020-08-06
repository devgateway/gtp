export default class RainLevelConfig {
  years: Array<number>
  pluviometricPostIds: Array<number>

  constructor({ years, pluviometricPostIds } = {}) {
    this.years = years || []
    this.pluviometricPostIds = pluviometricPostIds || []
  }
}
