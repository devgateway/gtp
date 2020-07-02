import React, { Component } from 'react'
import InitResizeObserver from "./polyfills/InitResizeObserver"
import {initializeObserver} from "./TrackVisibility"

export default function asyncComponent (importComponent) {
  class AsyncComponent extends Component {
    constructor (props) {
      super(props)

      this.state = {
        component: null
      }
    }

    async componentDidMount () {
      await initializeObserver()
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
