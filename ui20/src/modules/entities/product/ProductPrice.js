import MonthDay from "../MonthDay"

export default class ProductPrice {
  monthDay: MonthDay
  price: number
  priceTypeId: number

  constructor({ monthDay, price, priceTypeId}, year: number) {
    this.monthDay = new MonthDay(monthDay, year)
    this.price = price
    this.priceTypeId = priceTypeId
  }

}
