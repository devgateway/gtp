export default class RiverLevelYear {
  year: number
  isReference: boolean

  constructor(year: number, isReference: boolean) {
    this.year = year
    this.isReference = isReference
  }

  equals(other: RiverLevelYear) {
    return this.year === other.year && this.isReference === other.isReference
  }

}
