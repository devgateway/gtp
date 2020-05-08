import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../redux/actions/waterActions"
import "../ipar/indicators/indicators.scss"
import RainfallGraphic from "./rainfall"

class WaterResources extends Component {

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
    return (<div className="indicators content fixed">
      <RainfallGraphic />
    </div>)
  }

}

const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['water', 'isLoaded'])
  }
}

const mapActionCreators = {
  onLoadAll: waterActions.loadAllWaterData,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(WaterResources))
