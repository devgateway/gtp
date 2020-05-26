import PluviometricPost from "../PluviometricPost"
import Zone from "../Zone"
import CommonConfig, {fromApiToMap} from "./CommonConfig"

export default class WaterConfig extends CommonConfig {
  zones: Map<number, Zone>

  constructor({posts}, commonConfig) {
    super(commonConfig);
    this.posts = fromApiToMap(posts, PluviometricPost.newInstance, 'department', this.departments)
  }
}
