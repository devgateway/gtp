import RainLevelConfig from "./RainLevelConfig"
import RainLevelFilter from "./RainLevelFilter"
import RainLevelData from "./RainLevelData"
import RainLevelSetting from "./RainLevelSetting"

export default class RainLevelChart {
  config: RainLevelConfig
  filter: RainLevelFilter
  data: RainLevelData
  setting: RainLevelSetting

  constructor({ config, filter, data }) {
    this.config = new RainLevelConfig(config)
    this.filter = new RainLevelFilter(filter)
    this.data = new RainLevelData(data)
    this.setting = new RainLevelSetting()
  }
}
