import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import RainLevelSetting from "../../../modules/entities/rainfall/RainLevelSetting"
import * as waterActions from "../../../redux/actions/waterActions"

class RainfallProperties extends Component {
  static propTypes = {
    setting: PropTypes.instanceOf(RainLevelSetting).isRequired,
    setRainPerDecadal: PropTypes.func.isRequired,
  }

  render() {
    return (
      <div>
        <PeriodSetting {...this.props} />
      </div>)
  }
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
    setting: state.getIn(['water', 'data', 'rainLevelChart', 'setting']),
  }
}

const mapActionCreators = {
  setRainPerDecadal: waterActions.setRainPerDecadal,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainfallProperties))
