import CommonConfig from "../../../entities/config/CommonConfig"
import WaterConfig from "../../../entities/config/WaterConfig"
import PluviometricPostDTO from "./PluviometricPostDTO"
import PluviometricPostMapDTO from "./PluviometricPostMapDTO"

export default class PluviometricPostMapBuilder {
  commonConfig: CommonConfig
  waterConfig: WaterConfig

  constructor(commonConfig: CommonConfig, waterConfig: WaterConfig) {
    this.commonConfig = commonConfig
    this.waterConfig = waterConfig
  }

  build(): PluviometricPostMapDTO {
    return new PluviometricPostMapDTO(
      Array.from(this.waterConfig.posts.values())
        .map(p => new PluviometricPostDTO(p, this.commonConfig)))
  }
}
