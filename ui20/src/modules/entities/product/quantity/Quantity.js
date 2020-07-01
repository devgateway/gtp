import MonthDay from "../../MonthDay"

export default class Quantity {
  monthDay: MonthDay
  quantity: number

  constructor(monthDay: MonthDay, quantity: number) {
    this.monthDay = monthDay
    this.quantity = quantity
  }

  static compareByDate(q1: Quantity, q2: Quantity) {
    return q1.monthDay.monthDay.localeCompare(q2.monthDay.monthDay)
  }

}
