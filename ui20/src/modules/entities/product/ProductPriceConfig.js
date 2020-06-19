export default class ProductPriceConfig {
  years: Array<number>

  constructor({ years }) {
    this.years = years || []
  }
}
