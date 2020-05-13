export default class RainSeasonConfigDTO {
  years: Array<object>

  constructor({ years }) {
    this._setYears(years)
  }

  _setYears(years) {
    this.years = years.map(y => ({key: y, value: y, text: y}))
  }
}
