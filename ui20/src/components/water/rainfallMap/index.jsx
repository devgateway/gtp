import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import Graphic from "../../common/graphic/Graphic"
import {RainfallMap} from "./RainfallMap"

class RainfallMapGraphic extends Component {
  static propTypes = {
  }

  render() {
    return (
      <Graphic
        id="anchor.indicator.water.rainfall.map" titleId="indicators.chart.rainfall.title"
        helpId="indicators.map.market.help" helpProps={{wide: true}}
        className="map-graphic">
        <RainfallMap />
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallMapGraphic))
