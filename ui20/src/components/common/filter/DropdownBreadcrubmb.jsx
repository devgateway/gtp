import React from "react"
import {Popup} from "semantic-ui-react"

const DropdownBreadcrumb = (options, selected, text, single, withTooltip) => {
  const isAnySelected = selected && selected.length > 0
  const selectedVsTotal = `(${selected.length}/${options.length})`
  const details = single ? (isAnySelected ? findSingleSelected(options, selected, text)  : text) : selectedVsTotal
  const filterValue = <span className="filterValue">{details}</span>
  return (
    <div className="breadcrums">
      <span className="filterName">{text} </span>
      {withTooltip ? <Popup content={details} trigger={filterValue} hoverable /> : filterValue}
    </div>)
}

const findSingleSelected = (options: Array, selected: Array, text) => {
  if (!selected.length || !options.length) return text
  const option = options.find(a => a.key === selected[0])
  return option ? option.text : text
}

export default DropdownBreadcrumb
