import DateLinePoint from "./DateLinePoint"

export default class DateLine {
  id: string
  data: Array<DateLinePoint>
  index: number

  constructor(id: string, points: Array<DateLinePoint>) {
    this.id = id
    this.data = points
  }

}
