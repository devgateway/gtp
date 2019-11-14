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
import './agricutureIndex.scss'

import {ByAgeBar,ByAgeAndYearLine, ByMethodOfEnforcementBar,ByMethodOfEnforcementLine} from './AgricutureIndexCharts'

const  Filters=({indexTypes,filters,onChange, options})=>{
  const indexTypeSelection = filters && filters.getIn(['aoi', 'indexType'])? filters.getIn(['aoi', 'indexType']).toJS(): []

  return (<div className="indicator chart filter  women">
      <div className="filter item">
        <CustomFilterDropDown  options={items2options(indexTypes)}  onChange={s => {onChange([ 'filters', 'aoi', 'indexType'], s,['AOI'])}}
          selected={indexTypeSelection} text={<FormattedMessage id = "indicators.aoi.indexType" defaultMessage = "Index Type"  > </FormattedMessage>} />
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

        <div className="indicator chart aoi title ">
          <p>
            <FormattedMessage id="inidicators.chart.aoi.title" defaultMessage="The Agriculture Orientation Index for Government Expenditures"></FormattedMessage>
          </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
        </div>
        <div className="indicator chart aoi description">
          <p>
            <FormattedMessage id="inidicators.chart.aoi.description" defaultMessage="Analysis of public expenditure in support of agriculture and food in Senegal, 2010-2015 published by the FAO. The financial capacity of Senegalese farmers and their access to credit."></FormattedMessage>
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
  const indexTypes = state.getIn(['data', 'items', 'indexType']);
  const data=state.getIn(['indicator','aoi', 'data'])

  return {
    filters,
    indexTypes,
    data
  }
}

const mapActionCreators = {};

export default connect(mapStateToProps, mapActionCreators)(ChartSection);
