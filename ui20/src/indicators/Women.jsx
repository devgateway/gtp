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
import './women.scss'

import {ByAgeBar,ByAgeAndYearLine, ByMethodOfEnforcementBar,ByMethodOfEnforcementLine} from './WomenCharts'

const  Filters=({genders,ageGroups,methodOfEnforcements,filters,onChange, options})=>{

  const genderSelection = filters && filters.getIn(['women', 'gender'])? filters.getIn(['women', 'gender']).toJS(): []
  const ageSelection = filters && filters.getIn(['women', 'awGroup'])? filters.getIn(['women', 'awGroup']).toJS(): []
  const methodOfEnforcementsSelection = filters && filters.getIn(['women', 'awGroup'])? filters.getIn(['women', 'awGroup']).toJS(): []


  return (<div className="indicator chart filter  women">
      <div className="filter item">
        <CustomFilterDropDown disabled={!options.gender} options={items2options(genders)}  onChange={s => {onChange([ 'filters', 'women', 'gender'], s,['WOMEN'])}} selected={genderSelection} text={<FormattedMessage id = "indicators.filter.gender" defaultMessage = "Gender"  > </FormattedMessage>} />
      </div>

       <div className="filter item">
        <CustomFilterDropDown disabled={!options.methodOfEnforcement} options={items2options(methodOfEnforcements)}  onChange={s => {onChange([ 'filters', 'women', 'awGroup'], s,['WOMEN'])}} selected={methodOfEnforcementsSelection} text={<FormattedMessage id = "indicators.filter.enforcement.method" defaultMessage = "Enforcement Method"  > </FormattedMessage>} />
      </div>

      <div className="filter item">
        <OptionList disabled={!options.age} options={items2options(ageGroups)}  onChange={s => {onChange([ 'filters', 'women', 'awGroup'], s,['WOMEN'])}} selected={ageSelection} text={<FormattedMessage id = "indicators.filter.ageGroup" defaultMessage = "Age Group"  > </FormattedMessage>} />
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
            <div className="indicators chart women">
              <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:true}}></Filters>
              <ByAgeBar  {...props} data={props.population}></ByAgeBar>
            </div>,
       },
       {
         menuItem:  { key: 'line', icon: '', content: 'Female Progression' },
         render: () =><div className="indicators chart women">
               <Filters {...props} options={{gender:false, age:true,methodOfEnforcement:true}}></Filters>
               <ByAgeAndYearLine  data={props.population} {...props}></ByAgeAndYearLine>
             </div>,

       },
       {
         menuItem:  { key: 'bar', icon: '', content: 'By Method '+(lastetYear?'('+lastetYear+')':'') },
         render: () =><div className="indicators chart women">
               <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:true}}></Filters>
               <ByMethodOfEnforcementBar {...props} data={props.distribution}></ByMethodOfEnforcementBar>
             </div>,

       },
       {
         menuItem:  { key: 'line', icon: '', content: 'Female Progression' },
         render: () =><div className="indicators chart women">
               <Filters {...props} options={{gender:false, age:true,methodOfEnforcement:true}}></Filters>
               <ByMethodOfEnforcementLine {...props} data={props.distribution}></ByMethodOfEnforcementLine>
             </div>,

       }
     ]
    return (
        <div className="indicator.chart.container">

        <div className="indicator chart women title ">
          <p>
            <FormattedMessage id="inidicators.chart.women.title" defaultMessage="Women in the Agricultural sector"></FormattedMessage>
          </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
        </div>
        <div className="indicator chart women description">
          <p>
            <FormattedMessage id="inidicators.chart.women.description" defaultMessage="Measuring women's access to land through the percentage of men and women (aged 15-49) who solely own land which is legally registered to their name."></FormattedMessage>
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
