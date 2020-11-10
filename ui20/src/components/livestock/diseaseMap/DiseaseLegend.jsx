import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import CustomLegend from "../../common/legend/CustomLegend"

export default class DiseaseLegend extends Component {
  static propTypes = {
    max: PropTypes.number.isRequired,
    min: PropTypes.number.isRequired,
  }

  render() {
    const {min, max} = this.props
    const maxCases = max || "#"
    const minCases = min || 0
    return (
      <CustomLegend>
        <div className="disease-legend">
          <span>{maxCases} <FormattedMessage id="indicators.map.disease.report-cases" /></span>
          <span className="disease-gradient-container"><span className="disease-gradient" /></span>
          <span>{minCases} <FormattedMessage id="indicators.map.disease.report-cases" /></span>
        </div>
      </CustomLegend>
    )
  }

}
