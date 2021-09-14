export default class FMConfig {
  features: Set<String>

  constructor(featuresList: Array = []) {
    this.features = new Set(featuresList);
  }

  has(fmEntry: string): boolean {
    return fmEntry && this.features.has(fmEntry)
  }

}
