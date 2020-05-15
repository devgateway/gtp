import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"

class DrySequenceProperties extends Component {
  static propTypes = {
    isDaysWithRain: PropTypes.bool.isRequired,
    showDaysWithRain: PropTypes.func.isRequired,
  }

  render() {
    return (
      <div className="rainfall">
        <DrySeasonSetting key="settings" {...this.props} />
      </div>)
  }

}

const DrySeasonSetting = (props) => {
  const {isDaysWithRain, showDaysWithRain} = props
  const checked = !isDaysWithRain
  const onChange = (isDaysWithRain) => showDaysWithRain(isDaysWithRain)
  return (
    <div className="daysWithOrWithoutRain">
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
  )
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  showDaysWithRain: waterActions.showDaysWithRain,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DrySequenceProperties))
