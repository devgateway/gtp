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
    filter: PropTypes.object.isRequired,
  }

  render() {
    const {filter, getProductQuantities} = this.props

    return (
      <Graphic
        id="anchor.indicator.agriculture.market.quantity" titleId="indicators.chart.product.quantity.title"
        sourceId="indicators.chart.product.quantity.source">
        <ProductQuantity {...getProductQuantities()} filter={filter}/>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    filter: state.getIn(['agriculture', 'data', 'productQuantityChart', 'filter']),
  }
}

const mapActionCreators = {
  getProductQuantities: quantityActions.getProductQuantities
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantityGraphic))
