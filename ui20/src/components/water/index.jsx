import React, {Component} from "react";
import {injectIntl} from "react-intl";
import {connect} from "react-redux";
import {loadAllWaterData} from "../../redux/actions/waterActions";
import * as PropTypes from "prop-types";

class WaterResources extends Component {

  static propTypes = {
    onLoadAll: PropTypes.func.isRequired
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    return (<div className="png exportable">
      <div style={ {color: 'black', 'text-align': 'center'}}>TODO</div>
    </div>)
  }

}

const mapStateToProps = state => {
  return {

  }
}

const mapActionCreators = {
  onLoadAll: loadAllWaterData
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(WaterResources))
