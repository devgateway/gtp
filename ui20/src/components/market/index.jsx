import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import {
  INDICATOR_AGRICULTURE_MARKET_MAP,
  INDICATOR_AGRICULTURE_MARKET_PRICE,
  INDICATOR_AGRICULTURE_MARKET_QUANTITY
} from "../../modules/entities/FMConstants"
import * as marketActions from "../../redux/actions/market/index"
import "../common/graphic/indicators.scss"
import "../common/graphic/indicator-base.scss"
import GraphicPage, {GraphicDef} from "../common/graphic/GraphicPage"
import MarketLocationGraphic from "./marketLocation"
import ProductPriceGraphic from "./productPrice"
import ProductQuantityGraphic from "./productQuantity"

const agricultureGraphicsDef = [
  new GraphicDef('indicators.chart.product.price.title', 'masked-icon icon-linechart', ProductPriceGraphic, INDICATOR_AGRICULTURE_MARKET_PRICE),
  new GraphicDef('indicators.chart.product.quantity.title', 'masked-icon icon-linechart', ProductQuantityGraphic, INDICATOR_AGRICULTURE_MARKET_QUANTITY),
  /* keep MarketLocationGraphic always at the end*/
  new GraphicDef('indicators.map.market.title', 'masked-icon icon-barchart', MarketLocationGraphic, INDICATOR_AGRICULTURE_MARKET_MAP),
]


class AgricultureAndMarket extends Component {

  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    return <GraphicPage graphicsDefs={agricultureGraphicsDef} />
  }

}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  onLoadAll: marketActions.loadAllMarketData,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(AgricultureAndMarket))
