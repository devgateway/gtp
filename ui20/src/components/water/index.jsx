import React, {Component} from "react";
import {injectIntl} from "react-intl";
import {connect} from "react-redux";

class WaterResources extends Component {

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

}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(WaterResources))
