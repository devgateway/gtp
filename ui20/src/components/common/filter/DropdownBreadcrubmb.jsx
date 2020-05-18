import React from "react"

const DropdownBreadcrumb = (options, selected, text, single) => {
  const isAnySelected = selected && selected.length > 0
  const selectedVsTotal = `(${selected.length}/${options.length})`
  const details = single ? (isAnySelected ? options.find(a=>a.key === selected[0]).text : text) : selectedVsTotal
  return (
    <div className="breadcrums">
      <span className="filterName">{text} </span>
      <span>{details}</span>
    </div>)
}

export default DropdownBreadcrumb
