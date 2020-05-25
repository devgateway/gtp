import RiverStation from "./RiverStation"

export default class RiverLevelConfig {
  years: Array<number>
  riverStations: Map<number, RiverStation>

  constructor({ years, riverStations }) {
    this.years = years
    this.riverStations = riverStations.reduce((map, rs) => {
      map.set(rs.id, new RiverStation(rs))
      return map
    }, new Map())
  }

}
