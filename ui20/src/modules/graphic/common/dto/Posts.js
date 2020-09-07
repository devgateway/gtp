import CommonConfig from "../../../entities/config/CommonConfig"
import PluviometricPost from "../../../entities/PluviometricPost"
import DepartmentDTO from "./DepartmentDTO"
import PostDTO from "./PostDTO"
import RegionDTO from "./RegionDTO"

export default class Posts {
  commonConfig: CommonConfig
  posts: Array<PluviometricPost>
  depDTOById: Map<number, DepartmentDTO>
  regDTOById: Map<number, RegionDTO>

  constructor(posts: Array<PluviometricPost>, commonConfig: CommonConfig) {
    this.posts = posts
    this.commonConfig = commonConfig
    this.depDTOById = new Map()
    this.regDTOById = new Map()
  }

  buildPostDTOById(): Map<number, PostDTO> {
    this.commonConfig.regions.forEach((r, id) => {
      this.regDTOById.set(id, new RegionDTO(r, this.commonConfig.zones.get(r.zoneId)))
    })
    this.commonConfig.departments.forEach((d, id) => {
      this.depDTOById.set(id, new DepartmentDTO(d, this.regDTOById.get(d.regionId)))
    })
    return this.posts.map((p: PluviometricPost) => {
      const depDTO = this.depDTOById.get(p.departmentId)
      return new PostDTO(p, depDTO)
    }).reduce((map: Map, pDTO) => map.set(pDTO.id, pDTO), new Map())
  }

}
