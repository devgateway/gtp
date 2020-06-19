import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import ProductAvgPrice from "../../../modules/entities/product/ProductAvgPrice"
import ProductPriceChartDTO from "../../../modules/graphic/market/productPrice/ProductPriceChartDTO"
import CustomLegendItem from "../../common/legend/CustomLegend"
import {LEGEND_SYMBOL_CIRCLE, LEGEND_SYMBOL_LINE} from "../../common/legend/CustomLegendSymbol"

export default class ProductPriceLegend extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(ProductPriceChartDTO).isRequired,
    filter: PropTypes.object.isRequired,
    onAveragePriceToggle: PropTypes.func.isRequired,
    lineColors: PropTypes.arrayOf(PropTypes.string).isRequired,
    avgColors: PropTypes.arrayOf(PropTypes.string).isRequired,
  }

  render() {
    const {lineColors, avgColors, filter, onAveragePriceToggle} = this.props
    const data: ProductPriceChartDTO = this.props.data
    const legendData = data.lines.map((l, index) => ({
      key: index,
      label: l.id,
      color: lineColors[index],
      symbol: LEGEND_SYMBOL_CIRCLE,
    })).concat(data.previousYearAverages.map((avg, index) => ({
      key: avg.priceType.name,
      label: getAveragePriceLabel(avg, filter.year - 1),
      color: avgColors[index],
      symbol: LEGEND_SYMBOL_LINE,
      onClick: onAveragePriceToggle
    })))

    return (
      <div className="legend">

        {legendData.map(({key, label, color, symbol, onClick}) =>
          (<CustomLegendItem key={key} type={symbol} label={label} onClick={onClick} legendProps={{
            x: 0,
            y: 0,
            my: 5,
            size: 12,
            fill: color,
            lineLength: 10,
            stroke: color,
          }}/>))}

      </div>)
  }
}

export const getAveragePriceLabel = (productAvgPrice: ProductAvgPrice, year: number) =>
  <FormattedMessage id={`indicators.chart.product.price.average.${productAvgPrice.priceType.name}`} values={{year}} />
