import React from "react"
import {FormattedMessage} from "react-intl"

const RainfallProperties = (props) => {
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
          <input id="period" type="checkbox" onChange={e => onChange(!e.target.checked)}
                 defaultChecked={!byDecadal ? 'checked' : ''}/>
          <label className={byDecadal ? 'active' : ''}></label>
          <div className={byDecadal ? 'active' : ''} onClick={() => onChange(true)}>
            <FormattedMessage id="indicators.settings.decadals"/>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RainfallProperties

