import React, {Component} from "react"
import PropTypes from "prop-types"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"

class RiverLevel extends Component {
  static propTypes = {

  }

  render() {
    return (
      <div className="png exportable chart container">
        TODO
      </div>);
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevel))
