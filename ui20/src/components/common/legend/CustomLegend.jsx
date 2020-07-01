import PropTypes from "prop-types"
import React, {Component} from "react"
import CustomLegendItem from "./CustomLegendItem"

export default class CustomLegend extends Component {
  static propTypes = {
    children: PropTypes.oneOfType([
      PropTypes.arrayOf(PropTypes.instanceOf(CustomLegendItem)),
      PropTypes.node
    ]).isRequired
  }

  render() {
    return (
      <div className="legend">
        {this.props.children}
      </div>
    )
  }
}
