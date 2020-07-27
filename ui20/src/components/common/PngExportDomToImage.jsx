import download from "downloadjs"
import domtoimage from 'dom-to-image'
import React from "react"

const PngExport = ({id, element, name = "chart", filters = [],includes = []})=> {

  return (
    <div className="icon download masked-icon icon-download" onClick={e => {

      const node = id ? document.getElementById(id) : document[element];
      const exportable = id ? node.getElementsByClassName("png exportable")[0] : node

      domtoimage.toPng(exportable, {
        bgcolor: "#ffffff",
      }).then(function (dataUrl) {
        download(dataUrl, name + '.png', "image/png")
      }).catch(function (error) {
        console.error('oops, something went wrong!', error);
      });
    }}/>)
}

export default PngExport
