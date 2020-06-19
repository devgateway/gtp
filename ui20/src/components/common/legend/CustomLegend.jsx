import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import CustomLegendSymbol, {LEGEND_SYMBOLS} from "./CustomLegendSymbol"
import "./customLegend.scss"

export default class CustomLegendItem extends Component {
  static propTypes = {
    messageId: PropTypes.string,
    label: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
    leftToRight: PropTypes.bool,
    type: PropTypes.oneOf(LEGEND_SYMBOLS).isRequired,
    lineLength: PropTypes.number,
    legendProps: PropTypes.object.isRequired,
  }

  static defaultProps = {
    leftToRight: true,
  }

  render() {
    const {label, messageId, leftToRight, legendProps} = this.props
    const {size} = legendProps
    const message = label ? label : <FormattedMessage key="message" id={messageId}/>

    let legendItem = [
      (<span key="symbol" className="symbol">
        <svg width={size} height={size}>
          <g><CustomLegendSymbol key="symbol" {...this.props} /></g>
        </svg>
      </span>),
      <span key="message">{message}</span>]
    if (!leftToRight) {
      legendItem = legendItem.reverse()
    }

    return (
      <div className="custom-legend-item">
        {legendItem}
      </div>)
  }
}
