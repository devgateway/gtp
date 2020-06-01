import PropTypes from 'prop-types'
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Link} from "react-router-dom"

export default class MenuNavButton extends Component {
  static propTypes = {
    lan: PropTypes.string.isRequired,
    url: PropTypes.string.isRequired,
    messageId: PropTypes.string.isRequired,
    icon: PropTypes.string.isRequired,
  }

  render() {
    const {lan, url, messageId, icon} = this.props
    return (
      <div className="nav menu">
        <div className="nav link menu">
          <img src={icon} />
          <Link to={`/${lan}/${url}`}>
            <FormattedMessage id={messageId}/>
          </Link>
        </div>
      </div>
    )
  }
}
