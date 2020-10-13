import React from "react"
import {Dropdown, Menu} from 'semantic-ui-react'

const MenuItem = ({label, url}) =>
  (<Menu.Item href={url} className="cnsc-item">
    {label}
  </Menu.Item>)

const MenuDropdownItem = ({label, url}) =>
  (<Dropdown.Item as={"a"} href={url} className="cnsc-item">
    {label}
  </Dropdown.Item>)

const MenuDropdown = ({menuGroup, toRight}) =>
  (<Dropdown text={menuGroup.label} simple basic className={toRight ? "to-right" : null}>
    <Dropdown.Menu>
      {menuGroup.items.map(entry => entry.items ?
        <MenuDropdown key={entry.label} menuGroup={entry} toRight={true} />
        : <MenuDropdownItem key={entry.label} label={entry.label} url={entry.url} />
      )}
    </Dropdown.Menu>
  </Dropdown>)

export const CNSCMenu = ({menuTree}) => {
  const level1 = (menuTree && menuTree.items) || []
  return (
    <Menu compact icon='labeled' className="cnsc-menu" stackable>
      {level1.map(entry => entry.items ?
        <MenuDropdown key={entry.label} menuGroup={entry}/>
        : <MenuItem key={entry.label} label={entry.label} url={entry.url}/>)}
    </Menu>)
}
