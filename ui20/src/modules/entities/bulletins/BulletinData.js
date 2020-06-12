import {getOrDefault} from "../../utils/DataUtilis"
import {Bulletin} from "./Bulletin"
import Bulletins from "./Bulletins"

export default class BulletinData {
  gtpMaterials: Map<number, Bulletins>

  constructor({ bulletins, annualReports }) {
    this.gtpMaterials = new Map()
    annualReports.forEach(apiAnnualReport => {
      const b = new Bulletin(apiAnnualReport)
      const bs = new Bulletins(b.year, b)
      this.gtpMaterials.set(b.year, bs)
    })
    bulletins.forEach(apiBulletin => {
      const b = new Bulletin(apiBulletin)
      const bs: Bulletins = getOrDefault(this.gtpMaterials, b.year, null, () => new Bulletins(b.year))
      bs.addBulletin(b)
    })
  }
}
