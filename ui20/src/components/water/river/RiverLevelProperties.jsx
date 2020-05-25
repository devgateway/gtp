import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import RiverLevelFilter from "../../../modules/entities/river/RiverLevelFilter"
import {yearsToOptions} from "../../../modules/graphic/water/CommonDTO"
import * as waterActions from "../../../redux/actions/waterActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

class RiverLevelProperties extends Component {
  static propTypes = {
    setYearsFilter: PropTypes.func.isRequired,
    config: PropTypes.object.isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    return (
      <div className="rainfall">
        <RiverLevelFilters key="filters" {...this.props} />
      </div>)
  }

}

const RiverLevelFilters = (props) => {
  const {setYearsFilter, setRiverStationId, config}  = props
  const filter: RiverLevelFilter = props.filter
  const {years, riverStationId} = filter

  return (
    <div className="indicator chart filter">
      <div className="filter item">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={(years) => setYearsFilter(years)}
          min={1}
          selected={years} text={<FormattedMessage id="indicators.filters.year" defaultMessage="Years" />} />
      </div>
    </div>
  )
}


const mapStateToProps = state => {
  return {
    config: state.getIn(['water', 'data', 'riverLevelChart', 'config']),
  }
}

const mapActionCreators = {
  setYearsFilter: (years) => waterActions.setRiverLevelFilter(['years'], years),
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevelProperties))
