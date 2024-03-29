import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import ProductAvgPrice from "../../../modules/entities/product/price/ProductAvgPrice"
import ProductPriceChartDTO from "../../../modules/graphic/market/productPrice/ProductPriceChartDTO"
import CustomLegend from "../../common/legend/CustomLegend"
import CustomLegendItem from "../../common/legend/CustomLegendItem"
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
      label: `${l.id} ${filter.year}`,
      color: lineColors[index],
      symbol: LEGEND_SYMBOL_CIRCLE,
    })).concat(data.previousYearAverages.map((avg, index) => ({
      key: avg.priceType.name,
      label: getAveragePriceLabel(avg, filter.year - 1),
      color: avgColors[index],
      symbol: LEGEND_SYMBOL_LINE,
      onClick: () => onAveragePriceToggle(avg.priceTypeId)
    })))

    return (
      <CustomLegend>

        {legendData.map(({key, label, color, symbol, onClick}) =>
          (<CustomLegendItem
            key={key}
            type={symbol}
            label={label}
            onClick={onClick}
            x={0}
            y={0}
            my={5}
            size={12}
            fill={color}
            lineLength={10}/>))}

      </CustomLegend>)
  }
}

export const getAveragePriceLabel = (productAvgPrice: ProductAvgPrice, year: number) =>
  <FormattedMessage id={`indicators.chart.product.price.average.${productAvgPrice.priceType.name}`} values={{year}} />
