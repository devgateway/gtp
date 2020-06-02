import PropTypes from 'prop-types'
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Link} from "react-router-dom"
import { Popup } from 'semantic-ui-react'

class MenuNavButton extends Component {
  static propTypes = {
    lan: PropTypes.string.isRequired,
    url: PropTypes.string,
    messageId: PropTypes.string.isRequired,
    icon: PropTypes.string.isRequired,
    isOpened: PropTypes.bool.isRequired,
  }

  getMenuEntry() {
    const {messageId, icon} = this.props
    return (
      <div className="nav link menu">
        <img src={icon} alt={''}/>
        <span className="title"><FormattedMessage id={messageId}/></span>
      </div>)
  }

  render() {
    const {lan, url, isOpened} = this.props
    const menuEntry = this.getMenuEntry()
    const menuItem = (
      <div className="nav menu">
        {url && <Link to={`/${lan}/${url}`}>{menuEntry}</Link>}
        {!url && <a href="http://">{menuEntry}</a>}
      </div>)

    if (isOpened) {
      return menuItem
    }

    return (
      <Popup
        position="right center"
        trigger={menuItem}>
        <Popup.Content>
          <FormattedMessage id={this.props.messageId}/>
        </Popup.Content>
      </Popup>)
  }
}

export const MenuNavButtonOpen = (props) => <MenuNavButton isOpened={true} {...props} />
export const MenuNavButtonClosed = (props) => <MenuNavButton isOpened={false} {...props} />
