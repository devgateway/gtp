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
import {items2options,getAOIsubsidies, getAOItotalBudget} from './DataUtil'
import './agricutureIndex.scss'
import {injectIntl} from 'react-intl';
import messages from '../translations/messages'


import {BarChart, LineChart} from './AgricutureIndexCharts'


const  BudgetFilters=injectIntl(({intl,indexTypes1,filters,onChange, options})=>{
  const indexTypeSelection = filters && filters.getIn(['aoi', 'budget','indexType'])? filters.getIn(['aoi', 'budget','indexType']).toJS(): []

  return (<div className="indicator chart filter  aoi">
      <div className="filter item">
        <CustomFilterDropDown  options={items2options(indexTypes1,intl)}  onChange={s => {onChange([ 'filters', 'aoi','budget', 'indexType'], s,['BUDGET'])}}
          selected={indexTypeSelection} text={<FormattedMessage id = "indicators.filters.indexType" defaultMessage = "Index Type"  > </FormattedMessage>} />
      </div>


    </div>)
})

const  SubsidiesFilters=injectIntl(({intl,indexTypes2,filters,onChange, options})=>{
  const indexTypeSelection = filters && filters.getIn(['aoi','subsidies', 'indexType',])? filters.getIn(['aoi','subsidies','indexType']).toJS(): []

  return (<div className="indicator chart filter  aoi">
      <div className="filter item">
        <CustomFilterDropDown  options={items2options(indexTypes2,intl)}  onChange={s => {onChange([ 'filters', 'aoi','subsidies', 'indexType'], s,['SUBSIDIES'])}}
          selected={indexTypeSelection} text={<FormattedMessage id = "indicators.filters.indexType" defaultMessage = "Index Type"  > </FormattedMessage>} />
      </div>


    </div>)
})



const ChartSection = injectIntl(( props)=>{

  const panes=[
      {

        menuItem:  { key: 'bar', icon: '', content: `${props.intl.formatMessage(messages.indicator_aoi_total_public_budget)}` },
        render: () =>
           <div className="indicators chart aoi">
             <BudgetFilters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></BudgetFilters>
             <div className="chart container"><BarChart  {...getAOItotalBudget(props.budget, props.intl)}></BarChart></div>
           </div>,
      },

      {
        menuItem:  { key: 'line', icon: '', content: `${props.intl.formatMessage(messages.indicator_aoi_composition_of_subsidies)}` },
        render: () =><div className="indicators chart aoi">
              <SubsidiesFilters {...props} options={{gender:false, age:true ,methodOfEnforcement:false}}></SubsidiesFilters>
              <div className="chart container"><BarChart   {...getAOIsubsidies(props.subsidies,props.intl)}/></div>
            </div>,

      }]


    return (
        <div className="indicator chart container" id="anchor.indicator.global.aoi.short">

        <div className="indicator chart aoi title ">
          <p>
            <FormattedMessage id="inidicators.chart.aoi.title" defaultMessage="The Agriculture Orientation Index for Government Expenditures"></FormattedMessage>
          </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
        </div>
        <div className="indicator chart aoi description">
          <p>
            <FormattedMessage id="inidicators.chart.aoi.description" defaultMessage="The agriculture orientation index for government expenditures"></FormattedMessage>
          </p>
          <div className="indicator chart icon download xls"></div>
          <div className="indicator chart icon download png"></div>
          <div className="indicator chart icon download csv"></div>
        </div>

          <Tab menu={{ pointing: true }} panes={panes} />

        </div>
      )
    })

const mapStateToProps = state => {
  const filters = state.getIn(['indicator', 'filters'])
  const indexTypes1 = state.getIn(['data', 'items', 'indexType/1']);
  const indexTypes2 = state.getIn(['data', 'items', 'indexType/2']);
  const subsidies=state.getIn(['indicator','aoi', 'data','subsidies'])
  const budget=state.getIn(['indicator','aoi', 'data','budget'])


  return {
    filters,
    indexTypes1,
    indexTypes2,
    subsidies,
    budget
  }
}

const mapActionCreators = {};

export default connect(mapStateToProps, mapActionCreators)(ChartSection);
