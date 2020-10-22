import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Link} from "react-router-dom"

export default class NotFound extends Component {

  render() {
    return (
      <div className="not-found">
        <div>
          <FormattedMessage id="all.page-not-found" />
        </div>
        <div>
          <Link to="/">
            <FormattedMessage id="all.go-to-homepage" />
          </Link>
        </div>
      </div>)
  }

}
