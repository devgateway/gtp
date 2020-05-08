import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import RainLevelConfig from "../../../modules/entities/rainfall/RainLevelConfig"
import RainLevelFilter from "../../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../../modules/entities/rainfall/RainLevelSetting"
import * as waterActions from "../../../redux/actions/waterActions"
import {CustomFilterDropDown} from "../../common/Components"


class RainfallProperties extends Component {
  static propTypes = {
    config: PropTypes.instanceOf(RainLevelConfig).isRequired,
    filter: PropTypes.instanceOf(RainLevelFilter).isRequired,
    setFilter: PropTypes.func.isRequired,
    setting: PropTypes.instanceOf(RainLevelSetting).isRequired,
    setRainPerDecadal: PropTypes.func.isRequired,
  }

  render() {
    return (
      <div>
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

const RainfallFilters = (props) => {
  const {config, filter, setFilter} = props
  const onYearChange = (years) => setFilter(years, filter.pluviometricPostId)
  const {years} = filter
  const yearsOptions = yearsToOptions(config.years)
  return (
    <div className="indicator chart filter property">
      <div className="filter item">
        <CustomFilterDropDown
          options={yearsOptions} onChange={onYearChange}
          selected={years} text={<FormattedMessage id="indicators.filters.year" defaultMessage="Years" />} />
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
            <div className={!byDecadal ? 'active' : ''} onClick={() => onChange(false)}>
              <FormattedMessage id="indicators.settings.months"/>
            </div>
            <input id="period" type="checkbox" onChange={e => onChange(e.target.checked)}
                   defaultChecked={byDecadal ? 'checked' : ''}/>
            <label className={byDecadal ? 'active' : ''}></label>
            <div className={byDecadal ? 'active' : ''} onClick={() => onChange(true)}>
              <FormattedMessage id="indicators.settings.decadals"/>
            </div>
          </div>
        </div>
      </div>
    )
  }

const mapStateToProps = state => {
  return {
    config: state.getIn(['water', 'data', 'rainLevelChart', 'config']),
    filter: state.getIn(['water', 'data', 'rainLevelChart', 'filter']),
    setting: state.getIn(['water', 'data', 'rainLevelChart', 'setting']),
  }
}

const mapActionCreators = {
  setRainPerDecadal: waterActions.setRainPerDecadal,
  setFilter: waterActions.setFilter,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallProperties))
