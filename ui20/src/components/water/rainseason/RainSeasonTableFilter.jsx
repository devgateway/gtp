import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import RainSeasonConfigDTO from "../../../modules/graphic/water/rainSeason/RainSeasonConfigDTO"
import * as C from "../../../modules/graphic/water/rainSeason/RainSeasonConstants"
import * as waterActions from "../../../redux/actions/waterActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

const filterPath = ['data', 'rainSeasonChart', 'filter']

class RainSeasonTableFilter extends Component {
  static propTypes = {
    config: PropTypes.instanceOf(RainSeasonConfigDTO).isRequired,
    columnName: PropTypes.string.isRequired,
    filter: PropTypes.object.isRequired,
    setFilter: PropTypes.func.isRequired,
    min: PropTypes.number.isRequired,
    max: PropTypes.number,
  }

  render() {
    const {config, columnName, filter, setFilter, min, max} = this.props
    const options = config[`${columnName}s`]
    const filterName = `${columnName}Ids`
    const path = [...filterPath, filterName]
    const isYearFilter = columnName === C.YEAR
    const onSetFilter = (value) => setFilter(path, value, isYearFilter)
    return (
      <div className="filter item small">
        <FilterDropDown
          options={options} onChange={onSetFilter} withSearch={!isYearFilter}
          single={max === 1} max={max} min={min}
          selected={filter[filterName]} withTooltips={!isYearFilter}
          text={<FormattedMessage id={C.FILTER_MESSAGE_KEY[columnName]} defaultMessage={columnName}/>}/>
      </div>
    )
  }

}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  setFilter: waterActions.setRainSeasonFilter,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainSeasonTableFilter))

