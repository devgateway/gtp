import RainSeasonConfigDTO from "./RainSeasonConfigDTO"
import {RainSeasonPredictionDTO} from "./RainSeasonPredictionDTO"

export default class RainSeasonTableDTO {
  data: Array<RainSeasonPredictionDTO>
  config: RainSeasonConfigDTO

  constructor(data: Array<RainSeasonPredictionDTO>, config: RainSeasonConfigDTO) {
    this.data = data
    this.config = config
  }

}
