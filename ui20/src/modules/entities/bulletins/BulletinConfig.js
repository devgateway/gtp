import GTPLocation from "./GTPLocation"

export default class BulletinConfig {
  years: Array<number>
  locations: Array<GTPLocation>

  constructor(years: Array<number>, locations: Array<GTPLocation>) {
    this.years = years
    this.locations = locations
  }

}
