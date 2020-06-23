import React, {Component} from "react"
import PropTypes from "prop-types"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as priceActions from "../../../redux/actions/market/priceActions"
import Graphic from "../../common/Graphic"
import ProductPrice from "./ProductPrice"
import ProductPriceProperties from "./ProductPriceProperties"


class ProductPriceGraphic extends Component {
  static propTypes = {
    getProductPrices: PropTypes.func.isRequired,
    isPriceDataLoaded: PropTypes.bool.isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    const {filter, getProductPrices} = this.props

    return (
      <Graphic
        id="anchor.indicator.agriculture.market.price" titleId="indicators.chart.product.price.title"
        sourceId="indicators.chart.product.price.source">
        <ProductPriceProperties filter={filter} />
        <ProductPrice {...getProductPrices()} filter={filter}/>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    isPriceDataLoaded: state.getIn(['agriculture', 'isPriceDataLoading']),
    filter: state.getIn(['agriculture', 'data', 'productPriceChart', 'filter'])
  }
}

const mapActionCreators = {
  getProductPrices: priceActions.getProductPrices
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductPriceGraphic))
