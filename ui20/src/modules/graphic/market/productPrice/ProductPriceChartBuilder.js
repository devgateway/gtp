import AgricultureConfig from "../../../entities/config/AgricultureConfig"
import ProductPrice from "../../../entities/product/price/ProductPrice"
import ProductPriceChart from "../../../entities/product/price/ProductPriceChart"
import DateLinePoint from "../../common/dto/DateLinePoint"
import ProductPriceChartDTO from "./ProductPriceChartDTO"
import ProductPriceLine from "./ProductPriceLine"

export default class ProductPriceChartBuilder {
  productPriceChart: ProductPriceChart
  agricultureConfig: AgricultureConfig

  constructor(productPriceChart: ProductPriceChart, agricultureConfig: AgricultureConfig) {
    this.productPriceChart = productPriceChart
    this.agricultureConfig = agricultureConfig
  }

  build(): ProductPriceChartDTO {
    const {priceTypes, productsById} = this.agricultureConfig
    const { pricesByPriceTypeId, previousYearAverages } = this.productPriceChart.data
    const {productId} = this.productPriceChart.filter
    const product = productId && productsById.get(productId)
    const lines: Array<ProductPriceLine> = []

    pricesByPriceTypeId.forEach((prices: Array<ProductPrice>, priceTypeId: number) => {
      const points = prices.map(pp => new DateLinePoint(pp.monthDay.date, pp.price))
      lines.push(new ProductPriceLine(priceTypes.get(priceTypeId), points))
    })
    lines.forEach((l, index) => l.index = index)

    return new ProductPriceChartDTO(product, lines, previousYearAverages)
  }

}
