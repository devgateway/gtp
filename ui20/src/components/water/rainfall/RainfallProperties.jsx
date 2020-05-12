import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import CommonConfig from "../../../modules/entities/rainfall/CommonConfig"
import RainLevelConfig from "../../../modules/entities/rainfall/RainLevelConfig"
import RainLevelFilter from "../../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../../modules/entities/rainfall/RainLevelSetting"
import * as waterActions from "../../../redux/actions/waterActions"
import {CustomFilterDropDown} from "../../common/Components"


class RainfallProperties extends Component {
  static propTypes = {
    commonConfig: PropTypes.instanceOf(CommonConfig).isRequired,
    config: PropTypes.instanceOf(RainLevelConfig).isRequired,
    filter: PropTypes.instanceOf(RainLevelFilter).isRequired,
    setFilter: PropTypes.func.isRequired,
    setting: PropTypes.instanceOf(RainLevelSetting).isRequired,
    setRainPerDecadal: PropTypes.func.isRequired,
  }

  render() {
    return (
      <div className="rainfall">
        <RainfallFilters key="filters" {...this.props} />
        <PeriodSetting key="settings" {...this.props} />
      </div>)
  }
}

const yearsToOptions = (years) => years.sort().reverse().map(y => ({
  key: y,
  text: y,
  value: y
}))

const postIdsToOptions = (postIds, commonConfig: CommonConfig) => postIds.map(id => {
  const post = commonConfig.posts.get(id)
  const dep = post.department
  return ({
  key: id,
  text: `${post.label} (${dep.name})`,
  value: id
})}).sort((p1, p2) => p1.text.localeCompare(p2.text))

const RainfallFilters = (props) => {
  const {config, filter, setFilter, commonConfig} = props
  const onYearChange = (years) => setFilter(years, filter.pluviometricPostId)
  const onPostChange = (postIds) => setFilter(filter.years, postIds[0])
  const {years, pluviometricPostId} = filter
  return (
    <div className="indicator chart filter">
      <div className="filter item">
        <CustomFilterDropDown
          options={yearsToOptions(config.years)} onChange={onYearChange}
          min={1} max={3}
          selected={years} text={<FormattedMessage id="indicators.filters.year" defaultMessage="Years" />} />
      </div>
      <div className="filter item">
        <CustomFilterDropDown
          options={postIdsToOptions(config.pluviometricPostIds, commonConfig)} onChange={onPostChange}
          single={true} max={1}
          selected={[pluviometricPostId]} text={<FormattedMessage id="indicators.filters.location" defaultMessage="Location" />} />
      </div>
    </div>
  )
}

const PeriodSetting = (props) => {
    const {byDecadal} = props.setting
    const onChange = (byDecadal) => props.setRainPerDecadal(byDecadal)
    return (
      <div className="period">
        <div className="chart toggler view">
          { /* <label><FormattedMessage id="indicators.settings.periodicity" /></label> */}
          <div className="ui toggle checkbox">
            <div className={!byDecadal ? 'active' : ''}>
              <FormattedMessage id="indicators.settings.months"/>
            </div>
            <input id="period" type="checkbox" onChange={e => onChange(e.target.checked)}
                   defaultChecked={byDecadal ? 'checked' : ''}/>
            <label className={byDecadal ? 'active' : ''}></label>
            <div className={byDecadal ? 'active' : ''}>
              <FormattedMessage id="indicators.settings.decadals"/>
            </div>
          </div>
        </div>
      </div>
    )
  }

const mapStateToProps = state => {
  return {
    commonConfig: state.getIn(['water', 'data', 'commonConfig']),
    config: state.getIn(['water', 'data', 'rainLevelChart', 'config']),
    filter: state.getIn(['water', 'data', 'rainLevelChart', 'filter']),
    setting: state.getIn(['water', 'data', 'rainLevelChart', 'setting']),
  }
}

const mapActionCreators = {
  setRainPerDecadal: waterActions.setRainPerDecadal,
  setFilter: waterActions.setRainfallFilter,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallProperties))
