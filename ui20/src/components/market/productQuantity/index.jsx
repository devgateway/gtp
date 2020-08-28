import PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as quantityActions from "../../../redux/actions/market/quantityActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import ProductQuantity from "./ProductQuantity"
import ProductQuantityProperties from "./ProductQuantityProperties"

const hasDataFunc = ({data}) => data && data.hasData
const childPropsBuilder = (props) => props.getProductQuantities()
const ProductQuantityGraphicWithFallback = GraphicWithFallback('agriculture', 'isQuantityDataLoading', 'isQuantityDataLoaded',
  childPropsBuilder, hasDataFunc)


class ProductQuantityGraphic extends Component {
  static propTypes = {
    getProductQuantities: PropTypes.func.isRequired,
    isQuantityDataLoaded: PropTypes.bool.isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    const {apiData, filter, getProductQuantities} = this.props

    return (
      <Graphic
        id="anchor.indicator.agriculture.market.quantity" titleId="indicators.chart.product.quantity.title"
        helpId="indicators.chart.product.quantity.help" helpProps={{wide: true}}
        sourceId="indicators.chart.product.quantity.source">
        {apiData && <ProductQuantityProperties filter={filter} />}
        <ProductQuantityGraphicWithFallback filter={filter} getProductQuantities={getProductQuantities}>
          {childProps => <ProductQuantity {...childProps} />}
        </ProductQuantityGraphicWithFallback>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    apiData: state.getIn(['agriculture', 'data', 'productQuantityChart', 'data']),
    isQuantityDataLoaded: state.getIn(['agriculture', 'isQuantityDataLoaded']),
    filter: state.getIn(['agriculture', 'data', 'productQuantityChart', 'filter']),
  }
}

const mapActionCreators = {
  getProductQuantities: quantityActions.getProductQuantities
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantityGraphic))
