import Region from "../../../entities/Region"
import Zone from "../../../entities/Zone"

export default class RegionDTO {
  id: number
  name: string
  code: string
  zoneId: number
  zone: Zone

  constructor(region: Region, zone: Zone) {
    Object.assign(this, region)
    this.zone = Zone
  }
}
