import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"

class DrySeason extends Component {

  render() {
    return (
      <div>
        TODO
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DrySeason))
