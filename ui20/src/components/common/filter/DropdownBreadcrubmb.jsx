import React from "react"

const DropdownBreadcrumb = (options, selected, text, single) => {
  const isAnySelected = selected && selected.length > 0
  const selectedVsTotal = `(${selected.length}/${options.length})`
  const details = single ? (isAnySelected ? findSingleSelected(options, selected, text)  : text) : selectedVsTotal
  return (
    <div className="breadcrums">
      <span className="filterName">{text} </span>
      <span>{details}</span>
    </div>)
}

const findSingleSelected = (options: Array, selected: Array, text) => {
  if (!selected.length || !options.length) return text
  const option = options.find(a => a.key === selected[0])
  return option ? option.text : text
}

export default DropdownBreadcrumb
