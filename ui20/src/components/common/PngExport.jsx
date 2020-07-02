import download from "downloadjs"
import html2canvas from "html2canvas"
import React from "react"


const PngExport = ({id, element, name = "chart", filters = [],includes = []})=> {

  return (
    <div className="icon download masked-icon icon-png-download" onClick={e => {

      const node = id ? document.getElementById(id) : document[element];
      const exportable = id ? node.getElementsByClassName("png exportable")[0] : node
      const scrollY = window.pageYOffset

      html2canvas(exportable, {
        allowTaint: true,
        useCORS: true,
        dpi: 144,
        scrollY: scrollY * -1
      }).then(function (canvas) {
        // window.scrollTo(0, scrollY);
        download(canvas.toDataURL("image/png"), name + '.png', "image/png")
      }).catch(function (error) {
          console.error('oops, something went wrong!', error);
        });
    }}/>)
}

export default PngExport
