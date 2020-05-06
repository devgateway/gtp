export default class RainLevelConfig {
  years: Array<number>
  pluviometricPostIds: Array<number>

  constructor(props) {
    Object.assign(this, props)
  }
}
