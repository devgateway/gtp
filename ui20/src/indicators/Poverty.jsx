import './poverty.scss'
import 'rc-slider/assets/index.css'
import ReactDOM from 'react-dom';
import {connect} from 'react-redux';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown, RangeSlider} from './Components'
import {BarChart,LineChart} from './PovertyCharts'
import { Tab } from 'semantic-ui-react'
import {getPovertyRegionalYearly,getPovertyRegionalStackedByPovertyLevel, getPovertyTimeLine, items2options} from './DataUtil'

const PovertyFitlers=(props)=>{
  const {filters, onChange,range={},genders=[],activities=[],ageGroups=[]} = props
  const genderSelection = filters && filters.getIn(['poverty', 'gender'])? filters.getIn(['poverty', 'gender']).toJS(): []
  const activitySelection = filters && filters.getIn(['poverty', 'activity'])? filters.getIn(['poverty', 'activity']).toJS() :[]
  const ageGroupsSelection = filters && filters.getIn(['poverty', 'ageGroup'])? filters.getIn(['poverty', 'ageGroup']).toJS() :[]

  //age and score limits
  const minAge =range.age?range.age.min:0
  const maxAge = range.age?range.age.max:0

  const minScore = range.score?range.score.min:0
  const maxScore = range.score?range.score.max:0

  //age and score selection
  const age= [filters.getIn(['poverty','minAge']),filters.getIn(['poverty','maxAge'])]

  const score=[ filters.getIn(['poverty','minScore']),filters.getIn(['poverty','maxScore'])]

  return (<div className="indicator chart filter  poverty">
    <div className="filter item">
      <CustomFilterDropDown options={items2options(genders)}  onChange={s => {onChange([ 'filters', 'poverty', 'gender'], s,['POVERTY'])}} selected={genderSelection} text={<FormattedMessage id = "indicators.filter.gender" defaultMessage = "Gender"  > </FormattedMessage>} />
    </div>
    <div className="filter item">
      <CustomFilterDropDown options={items2options(activities)} onChange={s => {onChange(['filters', 'poverty', 'activity'], s,['POVERTY'])}} selected={activitySelection} text={<FormattedMessage id = "indicators.filter.activity" defaultMessage = "Profesional Activity" > </FormattedMessage>} />
    </div>
    <div className="filter item">
      {<RangeSlider onChange={s => {
        onChange(['filters', 'poverty', 'minScore'],s[0])
        onChange(['filters', 'poverty', 'maxScore'],s[1],['POVERTY'])

      }} max={maxScore} min={minScore}  selected={score} text={<FormattedMessage id="inidicators.filter.slider.score" defaultMessage="Score Range"/>}></RangeSlider>}
    </div>

    <div className="filter item">
      {<RangeSlider onChange={s => {
        onChange(['filters', 'poverty', 'minAge'],s[0])
        onChange(['filters', 'poverty', 'maxAge'],s[1],['POVERTY'])

      }} max={maxAge} min={minAge}  selected={age} text={<FormattedMessage id="inidicators.filter.slider.age" defaultMessage="Age Range"/>}></RangeSlider>}
    </div>
  </div>)
}

class Pooverty extends Component {
  render() {
    const {data=[]} = this.props
    const years = Array.from(new Set(data.map(r => r.year)))
    const maxYear=years.pop()

    const panes = [
      {
        menuItem: 'Yearly Regional',
        render: () =>  (<div> <PovertyFitlers {...this.props}/> <div className="indicators chart poverty"><BarChart {...getPovertyRegionalYearly(data)}/></div></div>),
      },
      {
        menuItem: `${maxYear} Stacked`,
        render: () =>   (<div> <PovertyFitlers {...this.props}/> <div className="indicators chart poverty"><BarChart {...getPovertyRegionalStackedByPovertyLevel(data)}/></div></div>),

      },
      {
        menuItem: 'Time Line',
        render: () =>  (<div> <PovertyFitlers {...this.props}/><div className="indicators chart poverty"><LineChart {...getPovertyTimeLine(data)}/></div></div>),
      }
    ]



    return (<div className="indicator.chart.container">

      <div className="indicator chart title poverty">
        <p>
          <FormattedMessage id="inidicators.chart.poverty.title" defaultMessage="Proportion of population below the international poverty line"></FormattedMessage>
        </p>
        <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
      </div>
      <div className="indicator chart description poverty">
        <p>
          <FormattedMessage id="inidicators.chart.poverty.description" defaultMessage="Proportion of population below the international poverty line is defined as the percentage of the population living on less than $1.90 a day at 2011 international prices. "></FormattedMessage>
        </p>
        <div className="indicator chart icon download xls"></div>
        <div className="indicator chart icon download png"></div>
        <div className="indicator chart icon download csv"></div>
      </div>
      <Tab key="poverty" menu={{ pointing: true }} panes={panes} />

      </div>)
  }

}

const mapStateToProps = state => {
  const filters = state.getIn(['indicator', 'filters'])
  const activities = state.getIn(['data', 'items', 'professionalActivity']);
  const genders = state.getIn(['data', 'items', 'gender']);
  const ageGroups = state.getIn(['data', 'items', 'ageGroup']);
  const range = state.getIn(['data', 'items', 'range']);
  const data=state.getIn(['indicator','poverty','data'])
  return {filters,activities, genders, ageGroups, range,data}
}

const mapActionCreators = {};

export default connect(mapStateToProps, mapActionCreators)(Pooverty);
