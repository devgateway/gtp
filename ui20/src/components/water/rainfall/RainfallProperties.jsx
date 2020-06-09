import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import WaterConfig from "../../../modules/entities/config/WaterConfig"
import RainLevelConfig from "../../../modules/entities/rainfall/RainLevelConfig"
import RainLevelFilter from "../../../modules/entities/rainfall/RainLevelFilter"
import {postIdsToOptions, yearsToOptions} from "../../../modules/graphic/water/CommonDTO"
import * as waterActions from "../../../redux/actions/waterActions"
import FilterDropDown from "../../common/filter/FilterDropDown"
import {cssClasses} from "../../ComponentUtil"


class RainfallProperties extends Component {
  static propTypes = {
    waterConfig: PropTypes.instanceOf(WaterConfig).isRequired,
    config: PropTypes.instanceOf(RainLevelConfig).isRequired,
    filter: PropTypes.instanceOf(RainLevelFilter).isRequired,
    setFilter: PropTypes.func.isRequired,
    setting: PropTypes.object.isRequired,
    setRainPerDecadal: PropTypes.func.isRequired,
    setShowReferences: PropTypes.func.isRequired,
  }

  render() {
    return (
      <div className="indicator chart properties">
        <RainfallFilters key="filters" {...this.props} />
        <RainfallSetting key="settings" {...this.props} />
      </div>)
  }
}

const RainfallFilters = (props) => {
  const {config, filter, setFilter, waterConfig} = props
  const onYearChange = (years) => setFilter(years, filter.pluviometricPostId)
  const onPostChange = (postIds) => setFilter(filter.years, postIds[0])
  const {years, pluviometricPostId} = filter
  return (
    <div className="indicator chart filter">
      <div className="filter item">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={onYearChange}
          min={1} max={3}
          description={<FormattedMessage id="indicators.filters.year.description" defaultMessage="Years" />}
          selected={years} text={<FormattedMessage id="indicators.filters.year" defaultMessage="Years" />} />
      </div>
      <div className="filter item">
        <FilterDropDown
          options={postIdsToOptions(config.pluviometricPostIds, waterConfig)} onChange={onPostChange}
          single={true} min={1} max={1} withSearch
          selected={[pluviometricPostId]} text={<FormattedMessage id="indicators.filters.location" defaultMessage="Location" />} />
      </div>
    </div>
  )
}

const RainfallSetting = (props) => {
  const {setRainPerDecadal, setShowReferences} = props
  const {byDecadal, showReferences} = props.setting
  return (
    <div className="indicator chart setting">
      <div className="setting item">
        <div className="chart toggler view">
          <div className="ui toggle checkbox">
            <div className={!byDecadal ? 'active' : ''}>
              <FormattedMessage id="indicators.settings.months"/>
            </div>
            <input id="period" type="checkbox" onChange={e => setRainPerDecadal(e.target.checked)}
                   defaultChecked={byDecadal ? 'checked' : ''}/>
            <label className={byDecadal ? 'active' : ''}/>
            <div className={byDecadal ? 'active' : ''}>
              <FormattedMessage id="indicators.settings.decadals"/>
            </div>
          </div>
        </div>
        <div className="ui separator" />
      </div>
      <div className="setting item">
        <div className="chart toggler view">
          <div className={cssClasses("ui", "slider", "checkbox", showReferences ? "checked" : null)}>
            <div className="reversed inline">
              <label className={showReferences ? 'active' : ''}>
                <span><FormattedMessage id="indicators.settings.showReferences"/></span>
              </label>
              <input id="showRefs" type="checkbox" className="hidden" onClick={e => setShowReferences(!showReferences)}
                     defaultChecked={showReferences ? 'checked' : ''}/>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

const mapStateToProps = state => {
  return {
    waterConfig: state.getIn(['water', 'data', 'waterConfig']),
    config: state.getIn(['water', 'data', 'rainLevelChart', 'config']),
    filter: state.getIn(['water', 'data', 'rainLevelChart', 'filter']),
    setting: state.getIn(['water', 'data', 'rainLevelChart', 'setting']),
  }
}

const mapActionCreators = {
  setRainPerDecadal: (value) => waterActions.setRainSetting(['byDecadal'], value),
  setShowReferences: (value) => waterActions.setRainSetting(['showReferences'], value),
  setFilter: waterActions.setRainfallFilter,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallProperties))
