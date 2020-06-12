export class Bulletin {
  id: number
  year: number
  month: number
  decadal: number

  constructor({ id, year, month, decadal }) {
    this.id = id
    this.year = year
    this.month = month
    this.decadal = decadal
  }
}
