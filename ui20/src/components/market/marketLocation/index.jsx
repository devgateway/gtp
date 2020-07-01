import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import Graphic from "../../common/graphic/Graphic"
import MarketMap from "./MarketMap"


class MarketLocationGraphic extends Component {
  static propTypes = {
  }

  render() {
    return (
      <Graphic
        id="anchor.indicator.agriculture.market.map" titleId="indicators.map.market.title"
        className="map-graphic">
        <MarketMap />
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(MarketLocationGraphic))
