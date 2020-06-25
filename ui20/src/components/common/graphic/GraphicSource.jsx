import React from "react"
import {FormattedMessage} from "react-intl"

const GraphicSource = (sourceMessageId) =>
  (<div className="source">
    <span className="content" >
      <span className="source label">
        <FormattedMessage id="data.fields.source_label" defaultMessage="Source: "/>
      </span>
      <FormattedMessage id={sourceMessageId}/>
    </span>
  </div>)

export default GraphicSource
