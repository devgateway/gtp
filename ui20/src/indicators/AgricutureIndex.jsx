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
import { Tab , Label} from 'semantic-ui-react'
import {items2options,getAOIsubsidies, getAOItotalBudget} from './DataUtil'
import './agricutureIndex.scss'
import {injectIntl} from 'react-intl';
import messages from '../translations/messages'
import {PngExport} from './Components'

import {BarChart, LineChart} from './AgricutureIndexCharts'


const  BudgetFilters=injectIntl(({intl,indexTypes1,filters,onChange, options})=>{
  const indexTypeSelection = filters && filters.getIn(['aoi', 'budget','indexType'])? filters.getIn(['aoi', 'budget','indexType']).toJS(): []

  return (<div className="indicator chart filter  aoi">
      <div className="filter item">
        <CustomFilterDropDown  options={items2options(indexTypes1,intl)}  onChange={s => {onChange([ 'filters', 'aoi','budget', 'indexType'], s,['BUDGET'])}}
          selected={indexTypeSelection} text={<FormattedMessage id = "indicators.filters.index_type" defaultMessage = "Index Type"  > </FormattedMessage>} />
      </div>


    </div>)
})

const  SubsidiesFilters=injectIntl(({intl,indexTypes2,filters,onChange, options})=>{
  const indexTypeSelection = filters && filters.getIn(['aoi','subsidies', 'indexType',])? filters.getIn(['aoi','subsidies','indexType']).toJS(): []

  return (<div className="indicator chart filter  aoi">
      <div className="filter item">
        <CustomFilterDropDown  options={items2options(indexTypes2,intl)}  onChange={s => {onChange([ 'filters', 'aoi','subsidies', 'indexType'], s,['SUBSIDIES'])}}
          selected={indexTypeSelection} text={<FormattedMessage id = "indicators.filters.index_type" defaultMessage = "Index Type"  > </FormattedMessage>} />
      </div>


    </div>)
})



const ChartSection = injectIntl(( props)=>{
  const {onExport, intl,metadata}=props


  const intro=metadata? (props.intl.locale=='fr')?metadata.introFr:metadata.intro:null
  const ansdLink=metadata?metadata.ansdLink:null
  const source=metadata?metadata.source:null




  const panes=[
      {

        menuItem:  { key: 'bar', icon: '', content: `${props.intl.formatMessage(messages.indicator_aoi_total_public_budget)}` },
        render: () =>
           <div className="indicators chart aoi">
             <BudgetFilters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></BudgetFilters>
             <div className="chart container">
                  {props.budget == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted>
                  <FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage> </Label>: <BarChart  {...getAOItotalBudget(props.budget, props.intl)}></BarChart>}

            </div>
           </div>,
      },

      {
        menuItem:  { key: 'line', icon: '', content: `${props.intl.formatMessage(messages.indicator_aoi_composition_of_subsidies)}` },
        render: () =><div className="indicators chart aoi">
              <SubsidiesFilters {...props} options={{gender:false, age:true ,methodOfEnforcement:false}}></SubsidiesFilters>
              <div className="chart container ">

                {props.budget == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted><FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:
                <BarChart   {...getAOIsubsidies(props.subsidies,props.intl)}/>}

              </div>
            </div>,

      }]


    return (
        <div className="indicator chart section" id="anchor.indicator.global.aoi.short">
  <div className="png exportable">
        <div className="indicator chart aoi title ">
        <img className="sdg icon" src='/sdg/12.svg' width="48px" height="48px"/>

          <p>
            <FormattedMessage id="indicators.chart.aoi.title" defaultMessage="The Agriculture Orientation Index for Government Expenditures"></FormattedMessage>
          </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
        </div>
        <div className="indicator chart aoi description">
          <p>
          {intro}
          </p>

          <div className="indicator chart icon group">
            <div className="indicator chart icon download xls" onClick={e=>onExport('AOI', 'XLS',intl.locale)}></div>
            <PngExport name={intl.formatMessage({id:'indicators.chart.aoi.title'})} id="anchor.indicator.global.aoi.short" filters={['filter','parent','item','download']} includes={['active']}/>

            <div className="indicator chart icon download csv" onClick={e=>onExport('AOI', 'CSV',intl.locale)}></div>
          </div>
        </div>

          <Tab menu={{ pointing: true }} panes={panes} />

          <div className="source"><span className="source label"> <FormattedMessage id="data.field.source.label" defaultMessage="Source :"></FormattedMessage></span> {source?source:<FormattedMessage id="data.field.source.undefined" defaultMessage="Not specified"></FormattedMessage>} </div>
  </div>
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
