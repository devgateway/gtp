import * as PropTypes from "prop-types"
import React, {Component} from 'react';
import {connect} from "react-redux"
import * as appActions from "../../redux/actions/appActions"

class ConnectionCheckWrapper extends Component {
  static propTypes = {
    connectionCheck: PropTypes.func.isRequired,
  }

  constructor(props) {
    super(props)
    this.state = {
      isConnected: undefined,
    }
  }

  componentDidMount() {
    if (navigator.onLine) {
      this.props.connectionCheck()
        .then(() => this.setState({isConnected: true}))
        .catch(() => this.setState({isConnected: false}))
    } else {
      this.setState({isConnected: false})
    }
  }

  render() {
    const {isConnected} = this.state
    return (
      <div>
        {isConnected === false && <ConnectivityError />}
        {this.props.children}
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  connectionCheck: appActions.connectionCheck
}

export default connect(mapStateToProps, mapActionCreators)(ConnectionCheckWrapper)
