import React from "react"
import {FormattedMessage} from "react-intl"
import {Menu, MenuItem} from "semantic-ui-react"
import {cssClasses, scrollToRef} from "../ComponentUtil"
import {ScrollRef} from "./ScrollableTo"

export class MenuItemDef {
  messageId: string
  icon: string
  scrollRef: ScrollRef

  constructor(messageId: string, icon: string, scrollRef: ScrollRef) {
    this.messageId = messageId
    this.icon = icon
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
            {itemDef.icon && MenuIcon(itemDef.icon)}
            <div className="link">
              <FormattedMessage id={itemDef.messageId}/>
            </div>
          </MenuItem>)
      })}
    </Menu>
  )
}

const MenuIcon = (icon: string) => {
  if (icon.endsWith(".svg")) {
    return <object type="image/svg+xml" data={icon}>icon</object>
  }
  if (icon.includes(".")) {
    return <img src={icon} alt="" />
  }
  return <span className={cssClasses("icon", icon)} />
}

export default MenuScrollableTo
