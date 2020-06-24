import PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as quantityActions from "../../../redux/actions/market/quantityActions"
import Graphic from "../../common/graphic/Graphic"
import ProductQuantity from "./ProductQuantity"


class ProductQuantityGraphic extends Component {
  static propTypes = {
    getProductQuantities: PropTypes.func.isRequired,
  }

  render() {
    const {getProductQuantities} = this.props

    return (
      <Graphic
        id="anchor.indicator.agriculture.market.quantity" titleId="indicators.chart.product.quantity.title"
        sourceId="indicators.chart.product.quantity.source">
        <ProductQuantity {...getProductQuantities()}/>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  getProductQuantities: quantityActions.getProductQuantities
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantityGraphic))
