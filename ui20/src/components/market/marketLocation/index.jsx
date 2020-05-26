import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import Graphic from "../../common/Graphic"


class MarketLocationGraphic extends Component {
  static propTypes = {
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
  }

  render() {
    return (
      <Graphic
        id="anchor.indicator.agriculture.market.map" titleId="indicators.map.market.title"
        sourceId="indicators.map.market.source" className="rainfall">
        TODO
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
