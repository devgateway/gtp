import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {yearsToOptions} from "../../../modules/graphic/common/GraphicDTO"
import {postIdsToOptions} from "../../../modules/graphic/water/CommonDTO"
import * as waterActions from "../../../redux/actions/waterActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

class DrySequenceProperties extends Component {
  static propTypes = {
    isDaysWithRain: PropTypes.bool.isRequired,
    showDaysWithRain: PropTypes.func.isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    return (
      <div className="indicator chart properties">
        <DrySequenceFilters key="filters" {...this.props} />
        <DrySeasonSetting key="settings" {...this.props} />
      </div>)
  }
}

const DrySequenceFilters = (props) => {
  const {config, filter, setFilter, waterConfig, commonConfig} = props
  const onYearChange = (year) => setFilter(year[0], filter.pluviometricPostId)
  const onPostChange = (postId) => setFilter(filter.year, postId[0])
  const {year, pluviometricPostId} = filter
  return (
    <div className="indicator chart filter two-filters">
      <div className="filter item fixed">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={onYearChange}
          min={1} single
          selected={[year]} text={<FormattedMessage id="indicators.filters.year" defaultMessage="Years" />} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={postIdsToOptions(config.pluviometricPostIds, waterConfig, commonConfig)} onChange={onPostChange}
          min={1} single withSearch withTooltips
          selected={[pluviometricPostId]} text={<FormattedMessage id="indicators.filters.location" defaultMessage="Location" />} />
      </div>
    </div>
  )
}


const DrySeasonSetting = (props) => {
  const {isDaysWithRain, showDaysWithRain} = props
  const checked = !isDaysWithRain
  const onChange = (isDaysWithRain) => showDaysWithRain(isDaysWithRain)
  return (
    <div className="indicator chart setting daysWithOrWithoutRain">
      <div className="setting item">
        <div className="chart toggler view">
          <div className="ui toggle checkbox">
            <div className={!checked ? 'active' : ''}>
              <FormattedMessage id="indicators.settings.withRain"/>
            </div>
            <input id="period" type="checkbox" onChange={e => onChange(!e.target.checked)}
                   defaultChecked={checked ? 'checked' : ''}/>
            <label className={checked ? 'active' : ''}></label>
            <div className={checked ? 'active' : ''}>
              <FormattedMessage id="indicators.settings.withoutRain"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}


const mapStateToProps = state => {
  return {
    commonConfig: state.getIn(['app', 'data', 'commonConfig']),
    waterConfig: state.getIn(['water', 'data', 'waterConfig']),
    config: state.getIn(['water', 'data', 'rainLevelChart', 'config']),
  }
}

const mapActionCreators = {
  showDaysWithRain: waterActions.showDaysWithRain,
  setFilter: waterActions.setDrySequenceFilter,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DrySequenceProperties))
