import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {connect} from "react-redux"
import {ConnectivityError} from "../AsyncComponent"
import PageLoader from "../PageLoader"

class PageLoad extends Component {
  static propTypes = {
    isConnected: PropTypes.bool,
    isLoading: PropTypes.bool.isRequired,
    isLoaded: PropTypes.bool,
    error: PropTypes.any,
  }

  render() {
    const {isLoading, isLoaded, error} = this.props

    let loadStatus
    if (isLoaded === undefined || isLoading) {
      loadStatus = <PageLoader />
    } else if (!isLoaded && error) {
      loadStatus = <ConnectivityError />
    }
    return (
      <div>
        {loadStatus}
        {this.props.children(this.props)}
      </div>)
  }
}

const PageLoadWrapper = (props) => {
  const {statePath} = props
  const mapStateToProps = state => {
    return {
      isLoading: state.getIn([statePath, 'isLoading']),
      isLoaded: state.getIn([statePath, 'isLoaded']),
      error: state.getIn([statePath, 'error']),
    }
  }

  const mapActionCreators = {
  }

  return connect(mapStateToProps, mapActionCreators)(PageLoad)
}

export default PageLoadWrapper
