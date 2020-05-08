import RainLevelConfig from "./RainLevelConfig"
import RainLevelData from "./RainLevelData"

export default class RainLevelReport {
  config: RainLevelConfig
  data: RainLevelData

  constructor({ config, data }) {
    this.config = new RainLevelConfig(config)
    this.data = new RainLevelData(data)
  }
}
