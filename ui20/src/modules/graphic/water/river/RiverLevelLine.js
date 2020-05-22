import RiverLevelPoint from "./RiverLevelPoint"

export default class RiverLevelLine {
  // hydrological year
  id: string
  data: Array<RiverLevelPoint>
  isReference: boolean

  constructor(id: string, data: Array<RiverLevelPoint>, isReference: boolean) {
    this.id = id
    this.data = data
    this.isReference = isReference
  }

}
