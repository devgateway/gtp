import AgricultureConfig from "../../../entities/config/AgricultureConfig"
import ProductPrice from "../../../entities/product/ProductPrice"
import ProductPriceChart from "../../../entities/product/ProductPriceChart"
import DateLine from "../../common/dto/DateLine"
import DateLinePoint from "../../common/dto/DateLinePoint"
import ProductPriceChartDTO from "./ProductPriceChartDTO"

export default class ProductPriceChartBuilder {
  productPriceChart: ProductPriceChart
  agricultureConfig: AgricultureConfig

  constructor(productPriceChart: ProductPriceChart, agricultureConfig: AgricultureConfig) {
    this.productPriceChart = productPriceChart
    this.agricultureConfig = agricultureConfig
  }

  build(): ProductPriceChartDTO {
    const {priceTypes, productsById} = this.agricultureConfig
    const { pricesByPriceTypeId } = this.productPriceChart.data
    const {productId} = this.productPriceChart.filter
    const product = productId && productsById.get(productId)
    const lines: Array<DateLine> = []

    pricesByPriceTypeId.forEach((prices: Array<ProductPrice>, priceTypeId: number) => {
      const points = prices.map(pp => new DateLinePoint(pp.monthDay.date, pp.price))
      lines.push(new DateLine(priceTypes.get(priceTypeId).label, points))
    })
    lines.forEach((l, index) => l.index = index)
    return new ProductPriceChartDTO(product, lines)
  }

}
