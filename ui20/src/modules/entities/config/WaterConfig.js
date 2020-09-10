import PluviometricPost from "../PluviometricPost"
import {reduceToMap} from "./CommonConfig"

export default class WaterConfig {
  posts: Map<number, PluviometricPost>

  constructor({posts} = {}) {
    this.posts = reduceToMap(posts, PluviometricPost.newInstance)
  }
}
