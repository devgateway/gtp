export default class RainMapConfig {
  years: Array<number>
  layerTypes: Array<string>

  constructor({years, layerTypes}) {
    this.years = years || []
    this.layerTypes = layerTypes
  }
}
