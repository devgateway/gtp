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
import { Tab, Label } from 'semantic-ui-react'
import {items2options} from './DataUtil'
import './globalFoodLoss.scss'
import {BarChart} from './GlobalFoodLossCharts'
import {getAverageProductionLossData} from './DataUtil'
import {injectIntl} from 'react-intl';
import messages from '../translations/messages'



const  Filters=injectIntl(({intl,lossTypes,filters,onChange, options})=>{
  const lossTypesSelection = filters && filters.getIn(['food','lossType'])? filters.getIn(['food','lossType']).toJS(): []
  return (<div className="indicator chart filter  women">
    <div className="filter item">
      <CustomFilterDropDown  options={items2options(lossTypes,intl)}  onChange={s => {onChange([ 'filters', 'food', 'lossType'], s,['FOOD'])}} selected={lossTypesSelection} text={<FormattedMessage id = "indicators.filter.losstype" defaultMessage = "Loss Type"  > </FormattedMessage>} />
    </div>
  </div>)
})




const ChartSection = injectIntl(( props)=>{
  const {data=[], onExport, intl} = props
  const years = Array.from(new Set(data.map(r => r.year)))
  const maxYear=years.pop()

    const panes = [
       {
         menuItem:  { key: 'bar_production', icon: '', content:`${props.intl.formatMessage(messages.indicator_food_chart_average_production_loss,{year:maxYear})}` },
         render: () =>
            <div className="indicators chart food">
              <Filters {...props} options={ { gender:true, age:true,methodOfEnforcement:false } }></Filters>
              <div className="chart container">

  {data.length == 0?<Label    className="no data centered" basic inverted>No data available</Label>:  <BarChart
        yLegend={props.intl.formatMessage(messages.percent)}
        xLegend={props.intl.formatMessage(messages.crop_type)}

      label={(s)=>props.intl.formatNumber(s.value/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0})}

     {...getAverageProductionLossData(props.data,'avgPercentage', props.intl)}></BarChart>}

            </div>
            </div>,
       },
       {
         menuItem:  { key: 'bar_quantity', icon: '', content:`${props.intl.formatMessage(messages.indicator_food_chart_average_quantity,{year:maxYear})}` },
         render: () =>
            <div className="indicators chart food">
              <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></Filters>
              <div className="chart container">


              <BarChart
                yLegend={props.intl.formatMessage(messages.kg)}
                xLegend={props.intl.formatMessage(messages.crop_type)}
                label={(s)=>props.intl.formatNumber(s.value, {notation:'compact', minimumFractionDigits: 0,maximumFractionDigits: 0})}

              {...getAverageProductionLossData(props.data,'avgKilograms',props.intl)}></BarChart>}
              </div>
            </div>,
       }

     ]

    return (
        <div className="indicator.chart.container" id="anchor.indicator.global.food.short">
          <div className="indicator chart food title ">
            <p>
              <FormattedMessage id="indicators.chart.food.title" defaultMessage="Post-Harvest Loss"></FormattedMessage>
            </p>
            <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
          </div>
          <div className="indicator chart food description">
            <p>
              <FormattedMessage id="indicators.chart.food.description"
                defaultMessage="The destinations of agricultural production presents the different uses of the agricultural production included the losses.">
              </FormattedMessage>
            </p>
            <div className="indicator chart icon download xls" onClick={e=>onExport('FOOD', 'XLS',intl.locale)}></div>
            <div className="indicator chart icon download png"></div>
            <div className="indicator chart icon download csv" onClick={e=>onExport('FOOD', 'CSV',intl.locale)}></div>

          </div>

          <Tab menu={{ pointing: true }} panes={panes} />

          <div className="source"><span className="source label"> <FormattedMessage id="indicators.source.label" defaultMessage="Source :"></FormattedMessage></span> Source place holder.</div>


        </div>
      )
    })

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
