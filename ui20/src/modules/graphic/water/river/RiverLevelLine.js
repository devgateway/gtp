import RiverLevelYear from "../../../entities/river/RiverLevelYear"
import DateLine from "../../common/dto/DateLine"
import {hydrologicalYearToString} from "../CommonDTO"
import DateLinePoint from "../../common/dto/DateLinePoint"

export default class RiverLevelLine extends DateLine {
  riverLevelYear: RiverLevelYear

  constructor(year: number, data: Array<DateLinePoint>, isReference: boolean) {
    super(hydrologicalYearToString(year), data)
    this.riverLevelYear = new RiverLevelYear(year, isReference)
  }

}
