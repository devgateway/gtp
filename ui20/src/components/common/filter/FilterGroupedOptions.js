export default class FilterGroupedOptions {
  groups: Map<String, Set<number>>

  constructor(groups: Map<String, Set<number>>) {
    this.groups = groups
  }
}
