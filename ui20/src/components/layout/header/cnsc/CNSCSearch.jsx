import React, {useState} from "react"
import { Input } from 'semantic-ui-react'
import {cssClasses, getBrowserClass} from "../../../ComponentUtil"

export const CNSCSearch = ({searchPrefix, onStateChange, intl}) => {
  const [isActive, setActive] = useState(false);
  const swapActive = () => {
    setActive(!isActive)
    onStateChange(!isActive)
  }

  return (
    <div className={cssClasses("cnsc-search", isActive ? "active" : null)}>
      {!isActive && <span className="cnsc-search-icon" onClick={swapActive}/>}
      {isActive && <CNSCSearchBox searchPrefix={searchPrefix} onClose={swapActive} intl={intl} />}
    </div>)
}

const CNSCSearchBox = ({searchPrefix, onClose, intl}) =>
  (<div className="cnsc-search-box" onBlur={onClose}>
    <Input
      id="search-input-id"
      autoFocus={getBrowserClass() !== 'IE'}
      icon={<CNSCSearchIcon onClick={onClose} />}
      onKeyDown={(e) => {
        if (e.key === 'Enter') {
          window.open(`${searchPrefix}${(e.target.value || "").trim()}`, "_top")
        }
      }}
      placeholder={intl.formatMessage({id: "all.search"})} />
  </div>)

const CNSCSearchIcon = ({onClick}) => <div className="cnsc-search-close-icon" onClick={onClick} />
