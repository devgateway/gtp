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
import {items2options} from './DataUtil'
import './women.scss'
import  {getWomenDistributionByGroup, getWomebHistoricalDistribution} from './DataUtil'
import {BarChart,LineChart} from './WomenCharts'

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
  const {population=[]} = props
  const years = Array.from(new Set(population.map(r => r.year))).sort()
  const maxYear=years.pop()


    const byAgePanes=[
      {
        menuItem:  { key: 'bar', icon: '', content: 'Distribution of the agricultural population by age group and gender '+(maxYear?'('+maxYear+')':'') },
        render: () =>
           <div className="indicators chart women">
             <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></Filters>
             <div className="chart container"><BarChart  {...getWomenDistributionByGroup(props.population)}></BarChart></div>
           </div>,
      },
      {
        menuItem:  { key: 'line', icon: '', content: 'Historical Female Distribution' },
        render: () =><div className="indicators chart women">
              <Filters {...props} options={{gender:false, age:true ,methodOfEnforcement:false}}></Filters>
              <div className="chart container"><LineChart   {...getWomebHistoricalDistribution(props.population)}/></div>
            </div>,

      }]

      const byMethodPanes=[
        {
          menuItem:  { key: 'bar', icon: '', content: 'Distribution of parcels by method of enforcement and gender '+(maxYear?'('+maxYear+')':'') },
          render: () =><div className="indicators chart women">
                <Filters {...props} options={{gender:true, age:false,methodOfEnforcement:true}}></Filters>
                <div className="chart container"><BarChart  {...getWomenDistributionByGroup(props.distribution)}></BarChart></div>
              </div>,

        },
        {
          menuItem:  { key: 'line', icon: '', content: 'Historical female distribution of parcels by method of enforcement' },
          render: () =><div className="indicators chart women">
                <Filters {...props} options={{gender:false, age:false,methodOfEnforcement:true}}></Filters>
                <div className="chart container"><LineChart  key="ByMethodOfEnforcementLine"  {...getWomebHistoricalDistribution(props.distribution)}></LineChart></div>
              </div>,

        }]

    const panes = [
      {
        menuItem:  { key: 'bar', icon: '', content: 'By Age '},
        render: () =><Tab class="sub tab" key="byAge" menu={{ pointing: true }} panes={byAgePanes}/>,

      },
      {
        menuItem:  { key: 'line', icon: '', content: 'By Method' },
        render: () =><Tab class="sub tab" key="byAge" menu={{ pointing: true }} panes={byMethodPanes}/>,

      },
      {
        menuItem:  { key: 'line', icon: '', content: 'By Other 1' },
        render: () =><div/>,

      },
      {
        menuItem:  { key: 'line', icon: '', content: 'By Other 2' },
        render: () =><div/>,

      }]
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
            <FormattedMessage id="inidicators.chart.women.description" defaultMessage="Measuring women's access to land through the percentage of men and women (aged 15-49) who solely own land which is legally registered to their name."></FormattedMessage>
          </p>
          <div className="indicator chart icon download xls"></div>
          <div className="indicator chart icon download png"></div>
          <div className="indicator chart icon download csv"></div>
        </div>
          <Tab key="women" menu={{ pointing: true }} panes={panes} />
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
