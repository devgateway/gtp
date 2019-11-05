import './poverty.scss'
import 'rc-slider/assets/index.css'

import ReactDOM from 'react-dom';
import {connect} from 'react-redux';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown} from './Components'
import Plot from 'react-plotly.js';
import {loadDefaultPovertyFilters, loadPovertyChartData, loadDataItems} from '../modules/Indicator'
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
      <Range step={1} dots={false} defaultValue={selected} min={min} max={max} onChange={onChange}/>
      <span className="breadcrumbs min">Max: {selected[0]}</span>
      <span className="breadcrumbs max">Min: {selected[1]}
      </span>
    </div>
  </div>
}

class Pooverty extends Component {

  componentDidMount() {
  }

  componentDidUpdate(prevProps, prevState, snapshot) {

        if (this.props.globalFiltersReady){

        }
      if (this.props.filterItemsReady && !this.props.povertyFiltersReady){
          this.props.loadDefaultPovertyFilters()
      }

      if (this.props.globalFiltersReady && this.props.povertyFiltersReady && !this.props.data){
          debugger;
          this.props.loadPovertyChartData()
      }
  }


  render() {
    const {filters, onChange,povertyRange,genders=[],activities=[],ageGroups=[]} = this.props


    const genderSelection = filters && filters.getIn(['poverty', 'gender'])? filters.getIn(['poverty', 'gender']).toJS(): []
    const activitySelection = filters && filters.getIn(['poverty', 'activity'])? filters.getIn(['poverty', 'activity']).toJS() :[]
    const ageGroupsSelection = filters && filters.getIn(['poverty', 'ageGroup'])? filters.getIn(['poverty', 'ageGroup']).toJS() :[]

    const min=povertyRange?povertyRange.score.min:null
    const max=povertyRange?povertyRange.score.max:null

    const povertyRangeSelection = filters && filters.getIn(['poverty', 'range'])? filters.getIn(['poverty', 'range']).toJS():[min,max]

    return (<div className="indicator.chart.container">

      {(this.props.globalFiltersReady)?<h1>Global Filter  ready</h1>:""}


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
          <CustomFilterDropDown options={gender2options(genders)}  onChange={s => {onChange([ 'filters', 'poverty', 'gender'], s)}} selected={genderSelection} text={<FormattedMessage id = "indicators.filter.gender" defaultMessage = "Gender"  > </FormattedMessage>} />
        </div>
        <div className="filter item">
          <CustomFilterDropDown options={activity2options(activities)} onChange={s => {onChange(['filters', 'poverty', 'activity'], s)}} selected={activitySelection} text={<FormattedMessage id = "indicators.filter.activity" defaultMessage = "Profesional Activity" > </FormattedMessage>} />
        </div>
        <div className="filter item">
          {min&&max&&<RangeSlider onChange={s => {onChange(['filters', 'poverty', 'range'], s)}} max={max} min={min}  selected={povertyRangeSelection} text={<FormattedMessage id="inidicators.filter.slider.level" defaultMessage="Poverty Level"/>}></RangeSlider>}
        </div>

        <div className="filter item">
          <OptionList options={age2options(ageGroups)} selected={ageGroupsSelection } onChange={s => {onChange(['filters', 'poverty', 'ageGroup'], s)}} text={<FormattedMessage id = "inidicators.filter.option.age" defaultMessage = "Age Range" />}></OptionList>
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
  const povertyRange = state.getIn(['data', 'items', 'range']);


  const filterItemsReady = (activities  && ageGroups && povertyRange) != null
  const povertyFiltersReady = filters.get('poverty') != null

  const data=state.getIn(['indicator','poverty','data'])

  return {activities, genders, ageGroups, povertyRange, filterItemsReady, povertyFiltersReady,data}
}

const mapActionCreators = {
  
  loadDefaultPovertyFilters,
};

export default connect(mapStateToProps, mapActionCreators)(Pooverty);
