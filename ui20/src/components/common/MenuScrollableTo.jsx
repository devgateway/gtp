import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Menu, MenuItem} from "semantic-ui-react"
import {scrollToRef} from "../ComponentUtil"
import {ScrollRef} from "./ScrollableTo"

export class MenuItemDef {
  messageId: string
  scrollRef: ScrollRef

  constructor(messageId: string, scrollRef: ScrollRef) {
    this.messageId = messageId
    this.scrollRef = scrollRef
  }
}

export default class MenuScrollableTo extends Component {
  static propTypes = {
    defs: PropTypes.arrayOf(PropTypes.instanceOf(MenuItemDef)).isRequired,
  }

  render() {
    const {defs} = this.props
    return (
      <Menu fluid widths={defs.length} stackable>
        {defs.map((itemDef:MenuItemDef) => {
          const name = `menu-${itemDef.messageId}`
          return (
            <MenuItem
              key={name}
              name={name}
              onClick={() => scrollToRef(itemDef.scrollRef.ref)}
            >
              <FormattedMessage id={itemDef.messageId} />
            </MenuItem>)
        })}
      </Menu>
    )
  }
}
