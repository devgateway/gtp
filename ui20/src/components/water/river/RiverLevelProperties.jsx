import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import RiverLevelFilter from "../../../modules/entities/river/RiverLevelFilter"
import {yearsToOptions} from "../../../modules/graphic/common/GraphicDTO"
import {riverStationToOptions} from "../../../modules/graphic/water/CommonDTO"
import * as waterActions from "../../../redux/actions/waterActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

class RiverLevelProperties extends Component {
  static propTypes = {
    setYearsFilter: PropTypes.func.isRequired,
    setRiverStationId: PropTypes.func.isRequired,
    config: PropTypes.object.isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    return (
      <div className="indicator chart properties">
        <RiverLevelFilters key="filters" {...this.props} />
      </div>)
  }
}

const RiverLevelFilters = (props) => {
  const {setYearsFilter, setRiverStationId, config, intl}  = props
  const filter: RiverLevelFilter = props.filter
  const {years, riverStationId} = filter

  return (
    <div className="indicator chart filter two-filters">
      <div className="filter item fixed">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={(years) => setYearsFilter(years)}
          min={1}
          selected={years} text={<FormattedMessage id="indicators.filters.year" defaultMessage="Years" />} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={riverStationToOptions(Array.from(config.riverStations.values()), intl)}
          onChange={(riverStationIds) => setRiverStationId(riverStationIds[0])}
          single={true} min={1} max={1} withSearch withTooltips
          selected={[riverStationId]} text={<FormattedMessage id="indicators.filters.river_station" defaultMessage="River Station" />} />
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
  setRiverStationId: (riverStationId) => waterActions.setRiverLevelFilter(['riverStationId'], riverStationId),
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevelProperties))
