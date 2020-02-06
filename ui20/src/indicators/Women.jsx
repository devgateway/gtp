import './poverty.scss'
import 'rc-slider/assets/index.css'

import ReactDOM from 'react-dom';
import {connect} from 'react-redux';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage,injectIntl} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown,OptionList} from './Components'
import Plot from 'react-plotly.js';
import Slider, {Range} from 'rc-slider';
import {Dropdown,Grid,Image,Rail,Ref,Segment,Sticky} from 'semantic-ui-react'
import { Tab, Label } from 'semantic-ui-react'
import {items2options} from './DataUtil'
import './women.scss'
import  {getWomenDistributionByGroup, getWomebHistoricalDistribution} from './DataUtil'
import {BarChart,LineChart} from './WomenCharts'
import messages from '../translations/messages'

const  Filters=injectIntl(({intl,genders,ageGroups,methodOfEnforcements,filters,onChange, options})=>{
  const genderSelection = filters && filters.getIn(['women', 'gender'])? filters.getIn(['women', 'gender']).toJS(): []
  const ageSelection = filters && filters.getIn(['women', 'ageGroup'])? filters.getIn(['women', 'ageGroup']).toJS(): []
  const methodOfEnforcementsSelection = filters && filters.getIn(['women', 'methodOfEnforcement'])? filters.getIn(['women', 'methodOfEnforcement']).toJS(): []
  return (<div className="indicator chart filter  women">
            <div className="filter item">
              <CustomFilterDropDown disabled={!options.gender} options={items2options(genders,intl)}
              onChange={s => {onChange([ 'filters', 'women', 'gender'], s,['WOMEN'])}}
              selected={genderSelection}
              text={<FormattedMessage id = "indicators.filter.gender"
              defaultMessage = "Gender">
              </FormattedMessage>} />
            </div>
             <div className="filter item">
              <CustomFilterDropDown disabled={!options.methodOfEnforcement}
               options={items2options(methodOfEnforcements,intl)}
               onChange={s => {onChange([ 'filters', 'women', 'methodOfEnforcement'], s,['WOMEN'])}}
               selected={methodOfEnforcementsSelection}
               text={<FormattedMessage id = "indicators.filter.enforcement_method" defaultMessage = "Enforcement Method">
               </FormattedMessage>} />
            </div>
            <div className="filter item">
              <OptionList disabled={!options.age} options={items2options(ageGroups,intl).sort((a,b)=>a.key -b.key)}
               onChange={s => {onChange([ 'filters', 'women', 'ageGroup'], s,['WOMEN'])}}
               selected={ageSelection}
               text={<FormattedMessage id="indicators.filter.age_group" defaultMessage = "Age Group">
               </FormattedMessage>} />
            </div>
    </div>)
})

const ChartSection = injectIntl((props)=>{
  const {population=[], onExport, intl} = props
  const years = Array.from(new Set(population.map(r => r.year))).sort()
  const maxYear=years.pop()
    const byAgePanes=[
      {
        menuItem:  { key: 'bar', icon: '', content:`${props.intl.formatMessage(messages.indicator_women_chart_distribution_by_gender,{year:maxYear})}`},
        render: () =>
           <div className="indicators chart women">
             <Filters {...props} options={{gender:true, age:true,methodOfEnforcement:false}}></Filters>
             <div className="chart container">
              {population.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted>No data available</Label>:<BarChart
              yLegend={props.intl.formatMessage(messages.age)}
              xLegend={props.intl.formatMessage(messages.percent)}

              {...getWomenDistributionByGroup(props.population,props.intl)}></BarChart>}
             </div>
           </div>,
      },
      {
        menuItem:  { key: 'line', icon: '', content:  `${props.intl.formatMessage(messages.indicator_women_chart_distribution_historical)}` },
        render: () =><div className="indicators chart women">
              <Filters {...props} options={{gender:false, age:true ,methodOfEnforcement:false}}></Filters>
              <div className="chart container">
              {population.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted>No data available</Label>:<LineChart   {...getWomebHistoricalDistribution(props.population,props.intl)}/>}

              </div>

            </div>,

      }]

      const byMethodPanes=[
        {
          menuItem:  { key: 'bar', icon: '', content:`${props.intl.formatMessage(messages.indicator_women_chart_distribution_by_enforcement_method,{year:maxYear})}` },
          render: () =><div className="indicators chart women">
                <Filters {...props} options={{gender:true, age:false,methodOfEnforcement:true}}></Filters>
                <div className="chart container">

                {population.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted>No data available</Label>:<BarChart
                yLegend={props.intl.formatMessage(messages.methodOfEnforcement)}
                xLegend={props.intl.formatMessage(messages.percent)}


                {...getWomenDistributionByGroup(props.distribution,props.intl)}></BarChart>}

                </div>
              </div>,

        },
        {
          menuItem:  { key: 'line', icon: '', content: `${props.intl.formatMessage(messages.indicator_women_chart_distribution_by_enforcement_historical)}`  },
          render: () =><div className="indicators chart women">
                <Filters {...props} options={{gender:false, age:false,methodOfEnforcement:true}}></Filters>
                <div className="chart container">

                {population.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted>No data available</Label>:<LineChart  key="ByMethodOfEnforcementLine"  {...getWomebHistoricalDistribution(props.distribution,props.intl)}></LineChart>}

                </div>
              </div>,

        }]

    const panes = [
      {
        menuItem:  { key: 'bar', icon: '', content: props.intl.formatMessage(messages.indicator_women_chart_distribution_by_age_tab_title)},
        render: () =><Tab class="sub tab" key="byAge" menu={{ pointing: true }} panes={byAgePanes}/>,

      },
      {
        menuItem:  { key: 'line', icon: '', content: props.intl.formatMessage(messages.indicator_women_chart_distribution_by_method_tab_title) },
        render: () =><Tab class="sub tab" key="byAge" menu={{ pointing: true }} panes={byMethodPanes}/>,

      },
    ]
    return (
        <div className="indicator.chart.container" id="anchor.indicator.global.women.short">

        <div className="indicator chart women title ">
          <p>
            <FormattedMessage id="indicators.chart.women.title" defaultMessage="Women in the Agricultural sector"></FormattedMessage>
          </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
        </div>
        <div className="indicator chart women description">
          <p>
            <FormattedMessage id="indicators.chart.women.description" defaultMessage="Proportion of total agricultural population with ownership or secure rights over agricultural land, by sex; and (b) share of women among owners or rights-bearers of agricultural land, by type of tenure."></FormattedMessage>
          </p>
          <div className="indicator chart icon download xls" onClick={e=>onExport('WOMEN', 'XLS',intl.locale)}></div>
          <div className="indicator chart icon download png"></div>
          <div className="indicator chart icon download csv" onClick={e=>onExport('WOMEN', 'CSV',intl.locale)}></div>
        </div>
          <Tab key="women" menu={{ pointing: true }} panes={panes} />

          <div className="source"><span className="source label"> <FormattedMessage id="indicators.source.label" defaultMessage="Source :"></FormattedMessage></span> Source place holder.</div>
        </div>
      )
})

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
