import React, {Component} from "react"
import * as PropTypes from "prop-types"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as marketLocationActions from "../../../redux/actions/market/marketLocation"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import MarketMap from "./MarketMap"

const hasDataFunc = ({marketLocationsDTO} = {}) => !!marketLocationsDTO
const childPropsBuilder = (props) => props.getMarketLocations()
const MarketLocationGraphicWithFallback = GraphicWithFallback('agriculture', 'isLoading', 'isLoaded',
  childPropsBuilder, hasDataFunc)

class MarketLocationGraphic extends Component {
  static propTypes = {
    getMarketLocations: PropTypes.func.isRequired,
  }

  render() {
    const {agricultureConfig, getMarketLocations} = this.props
    return (
      <Graphic
        id="anchor.indicator.agriculture.market.map" titleId="indicators.map.market.title"
        helpId="indicators.map.market.help" helpProps={{wide: true}}
        className="map-graphic">
        <MarketLocationGraphicWithFallback agricultureConfig={agricultureConfig} getMarketLocations={getMarketLocations} >
          {childProps => <MarketMap {...childProps} />}
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
  getMarketLocations: marketLocationActions.getMarketLocations
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(MarketLocationGraphic))
