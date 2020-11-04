import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {SEASON_MONTHS} from "../../../modules/entities/Constants"
import {decadalsToOptions, monthsToOptions, yearsToOptions} from "../../../modules/graphic/common/GraphicDTO"
import * as rainMapActions from "../../../redux/actions/water/rainMapActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

class RainfallMapProperties extends Component {
  static propTypes = {
    setYearsFilter: PropTypes.func.isRequired,
    setMonth: PropTypes.func.isRequired,
    setDecadal: PropTypes.func.isRequired,
    setShowPostsSetting: PropTypes.func.isRequired,
    config: PropTypes.object.isRequired,
    filter: PropTypes.object.isRequired,
    setting: PropTypes.object.isRequired,
  }
  render() {
    return (
      <div className="indicator chart properties">
        <RainfallMapFilters {...this.props} />
        <RainMapSetting {...this.props} />
      </div>)
  }
}

const RainfallMapFilters = (props) => {
  const {setYearFilter, setMonth, setDecadal, config, intl}  = props
  const {year, month, decadal} = props.filter

  let months = SEASON_MONTHS
  const now =  new Date()
  if (now.getFullYear() === year) {
    months = now.getMonth() === 0 ? [] : months.slice(0, now.getMonth())
  }

  return (
    <div className="indicator chart filter three-filters">
      <div className="filter item fixed">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={(years) => setYearFilter(years[0])}
          min={1} max={1} single
          selected={[year]} text={intl.formatMessage({ id: "indicators.filters.year" })} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={monthsToOptions(intl, months)} onChange={(months) => setMonth(months[0])}
          min={1} max={1} single withSearch
          selected={[month]} text={intl.formatMessage({ id: "all.month", defaultMessage: "Months" })} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={decadalsToOptions(intl)} onChange={(decadals) => setDecadal(decadals[0])}
          min={1} max={1} single
          selected={[decadal]} text={intl.formatMessage({ id: "all.decadal" })} />
      </div>
    </div>)
}

const RainMapSetting = (props) => {
  const {setting, setShowPostsSetting} = props
  const checked = setting.showPluviometricPosts
  const onChange = (showPosts) => setShowPostsSetting(showPosts)
  return (
    <div className="indicator chart setting daysWithOrWithoutRain">
      <div className="setting item">
        <div className="chart toggler view">
          <div className="ui toggle checkbox">
            <div className={checked ? 'active' : ''}>
              <FormattedMessage id="all.posts"/>
            </div>
            <input id="period" type="checkbox" onChange={e => onChange(!checked)}
                   defaultChecked={checked ? 'checked' : ''}/>
            <label className={!checked ? 'active' : ''}></label>
          </div>
        </div>
      </div>
    </div>
  )
}

const mapStateToProps = state => {
  return {
    config: state.getIn(['water', 'data', 'rainMap', 'config']),
  }
}

const mapActionCreators = {
  setYearFilter: (year) => rainMapActions.setRainMapFilter(['year'], year),
  setMonth: (month) => rainMapActions.setRainMapFilter(['month'], month),
  setDecadal: (decadal) => rainMapActions.setRainMapFilter(['decadal'], decadal),
  setShowPostsSetting: (showPosts) => rainMapActions.setRainMapSetting(['showPluviometricPosts'], showPosts)
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallMapProperties))
