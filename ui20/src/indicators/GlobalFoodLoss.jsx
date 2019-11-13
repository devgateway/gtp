import './poverty.scss'
import 'rc-slider/assets/index.css'

import ReactDOM from 'react-dom';
import {connect} from 'react-redux';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown,OptionList} from './Components'
import Plot from 'react-plotly.js';
import Slider, {Range} from 'rc-slider';
import {Dropdown,Grid,Image,Rail,Ref,Segment,Sticky} from 'semantic-ui-react'
import { Tab } from 'semantic-ui-react'
import {gender2options,age2options,items2options} from '../api'
import './globalFoodLoss.scss'

import {ByAgeBar,ByAgeAndYearLine, ByMethodOfEnforcementBar,ByMethodOfEnforcementLine} from './GlobalFoodLossCharts'

const  Filters=({genders,ageGroups,methodOfEnforcements,filters,onChange, options})=>{
  const genderSelection = filters && filters.getIn(['women', 'gender'])? filters.getIn(['women', 'gender']).toJS(): []
  const ageSelection = filters && filters.getIn(['women', 'ageGroup'])? filters.getIn(['women', 'ageGroup']).toJS(): []
  const methodOfEnforcementsSelection = filters && filters.getIn(['women', 'methodOfEnforcement'])? filters.getIn(['women', 'methodOfEnforcement']).toJS(): []
  return (<div className="indicator chart filter  women">
      <div className="filter item">
        <CustomFilterDropDown disabled={!options.gender} options={items2options(genders)}  onChange={s => {onChange([ 'filters', 'women', 'gender'], s,['WOMEN'])}} selected={genderSelection} text={<FormattedMessage id = "indicators.filter.gender" defaultMessage = "Gender"  > </FormattedMessage>} />
      </div>

       <div className="filter item">
        <CustomFilterDropDown disabled={!options.methodOfEnforcement} options={items2options(methodOfEnforcements)}  onChange={s => {onChange([ 'filters', 'women', 'methodOfEnforcement'], s,['WOMEN'])}} selected={methodOfEnforcementsSelection} text={<FormattedMessage id = "indicators.filter.enforcement.method" defaultMessage = "Enforcement Method"  > </FormattedMessage>} />
      </div>

      <div className="filter item">
        <OptionList disabled={!options.age} options={items2options(ageGroups)}  onChange={s => {onChange([ 'filters', 'women', 'ageGroup'], s,['WOMEN'])}} selected={ageSelection} text={<FormattedMessage id = "indicators.filter.ageGroup" defaultMessage = "Age Group"  > </FormattedMessage>} />
      </div>

    </div>)
}


const ChartSection = ( props)=>{
  let lastetYear=null
    if (props.population){
      lastetYear=props.population.map(d=>d.year).sort()[props.population.length-1];
    }
    const panes = [
       {
         menuItem:  { key: 'bar', icon: '', content: 'By Gender '+(lastetYear?'('+lastetYear+')':'') },
         render: () =>
            <div className="indicators chart food">
              <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></Filters>
              <ByAgeBar  {...props} data={props.population}></ByAgeBar>
            </div>,
       },
       {
         menuItem:  { key: 'line', icon: '', content: 'Female Progression' },
         render: () =><div className="indicators chart food">
               <Filters {...props} options={{gender:false, age:true ,methodOfEnforcement:false}}></Filters>
               <ByAgeAndYearLine  data={props.population} {...props}></ByAgeAndYearLine>
             </div>,

       }
     ]
    return (
        <div className="indicator.chart.container">

        <div className="indicator chart food title ">
          <p>
            <FormattedMessage id="inidicators.chart.food.title" defaultMessage="Global Food Loss Index"></FormattedMessage>
          </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
        </div>
        <div className="indicator chart food description">
          <p>
            <FormattedMessage id="inidicators.chart.food.description" defaultMessage="Global Food Loss Index (GFLI), measuring the total losses of ag. commodities from the production to the retail level. By 2030, halve
per capita global food waste at the retail and consumer levels and reduce food losses along production and supply chains."></FormattedMessage>
          </p>
          <div className="indicator chart icon download xls"></div>
          <div className="indicator chart icon download png"></div>
          <div className="indicator chart icon download csv"></div>
        </div>
          <Tab menu={{ pointing: true }} panes={panes} />
        </div>
      )



  }

const mapStateToProps = state => {
  const ageGroups = state.getIn(['data', 'items', 'ageGroup']);
  const genders = state.getIn(['data', 'items', 'gender']);
  const filters = state.getIn(['indicator', 'filters'])
  const methodOfEnforcements=state.getIn(['data','items','methodOfEnforcement'])
  const population=state.getIn(['indicator','women','population', 'data'])
  const distribution=state.getIn(['indicator','women','distribution', 'data'])
  return {
    filters,
    genders,
    methodOfEnforcements,
    ageGroups,
    population,
    distribution
  }
}

const mapActionCreators = {};

export default connect(mapStateToProps, mapActionCreators)(ChartSection);
