import * as PropTypes from "prop-types"
import React, {Component} from "react"
import ProductQuantityChartDTO from "../../../modules/graphic/market/productQuantity/ProductQuantityChartDTO"
import ProductQuantityLine from "../../../modules/graphic/market/productQuantity/ProductQuantityLine"
import CustomLegend from "../../common/legend/CustomLegend"
import CustomLegendItem from "../../common/legend/CustomLegendItem"
import {LEGEND_SYMBOL_CIRCLE} from "../../common/legend/CustomLegendSymbol"

export default class ProductQuantityLegend extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(ProductQuantityChartDTO).isRequired,
    colors: PropTypes.arrayOf(PropTypes.string).isRequired,
  }

  render() {
    const items = this.props.data.lines.map((l: ProductQuantityLine, index) => ({
      key: l.product.id,
      label: l.product.name,
      color: this.props.colors[index],
    }))

    return (
      <CustomLegend>
        {items.map(({key, label, color}) =>
          <CustomLegendItem
            key={key}
            type={LEGEND_SYMBOL_CIRCLE}
            label={label}
            x={0}
            y={0}
            my={5}
            size={12}
            fill={color}/>)

        }
      </CustomLegend>)
  }
}
