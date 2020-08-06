import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import MarketMap from "./MarketMap"

const hasDataFunc = ({agricultureConfig}) => !!agricultureConfig
const childPropsBuilder = (props) => props
const MarketLocationGraphicWithFallback = GraphicWithFallback('agriculture', 'isLoading', 'isLoaded',
  childPropsBuilder, hasDataFunc)

class MarketLocationGraphic extends Component {
  static propTypes = {
  }

  render() {
    const {agricultureConfig} = this.props
    return (
      <Graphic
        id="anchor.indicator.agriculture.market.map" titleId="indicators.map.market.title"
        helpId="indicators.map.market.help" helpProps={{wide: true}}
        className="map-graphic">
        <MarketLocationGraphicWithFallback agricultureConfig={agricultureConfig}>
          {childProps => <MarketMap />}
        </MarketLocationGraphicWithFallback>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    agricultureConfig: state.getIn(['agriculture', 'data', 'agricultureConfig']),
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(MarketLocationGraphic))
