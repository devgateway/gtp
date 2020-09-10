import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import CustomLegend from "../../common/legend/CustomLegend"

export default class DiseaseLegend extends Component {
  static propTypes = {
    max: PropTypes.number.isRequired,
  }

  render() {
    const {max} = this.props
    return (
      <CustomLegend>
        <div className="disease-legend">
          <span>{max} <FormattedMessage id="indicators.map.disease.report-cases" /></span>
          <span className="disease-gradient-container"><span className="disease-gradient" /></span>
          <span>{0} <FormattedMessage id="indicators.map.disease.report-cases" /></span>
        </div>
      </CustomLegend>
    )
  }

}
