import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Popup} from "semantic-ui-react"
import {cssClasses} from "../ComponentUtil"

export default class HelpIcon extends Component {
  static propTypes = {
    messageId: PropTypes.string.isRequired,
    className: PropTypes.string,
    position:  PropTypes.oneOf(
      ["top left", "top right", "bottom right", "bottom left", "right center", "left center", "top center", "bottom center"]),
    hidePointingArrow: PropTypes.bool,
    wide: PropTypes.oneOf([false, true, "very"]),
  }

  static defaultProps = {
    wide: false,
  }

  render() {
    const {messageId, className, position, hidePointingArrow, wide} = this.props

    return (
      <Popup
        className="help-icon-popup"
        position={position}
        basic={hidePointingArrow}
        wide={wide}
        content={<FormattedMessage id={messageId} values={{
          b: (...chunks) => <span className="strong">{chunks}</span>,
          p: (...chunks) => <span className="paragraph">{chunks}</span>,
          linebreak: <br />,
        }}/>}
        trigger={<div className={cssClasses("icon masked-icon icon-question", className)} />}
        hoverable
      />)
  }
}
