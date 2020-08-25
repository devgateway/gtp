import React, { Component } from 'react'
import InitIntersectionObserver from "./polyfills/InitIntersectionObserver"
import InitResizeObserver from "./polyfills/InitResizeObserver"


export default function asyncComponent (importComponent) {
  class AsyncComponent extends Component {
    constructor (props) {
      super(props)

      this.state = {
        component: null
      }
    }

    async componentDidMount () {
      await InitIntersectionObserver()
      InitResizeObserver()
      const { default: component } = await importComponent()

      this.setState({
        component: component
      })
    }

    render () {
      const C = this.state.component

      return C ? <C {...this.props} /> : null
    }
  }

  return AsyncComponent
}

export const ConnectivityError = () => (
  <div className="connectivity-error">
    <h3><FormattedMessage id="all.data-error" /></h3>
  </div>)

