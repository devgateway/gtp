import PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as quantityActions from "../../../redux/actions/market/quantityActions"
import Graphic from "../../common/graphic/Graphic"
import ProductQuantity from "./ProductQuantity"
import ProductQuantityProperties from "./ProductQuantityProperties"


class ProductQuantityGraphic extends Component {
  static propTypes = {
    getProductQuantities: PropTypes.func.isRequired,
    isQuantityDataLoaded: PropTypes.bool.isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    const {filter, getProductQuantities} = this.props

    return (
      <Graphic
        id="anchor.indicator.agriculture.market.quantity" titleId="indicators.chart.product.quantity.title"
        helpId="indicators.chart.product.quantity.help" helpProps={{wide: true}}
        sourceId="indicators.chart.product.quantity.source">
        <ProductQuantityProperties filter={filter} />
        <ProductQuantity {...getProductQuantities()} filter={filter}/>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    isQuantityDataLoaded: state.getIn(['agriculture', 'isQuantityDataLoaded']),
    filter: state.getIn(['agriculture', 'data', 'productQuantityChart', 'filter']),
  }
}

const mapActionCreators = {
  getProductQuantities: quantityActions.getProductQuantities
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantityGraphic))
