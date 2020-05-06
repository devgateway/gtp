import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../redux/actions/waterActions"
import Graphic from "../common/Graphic"
import "../ipar/indicators/indicators.scss"


class WaterResources extends Component {

  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    getRain: PropTypes.func.isRequired,
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
    return (<div className="indicators content fixed">
      <Graphic
        id="anchor.indicator.water.rainfall" titleId="indicators.chart.rainfall.title"
        sourceId="indicators.chart.rainfall.source">

      </Graphic>
    </div>)
  }

}

const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['water', 'isLoaded'])
  }
}

const mapActionCreators = {
  onLoadAll: waterActions.loadAllWaterData
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(WaterResources))
