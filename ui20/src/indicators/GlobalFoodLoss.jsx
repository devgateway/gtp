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
import './globalFoodLoss.scss'

import {BarChart} from './GlobalFoodLossCharts'
import {getAverageProductionLossData} from './DataUtil'

const  Filters=({lossTypes,filters,onChange, options})=>{
  const lossTypesSelection = filters && filters.getIn(['food','lossType'])? filters.getIn(['food','lossType']).toJS(): []
  return (<div className="indicator chart filter  women">
    <div className="filter item">
      <CustomFilterDropDown  options={items2options(lossTypes)}  onChange={s => {onChange([ 'filters', 'food', 'lossType'], s,['FOOD'])}} selected={lossTypesSelection} text={<FormattedMessage id = "indicators.filter.losstype" defaultMessage = "Loss Type"  > </FormattedMessage>} />
    </div>
  </div>)
}




const ChartSection = ( props)=>{
  const {data=[]} = props
  const years = Array.from(new Set(data.map(r => r.year)))
  const maxYear=years.pop()

    const panes = [
       {
         menuItem:  { key: 'bar', icon: '', content: 'Average production loss  (%) '+maxYear },
         render: () =>
            <div className="indicators chart food">
              <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></Filters>
              <div className="chart container"><BarChart {...getAverageProductionLossData(props.data,'avgPercentage')}></BarChart></div>
            </div>,
       },
       {
         menuItem:  { key: 'bar', icon: '', content: 'Average quantity (in kg) per household '+(maxYear-1)+'/'+(maxYear) },
         render: () =>
            <div className="indicators chart food">
              <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></Filters>
              <div className="chart container"><BarChart {...getAverageProductionLossData(props.data,'avgKilograms')}></BarChart></div>
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
              <FormattedMessage id="inidicators.chart.food.description"
                defaultMessage="Global Food Loss Index (GFLI), measuring the total losses of ag. commodities from the production to the retail level. By 2030, halve
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
  const filters = state.getIn(['indicator', 'filters'])
  const lossTypes = state.getIn(['data', 'items', 'lossType']);
  const data = state.getIn(['indicator','food', 'data']);


  return {
    filters,
    lossTypes,
    data
    }
}

const mapActionCreators = {};


export default connect(mapStateToProps, mapActionCreators)(ChartSection);
