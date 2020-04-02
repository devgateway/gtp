import './poverty.scss'
import 'rc-slider/assets/index.css'
import React, {Component, createRef, useState,useEffect} from 'react'
import ReactDOM from 'react-dom';
import {connect} from 'react-redux';
import {FormattedMessage,injectIntl} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown,OptionList} from './Components'

import Slider, {Range} from 'rc-slider';
import {Dropdown,Grid,Image,Rail,Ref,Segment,Sticky} from 'semantic-ui-react'
import { Tab, Label } from 'semantic-ui-react'
import {items2options} from './DataUtil'
import './women.scss'
import  {getWomenDistributionByGroup, getWomenHistoricalDistribution} from './DataUtil'
import {BarChart,LineChart} from './WomenCharts'
import messages from '../translations/messages'
import {PngExport} from './Components'

const  Filters=injectIntl(({intl,genders,ageGroups,methodOfEnforcements,filters,onChange, options})=>{
  const genderSelection = filters && filters.getIn(['women', 'gender'])? filters.getIn(['women', 'gender']).toJS(): []
  const ageSelection = filters && filters.getIn(['women', 'ageGroup'])? filters.getIn(['women', 'ageGroup']).toJS(): []
  const methodOfEnforcementsSelection = filters && filters.getIn(['women', 'methodOfEnforcement'])? filters.getIn(['women', 'methodOfEnforcement']).toJS(): []

  return (<div className="indicator chart filter  women">
          {
            options.gender==true&&
             <div className="filter item">
              <CustomFilterDropDown
              options={items2options(genders,intl)}
              onChange={s => {onChange([ 'filters', 'women', 'gender'], s,['WOMEN'])}}
              selected={genderSelection}
              text={<FormattedMessage id = "indicators.filters.gender"
              defaultMessage = "Gender">
              </FormattedMessage>} />
            </div>
          }
          {
            options.methodOfEnforcement==true &&
             <div className="filter item">
              <CustomFilterDropDown
               options={items2options(methodOfEnforcements,intl)}
               onChange={s => {onChange([ 'filters', 'women', 'methodOfEnforcement'], s,['WOMEN'])}}
               selected={methodOfEnforcementsSelection}
               text={<FormattedMessage id = "indicators.filters.enforcement_method" defaultMessage = "Enforcement Method">
               </FormattedMessage>} />
            </div>
          }
          {
            options.ageGroup==true &&
              <div className="filter item">
               <OptionList
                options={items2options(ageGroups,intl).sort((a,b)=>a.key -b.key)}
                onChange={s => {onChange([ 'filters', 'women', 'ageGroup'], s,['WOMEN'])}}
                selected={ageSelection}
                text={<FormattedMessage id="indicators.filters.age_group" defaultMessage = "Age Group"></FormattedMessage>} />
              </div>
          }
    </div>)
})

const ageSortFunction=(a,b)=>{

  const aValue=a.substring(a.length-2)
  const bValue=b.substring(b.length-2)

  if(aValue< bValue) { return -1; }
    if(aValue > bValue) { return 1; }
    return 0;
}

const ChartSection = injectIntl((props)=>{
  const {population=[], onExport, intl, metadata} = props

  const intro=metadata? (props.intl.locale=='fr')?metadata.introFr:metadata.intro:null
  const ansdLink=metadata?metadata.ansdLink:null
  const source=metadata?metadata.source:null

  const [tab,setTab]=useState(0)
  const [subTab,setSubTab]=useState(0)

  const [options,setOptions]=useState({gender:false, ageGroup:false ,methodOfEnforcement:false})

  useEffect(() => {
    debugger;
    switch (tab) {
      case 0:
        switch (subTab) {
          case 0:
            setOptions({gender:true, ageGroup:true ,methodOfEnforcement:false})
            break
          case 1:
            setOptions({gender:false, ageGroup:true ,methodOfEnforcement:false})
            break
        }
        break;
        case 1:
          switch (subTab) {
            case 0:
              setOptions({gender:true, ageGroup:false ,methodOfEnforcement:true})
              break
            case 1:
              setOptions({gender:false, ageGroup:false ,methodOfEnforcement:true})
              break
          }
          break;

    }

  },[tab,subTab]);


  const years = Array.from(new Set(population.map(r => r.year))).sort()
  const maxYear=years.pop()

  const byAgePanes=[
      {
        menuItem:  { key: 'bar', icon: '', content:`${props.intl.formatMessage(messages.indicator_women_chart_distribution_by_gender,{year:maxYear})}`},
        render: () =>
           <div className="indicators chart women">
             <Filters {...props} options={options}></Filters>
             <div className="chart container">
              {population.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted>
              <FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:
              <BarChart groupMode="stacked"
                yLegend={props.intl.formatMessage(messages.age)}
                xLegend={props.intl.formatMessage(messages.percent)}
               {...getWomenDistributionByGroup(props.population,props.intl,'grouped',ageSortFunction)}>
               </BarChart>}
             </div>
           </div>,
      },
      {
        menuItem:  { key: 'line', icon: '', content:  `${props.intl.formatMessage(messages.indicator_women_chart_distribution_historical)}` },
        render: () =><div className="indicators chart women">
              <Filters {...props} options={options}></Filters>
              <div className="chart container">
              {population.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted>
              <FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:

              <LineChart   {...getWomenHistoricalDistribution(props.population,props.intl,ageSortFunction)}/>}

              </div>

            </div>,

      }]
  const byMethodPanes=[
        {
          menuItem:  { key: 'bar', icon: '', content:`${props.intl.formatMessage(messages.indicator_women_chart_distribution_by_enforcement_method,{year:maxYear})}` },
          render: () =><div className="indicators chart women">
                <Filters {...props} options={options}></Filters>
                <div className="chart container">

                {population.length == 0?
                <Label   ribbon="right" className="no data centered" basic color="olive" inverted>
                  <FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:
                  <BarChart
                    yLegend={props.intl.formatMessage(messages.methodOfEnforcement)}
                    xLegend={props.intl.formatMessage(messages.percent)}
                    {...getWomenDistributionByGroup(props.distribution,props.intl)}>
                    </BarChart>
                }
                </div>
              </div>,

        },
        {
          menuItem:  { key: 'line', icon: '', content: `${props.intl.formatMessage(messages.indicator_women_chart_distribution_by_enforcement_historical)}`  },
          render: () =><div className="indicators chart women">
                <Filters {...props} options={options}></Filters>
                <div className="chart container">

                {population.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted><FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:
                <LineChart  key="ByMethodOfEnforcementLine"  {...getWomenHistoricalDistribution(props.distribution,props.intl)}></LineChart>}

                </div>
              </div>,

        }]

  const panes = [
      {
        menuItem:  { key: 'bar', className:"parent", icon: '', content: props.intl.formatMessage(messages.indicator_women_chart_distribution_by_age_tab_title)},
        render: () =><Tab onTabChange={(a,b)=>{
          debugger
          setSubTab(b.activeIndex)
        }} class="sub tab" key="byAge" menu={{ pointing: true }} panes={byAgePanes}/>,

      },
      {
        menuItem:  { key: 'line',  className:"parent", icon: '', content: props.intl.formatMessage(messages.indicator_women_chart_distribution_by_method_tab_title) },
        render: () =><Tab onTabChange={(a,b)=>{
          debugger
          setSubTab(b.activeIndex)
        }} class="sub tab" key="byAge" menu={{ pointing: true }} panes={byMethodPanes}/>,

      },
    ]
    return (
        <div className="indicator chart section" id="anchor.indicator.global.women.short">
        <div className="png exportable">

          <div className="indicator chart women title">
          <img className="sdg icon" src='/sdg/5.svg' width="48px" height="48px"/>

            <p>
              <FormattedMessage id="indicators.chart.women.title" defaultMessage="Women in the Agricultural sector"></FormattedMessage>
            </p>
            <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
          </div>
          <div className="indicator chart women description">
            <p>
              {intro}
            </p>
            <div className="indicator chart icon group">

              <div className="indicator chart icon download xls" onClick={e=>onExport('WOMEN', 'XLS',intl.locale,options)}></div>

              <PngExport name={intl.formatMessage({id:'indicators.chart.women.title'})} id="anchor.indicator.global.women.short" filters={['filter','parent','item','download']} includes={['active']}/>

              <div className="indicator chart icon download csv" onClick={e=>onExport('WOMEN', 'CSV',intl.locale,options)}></div>
            </div>

          </div>

            <Tab onTabChange={(a,b)=>{
                debugger;
                setTab(b.activeIndex)
            }} key="women" menu={{ pointing: true }} panes={panes} />

            <div className="source"><span className="source label"> <FormattedMessage id="data.fields.source_label" defaultMessage="Source :"></FormattedMessage></span> {source?source:<FormattedMessage id="data.fields.source_undefined" defaultMessage="Not specified"></FormattedMessage>} </div>
          </div>
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
