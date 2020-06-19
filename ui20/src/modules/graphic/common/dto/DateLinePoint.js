export default class DateLinePoint {
  x: string
  y: number
  actualDate: Date

  constructor(x: Date, y: number, actualDate: Date) {
    this.x = x.toISOString().split('T')[0]
    this.y = y
    this.actualDate = actualDate || x
  }
}
