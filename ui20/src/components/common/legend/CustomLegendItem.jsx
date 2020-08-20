import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {cssClasses} from "../../ComponentUtil"
import type {CustomLegendSymbolProps} from "./CustomLegendSymbol"
import CustomLegendSymbol from "./CustomLegendSymbol"
import "./customLegend.scss"


export interface CustomLegendItemPropsInterface extends CustomLegendSymbolProps {
  key?: string | number;
  messageId?: string;
  label: string | React.ElementType;
  leftToRight?: boolean;
  onClick?: (e: Event) => any;
}

export default class CustomLegendItem extends Component<CustomLegendItemProps> {

  static defaultProps = {
    leftToRight: true,
  }

  render() {
    const {label, messageId, leftToRight, size, onClick} = this.props
    const message = label ? label : <FormattedMessage key="message" id={messageId}/>

    let legendItem = [
      (<span key="symbol" className="symbol">
        <svg width={size} height={size}>
          <g><CustomLegendSymbol key="symbol" {...this.props} /></g>
        </svg>
      </span>),
      <span key="message" className="legend-item-label">{message}</span>]
    if (!leftToRight) {
      legendItem = legendItem.reverse()
    }

    return (
      <div className={cssClasses("custom-legend-item", onClick ? "clickable" : null)}  onClick={onClick}>
        {legendItem}
      </div>)
  }
}
