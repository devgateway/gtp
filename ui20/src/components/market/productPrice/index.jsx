import React, {Component} from "react"
import PropTypes from "prop-types"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as priceActions from "../../../redux/actions/market/priceActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import ProductPrice from "./ProductPrice"
import ProductPriceProperties from "./ProductPriceProperties"

const hasDataFunc = ({data}) => data && data.hasData
const childPropsBuilder = (props) => props.getProductPrices()
const ProductPriceGraphicWithFallback = GraphicWithFallback('agriculture', 'isPriceDataLoading', 'isPriceDataLoaded',
  childPropsBuilder, hasDataFunc)


class ProductPriceGraphic extends Component {
  static propTypes = {
    getProductPrices: PropTypes.func.isRequired,
    isPriceDataLoaded: PropTypes.bool.isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    const {filter, getProductPrices, apiData} = this.props

    return (
      <Graphic
        id="anchor.indicator.agriculture.market.price" titleId="indicators.chart.product.price.title"
        helpId="indicators.chart.product.price.help" helpProps={{wide: "very"}}
        sourceId="indicators.chart.product.price.source">
        {apiData && <ProductPriceProperties filter={filter}/>}
        <ProductPriceGraphicWithFallback getProductPrices={getProductPrices} filter={filter}>
          {childProps => <ProductPrice {...childProps} />}
        </ProductPriceGraphicWithFallback>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    apiData: state.getIn(['agriculture', 'data', 'productPriceChart', 'data']),
    isPriceDataLoaded: state.getIn(['agriculture', 'isPriceDataLoading']),
    filter: state.getIn(['agriculture', 'data', 'productPriceChart', 'filter'])
  }
}

const mapActionCreators = {
  getProductPrices: priceActions.getProductPrices
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductPriceGraphic))
