import RainSeasonPrediction from "../../../entities/rainSeason/RainSeasonPrediction"
import PostDTO from "../../common/dto/PostDTO"

export class RainSeasonPredictionDTO {
  id: number
  zone: string
  region: string
  department: string
  post: string
  planned: string
  actual: string
  difference: number

  constructor(post: PostDTO, rainSeasonPrediction: RainSeasonPrediction) {
    this.id = rainSeasonPrediction.pluviometricPostId
    this.post = (post && post.label) || `id=${this.id}`
    this.department = post && post.department.name
    this.region = post && post.department.region.name
    this.zone = post && post.department.region.zone.name
    this.planned = rainSeasonPrediction.planned.toLocaleString()
    this.actual = rainSeasonPrediction.actual.toLocaleString()
    this.difference = rainSeasonPrediction.difference
  }

}
