import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import LivestockConfig from "../../../modules/entities/config/LivestockConfig"
import DiseaseQuantityConfig from "../../../modules/entities/diseaseSituation/DiseaseQuantityConfig"
import {anyWithIdAndLabelToOptions, monthsToOptions, yearsToOptions} from "../../../modules/graphic/common/GraphicDTO"
import * as diseaseQuantityActions from "../../../redux/actions/livestock/diseaseQuantityActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

class DiseaseMapProperties extends Component {
  static propTypes = {
    setDiseaseFilter: PropTypes.func.isRequired,
    setYearFilter: PropTypes.func.isRequired,
    setMonth: PropTypes.func.isRequired,
    livestockConfig: PropTypes.instanceOf(LivestockConfig).isRequired,
    config: PropTypes.instanceOf(DiseaseQuantityConfig).isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    return (
      <div className="indicator chart properties">
        <DiseaseQuantityFilters {...this.props} />
      </div>
    )
  }
}

const DiseaseQuantityFilters = (props) => {
  const {setYearFilter, setMonth, setDiseaseFilter, config, intl}  = props
  const {diseases} = props.livestockConfig
  const {year, month, diseaseId} = props.filter

  return (
    <div className="indicator chart filter three-filters">
      <div className="filter item fixed">
        <FilterDropDown
          options={anyWithIdAndLabelToOptions(diseases)}
          onChange={(diseaseIds) => setDiseaseFilter(diseaseIds[0])}
          min={1} max={1} single withSearch withTooltips
          selected={[diseaseId]} text={intl.formatMessage({ id: "indicators.filters.disease" })} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={(years) => setYearFilter(years[0])}
          min={1} max={1} single
          selected={[year]} text={intl.formatMessage({ id: "indicators.filters.year", defaultMessage: "Years" })} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={monthsToOptions(intl)} onChange={(months) => setMonth(months[0])}
          min={1} max={1} single withSearch
          selected={[month]} text={intl.formatMessage({ id: "all.month", defaultMessage: "Months" })} />
      </div>
    </div>
  )
}

const mapStateToProps = state => {
  return {
    livestockConfig: state.getIn(['livestock', 'data', 'livestockConfig']),
    config: state.getIn(['livestock', 'data', 'diseaseQuantityChart', 'config']),
  }
}

const mapActionCreators = {
  setDiseaseFilter: (diseaseId) => diseaseQuantityActions.setDiseaseQuantityFilter(['diseaseId'], diseaseId),
  setYearFilter: (year) => diseaseQuantityActions.setDiseaseQuantityFilter(['year'], year),
  setMonth: (month) => diseaseQuantityActions.setDiseaseQuantityFilter(['month'], month, true),
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DiseaseMapProperties))
