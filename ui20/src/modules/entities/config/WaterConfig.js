import PluviometricPost from "../PluviometricPost"
import Zone from "../Zone"
import CommonConfig, {fromApiToMap} from "./CommonConfig"

export default class WaterConfig {
  posts: Map<number, Zone>

  constructor({posts} = {}, commonConfig: CommonConfig) {
    this.posts = fromApiToMap(posts, PluviometricPost.newInstance, 'department', commonConfig && commonConfig.departments)
  }
}
