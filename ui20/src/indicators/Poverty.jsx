import './poverty.scss'
import 'rc-slider/assets/index.css'

import ReactDOM from 'react-dom';
import {connect} from 'react-redux';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown} from './Components'
import Plot from 'react-plotly.js';
import Slider, {Range} from 'rc-slider';
import {Dropdown,Grid,Image,Rail,Ref,Segment,Sticky} from 'semantic-ui-react'
import PovertyCharts from './PovertyCharts'

const gender2options = (genders) => genders
  ? genders.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

const activity2options = (activities) => activities
  ? activities.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

const age2options = (activities) => activities
  ? activities.sort((c1, c2) => c1.id - c2.id).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

export const OptionList = ({options, selected, onChange, text}) => {
  const updateSelection = (key) => {
    const newSelection = selected.slice(0)
    if (newSelection.indexOf(key) > -1) {
      newSelection.splice(newSelection.indexOf(key), 1);
    } else {
      newSelection.push(key)
    }
    onChange(newSelection)
  }

  const getChecked = (key) => {
    return selected.indexOf(key) > -1
  }
  return (<div className="indicator filter options age">
    <p>{text}</p>
    {
      options.map((a) => {
        return <div onClick={e => updateSelection(a.key)} className={`item ${getChecked(a.key)
            ? 'active'
            : ''}`}>
          <div className="checkbox"></div>
          <div className="label">{a.text}</div>
        </div>
      })
    }
  </div>)
}

export const RangeSlider = ({max,min,step,selected,onChange,text}) => {

  return <div className="slider container">
    <p>{text}</p>
    <div>
      <Range step={1} dots={false} value={selected} min={min} max={max} onChange={onChange}/>
      <span className="breadcrumbs min">Min: {selected[0]}</span>
      <span className="breadcrumbs max">Max: {selected[1]}
      </span>
    </div>
  </div>
}

class Pooverty extends Component {



  render() {
    const {filters, onChange,range={},genders=[],activities=[],ageGroups=[]} = this.props


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


      <div className="indicator chart filter  poverty">
        <div className="filter item">
          <CustomFilterDropDown options={gender2options(genders)}  onChange={s => {onChange([ 'filters', 'poverty', 'gender'], s,['POVERTY'])}} selected={genderSelection} text={<FormattedMessage id = "indicators.filter.gender" defaultMessage = "Gender"  > </FormattedMessage>} />
        </div>
        <div className="filter item">
          <CustomFilterDropDown options={activity2options(activities)} onChange={s => {onChange(['filters', 'poverty', 'activity'], s,['POVERTY'])}} selected={activitySelection} text={<FormattedMessage id = "indicators.filter.activity" defaultMessage = "Profesional Activity" > </FormattedMessage>} />
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
      </div>

      {this.props.data && <PovertyCharts data={this.props.data}/>}

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
