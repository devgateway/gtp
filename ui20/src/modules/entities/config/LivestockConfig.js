import Disease from "../diseaseSituation/Disease"

export default class LivestockConfig {
  diseases: Array<Disease>
  diseasesById: Map<number, Disease>

  constructor({diseases} = {}) {
    this.diseases = (diseases || []).map(d => new Disease(d)).sort(Disease.localeCompare)
    this.diseasesById = this.diseases.reduce((map: Map<number, Disease>, d) => map.set(d.id, d), new Map())
  }

}
