import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as marketActions from "../../redux/actions/market/index"
import "../common/indicators.scss"
import "../common/indicator-base.scss"
import MarketLocationGraphic from "./marketLocation"


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
      return <div></div>
    }
    return (
      <div className="indicators content fixed">
        {/* keep MarketLocationGraphic always at the end*/}
        <MarketLocationGraphic />
      </div>)
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
