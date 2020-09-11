import React, {Component} from "react"
import CustomLegend from "../../common/legend/CustomLegend"
import CustomLegendItem from "../../common/legend/CustomLegendItem"
import {LEGEND_SYMBOL_CIRCLE} from "../../common/legend/CustomLegendSymbol"
import {COLOR_BLUE} from "../../common/map/MapUtils"

export default class PluviometricPostMapLegned extends Component {
  render() {
    const {intl} = this.props

    return (
      <CustomLegend>

        <CustomLegendItem
            key="pluviometric-post-legend"
            type={LEGEND_SYMBOL_CIRCLE}
            label={intl.formatMessage({ id: "all.posts"})}
            x={0}
            y={0}
            size={12}
            fill={COLOR_BLUE}/>

      </CustomLegend>)
  }
}
