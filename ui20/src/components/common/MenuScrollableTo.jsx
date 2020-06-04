import React from "react"
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

const MenuScrollableTo = (props) => {
  const {defs, active} = props

  return (
    <Menu fluid widths={defs.length} stackable attached="top" borderless>
      {defs.map((itemDef: MenuItemDef, index) => {
        const name = `menu-${itemDef.messageId}`
        return (
          <MenuItem
            key={name}
            name={name}
            active={active === index}
            onClick={() => scrollToRef(itemDef.scrollRef.ref)}
          >
            <div className="link">
              <FormattedMessage id={itemDef.messageId}/>
            </div>
          </MenuItem>)
      })}
    </Menu>
  )
}

export default MenuScrollableTo
