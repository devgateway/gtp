import * as PropTypes from "prop-types"
import React, {Component} from 'react';
import {connect} from "react-redux"
import * as appActions from "../../redux/actions/appActions"

class ConnectionCheckWrapper extends Component {
  static propTypes = {
    connectionCheck: PropTypes.func.isRequired,
    childrenBuilder: PropTypes.func.isRequired,
  }

  constructor(props) {
    super(props)
    this.state = {
      isConnected: undefined,
    }
    this.updateConnectionStatus = this.updateConnectionStatus.bind(this)
  }

  componentDidMount() {
    this.mounted = true
    if (navigator.onLine) {
      this.props.connectionCheck()
        .then(() => this.updateConnectionStatus(true))
        .catch(() => this.updateConnectionStatus(false))
    } else {
      this.updateConnectionStatus(false)
    }
  }

  updateConnectionStatus(isConnected) {
    if (this.mounted) {
      this.setState({isConnected})
    }
  }

  componentWillUnmount() {
    this.mounted = false
  }

  render() {
    const {isConnected} = this.state
    return (
      <div>
        {this.props.childrenBuilder({isConnected})}
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
