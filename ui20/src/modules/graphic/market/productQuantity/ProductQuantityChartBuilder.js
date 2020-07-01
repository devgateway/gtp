import AgricultureConfig from "../../../entities/config/AgricultureConfig"
import ProductQuantities from "../../../entities/product/quantity/ProductQuantities"
import ProductQuantityChart from "../../../entities/product/quantity/ProductQuantityChart"
import Quantity from "../../../entities/product/quantity/Quantity"
import DateLinePoint from "../../common/dto/DateLinePoint"
import ProductQuantityChartDTO from "./ProductQuantityChartDTO"
import ProductQuantityLine from "./ProductQuantityLine"


export default class ProductQuantityChartBuilder {
  productQuantityChart: ProductQuantityChart
  agricultureConfig: AgricultureConfig

  constructor(productQuantityChart: ProductQuantityChart, agricultureConfig: AgricultureConfig) {
    this.productQuantityChart = productQuantityChart
    this.agricultureConfig = agricultureConfig
  }

  build(): ProductQuantityChartDTO {
    const {productTypes} = this.agricultureConfig
    const {productTypeId} = this.productQuantityChart.filter
    const productType = productTypeId && productTypes.get(productTypeId)
    const lines = Array.from(this.productQuantityChart.data.quantitiesByProductId.values())
      .map((pqs: ProductQuantities) => {
        const points = pqs.quantities.map((q: Quantity) => new DateLinePoint(q.monthDay.date, q.quantity))
        return new ProductQuantityLine(pqs.product, points)
      })
    return new ProductQuantityChartDTO(productType, lines)
  }

}
