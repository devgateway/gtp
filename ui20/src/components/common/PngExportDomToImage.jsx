import download from "downloadjs"
import domtoimage from 'dom-to-image'
import React from "react"

const PngExport = ({id, element, name = "chart", filters = [],includes = []})=> {
  const hasFilters = !!filters.length
  const hasIncludes = !!includes.length

  return (
    <div className="icon download masked-icon icon-download" onClick={e => {

      const node = id ? document.getElementById(id) : document[element];
      const exportable = id ? node.getElementsByClassName("png exportable")[0] : node

      const doFilter = (node) => {
        const nodeClasses = node.classList ? [...node.classList] : []
        return !hasFilters || !nodeClasses.some(c => filters.includes(c)) ||
          (hasIncludes && nodeClasses.some(c => includes.includes(c)))
      }

      domtoimage.toPng(exportable, {
        bgcolor: "#ffffff",
        filter: doFilter,
        imagePlaceholder: "/icon/icon_no_data.svg",
      }).then(function (dataUrl) {
        download(dataUrl, name + '.png', "image/png")
      }).catch(function (error) {
        console.error('oops, something went wrong!', error);
      });
    }}/>)
}

export default PngExport
