import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as marketActions from "../../redux/actions/market/index"
import "../common/indicators.scss"
import "../common/indicator-base.scss"
import GraphicPage, {GraphicDef} from "../common/GraphicPage"
import MarketLocationGraphic from "./marketLocation"
import ProductPriceGraphic from "./productPrice"
import ProductQuantityGraphic from "./productQuantity"

const agricultureGraphicsDef = [
  new GraphicDef('indicators.chart.product.price.title', 'masked-icon icon-linechart', ProductPriceGraphic),
  new GraphicDef('indicators.chart.product.quantity.title', 'masked-icon icon-linechart', ProductQuantityGraphic),
  /* keep MarketLocationGraphic always at the end*/
  new GraphicDef('indicators.map.market.title', 'masked-icon icon-barchart', MarketLocationGraphic),
]


class AgricultureAndMarket extends Component {

  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    isLoaded: PropTypes.bool.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    const {isLoaded} = this.props;
    if (!isLoaded) {
      return <div />
    }
    return <GraphicPage graphicsDefs={agricultureGraphicsDef} />
  }

}

const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['agriculture', 'isLoaded'])
  }
}

const mapActionCreators = {
  onLoadAll: marketActions.loadAllMarketData,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(AgricultureAndMarket))
