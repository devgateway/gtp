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
import AgriculturalPopulation from './AgriculturalPopulation'
const  Filters=({genders,ageGroups,methodOfEnforcements,filters,onChange})=>{

  const genderSelection = filters && filters.getIn(['women', 'gender'])? filters.getIn(['women', 'gender']).toJS(): []
  const ageSelection = filters && filters.getIn(['women', 'age'])? filters.getIn(['women', 'age']).toJS(): []
  const methodOfEnforcementsSelection = filters && filters.getIn(['women', 'methodOfEnforcement'])? filters.getIn(['women', 'methodOfEnforcement']).toJS(): []


  return (<div className="indicator chart filter  women">
      <div className="filter item">
        <CustomFilterDropDown options={items2options(genders)}  onChange={s => {onChange([ 'filters', 'women', 'gender'], s,['WOMEN'])}} selected={genderSelection} text={<FormattedMessage id = "indicators.filter.gender" defaultMessage = "Gender"  > </FormattedMessage>} />
      </div>
      <div className="filter item">
        <CustomFilterDropDown options={items2options(methodOfEnforcements)}  onChange={s => {onChange([ 'filters', 'women', 'methodOfEnforcement'], s,['WOMEN'])}} selected={methodOfEnforcementsSelection} text={<FormattedMessage id = "indicators.filter.enforcement.method" defaultMessage = "Enforcement Method"  > </FormattedMessage>} />

      </div>
      <div className="filter item">

        <OptionList options={items2options(ageGroups)}  onChange={s => {onChange([ 'filters', 'women', 'age'], s,['WOMEN'])}} selected={ageSelection} text={<FormattedMessage id = "indicators.filter.ageGroup" defaultMessage = "Age Group"  > </FormattedMessage>} />
      </div>

    </div>)
}


const ChartSection = ( props)=>{
    const panes = [
       {
         menuItem:  { key: 'bar', icon: '', content: 'Bar Chart' },
         render: () =>   <div className="indicators chart poverty">
         <Filters {...props}></Filters>
          <AgriculturalPopulation {...props}></AgriculturalPopulation>
      </div>,
       },
       {
         menuItem:  { key: 'pie', icon: '', content: 'Pie Chart' },
         render: () =>   <div className="indicators chart poverty"><Filters {...props}></Filters></div>,

       },
       {
         menuItem:  { key: 'line', icon: '', content: 'Line Chart' },
         render: () => <div className="indicators chart poverty"><Filters {...props}></Filters></div>,
       }
     ]
    return (
        <div className="indicator.chart.container">

        <div className="indicator chart title women">
          <p>
            <FormattedMessage id="inidicators.chart.women.title" defaultMessage="Women in the Agricultural sector"></FormattedMessage>
          </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
        </div>

        <div className="indicator chart description women">
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
  const data=state.getIn(['indicator','women','population', 'data'])


  return {
    filters,
    genders,
    methodOfEnforcements,
    ageGroups,
    data

  }

}

const mapActionCreators = {};

export default connect(mapStateToProps, mapActionCreators)(ChartSection);
