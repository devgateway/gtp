import * as PropTypes from "prop-types"
import React, {Component} from 'react'
import {FormattedMessage} from "react-intl"
import {FETCH_TIMEOUT_MILLISECONDS} from "../../modules/api/request"
import PageLoader from "./PageLoader"
import InitIntersectionObserver from "./polyfills/InitIntersectionObserver"
import InitResizeObserver from "./polyfills/InitResizeObserver"


export default function asyncComponent (importComponent) {
  class AsyncComponent extends Component {

    static propTypes = {
      isConnected: PropTypes.bool,
    }

    constructor (props) {
      super(props)

      this.state = {
        component: undefined,
      }
      this.componentStateUpdate = this.componentStateUpdate.bind(this)
    }

    async componentDidMount () {
      this.mounted = true

      await InitIntersectionObserver()
      InitResizeObserver()

      this.loadTimeout = setTimeout(() => this.componentStateUpdate(null), FETCH_TIMEOUT_MILLISECONDS)
      const { default: component } = await importComponent()
      clearTimeout(this.loadTimeout)

      const stateComponent = this.state.component
      // due to timeout, component may have been set to null, thus ignore any late completion
      this.componentStateUpdate(stateComponent === undefined ? component : stateComponent)
    }

    componentStateUpdate(component) {
      if (this.mounted) {
        this.setState({component})
      }
    }

    componentWillUnmount() {
      this.mounted = false
    }

    render () {
      const C = this.state.component
      const {isConnected} = this.props

      if (C === undefined && isConnected === undefined) {
        return <PageLoader />
      }
      if (C) {
        return <C {...this.props} />
      }

      return isConnected === false ? <ConnectivityError /> : null
    }
  }

  return AsyncComponent
}

export const ConnectivityError = () => (
  <div className="connectivity-error">
    <h3><FormattedMessage id="all.data-error" /></h3>
  </div>)

