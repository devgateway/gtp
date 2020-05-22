import RiverLevelYear from "../../../entities/river/RiverLevelYear"
import {hydrologicalYearToString} from "../CommonDTO"
import RiverLevelPoint from "./RiverLevelPoint"

export default class RiverLevelLine {
  // hydrological year
  id: string
  index: number
  riverLevelYear: RiverLevelYear
  data: Array<RiverLevelPoint>

  constructor(year: number, data: Array<RiverLevelPoint>, isReference: boolean) {
    this.id = hydrologicalYearToString(year)
    this.riverLevelYear = new RiverLevelYear(year, isReference)
    this.data = data
  }

}
