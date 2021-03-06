import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"

export default class NoData extends Component {
  static propTypes = {
    messageId: PropTypes.string,
  }

  render() {
    const {messageId, children} = this.props

    if (children) {
      return (
        <div className="graphic-content">
          <div className="no-data">
            {children}
          </div>
        </div>)
    }

    return (
      <div className="graphic-content">
        <div className="no-data">
          <div className="icon-no-data"/>
          {messageId && <div className="no-data-message"><FormattedMessage id={messageId}/></div>}
        </div>
      </div>
    )
  }
}
