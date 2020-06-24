import MonthDay from "../../MonthDay"

export default class Quantity {
  monthDay: MonthDay
  quantity: number

  constructor(monthDay: MonthDay, quantity: number) {
    this.monthDay = monthDay
    this.quantity = quantity
  }

}
