import {BarDefaultProps} from "@nivo/bar"
import React from "react"

const DefaultBarOrNegativeValueAsZeroBar = (props) => {
  const {height, data} = props
  const { value } = data
  return <BarDefaultProps.barComponent  {...props} height={value > 0 ? height : 2} />
}

export default DefaultBarOrNegativeValueAsZeroBar
