import RiverStation from "./RiverStation"

export default class RiverLevelConfig {
  years: Array<number>
  riverStations: Array<RiverStation>

  constructor({ years, riverStations }) {
    this.years = years
    this.riverStations = riverStations.map(rs => new RiverStation(rs))
  }

}
