import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown} from './Components'
import Plot from 'react-plotly.js';
import './poverty.scss'
import {Dropdown,Grid,Image,Rail,Ref,Segment,Sticky} from 'semantic-ui-react'
import { Slider } from "react-semantic-ui-range";





const gender2options = (genders) => genders
  ? genders.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

const activity2options = (activities) => activities
  ? activities.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []


const age2options = (activities) => activities
  ? activities.sort((c1, c2) => c1.id - c2.id).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []


export const OptionList = ({options, selected, onChange, text}) => {

  const updateSelection = (key) => {
      const newSelection = selected.slice(0)
      if (newSelection.indexOf(key) > -1) {
        newSelection.splice(newSelection.indexOf(key), 1);
      } else {
        newSelection.push(key)
      }
      onChange(newSelection)
    }

  const getChecked = (key) => {
    debugger;
    return selected.indexOf(key) > -1
  }


  return (<div className="indicator filter options age">
            <p>{text}</p>
            {options.map((a)=>{
                return <div onClick={e=>updateSelection(a.key)} className={`item ${getChecked(a.key)?'active':''}`}><div className="checkbox"></div><div className="label">{a.text}</div></div>
            })}
          </div>)

}



  const settings = {
             start: [0,100],
             min: 0,
             max: 100,
             step: 5
                         };
const Pooverty = (props) => {

  const {filters,onChange}=props
  const genderSelection=props.filters&&filters.getIn(['poverty','gender'])?filters.getIn(['poverty','gender']).toJS():[]
  const activitySelection=props.filters&&filters.getIn(['poverty','activity'])?filters.getIn(['poverty','activity']).toJS():[]
  const ageGroupsSelection=props.filters&&filters.getIn(['poverty','ageGroup'])?filters.getIn(['poverty','ageGroup']).toJS():[]

  console.log(props.ageGroups)

  return (<div className="indicator-chart-container">
    <div className="indicator chart title poverty">
      <p>
        <FormattedMessage id="inidicators.chart.poverty.title" defaultMessage="Proportion of population below the international poverty line"></FormattedMessage>
      </p>
      <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
    </div>
    <div className="indicator chart description poverty">
      <p>
        <FormattedMessage id="inidicators.chart.poverty.description" defaultMessage="Proportion of population below the international poverty line is defined as the percentage of the population living on less than $1.90 a day at 2011 international prices. "></FormattedMessage>
      </p>
      <div className="indicator chart icon download xls"></div>
      <div className="indicator chart icon download png"></div>
      <div className="indicator chart icon download csv"></div>
    </div>
    <div className="indicator chart filter  poverty">
    <CustomFilterDropDown onChange={s=>{onChange(['filters','poverty','gender'],s)}}    selected={genderSelection}      text={<FormattedMessage id = "indicators.filter.gender" defaultMessage = "Gender"> </FormattedMessage>} options={gender2options(props.genders)}></CustomFilterDropDown>
    <CustomFilterDropDown onChange={s=>{onChange(['filters','poverty','activity'],s)}}  selected={activitySelection}   text={<FormattedMessage id = "indicators.filter.activity" defaultMessage = "Profesional Activity"></FormattedMessage>} options={activity2options(props.activities)}></CustomFilterDropDown>

    <div className="slider level">
      <p><FormattedMessage id="inidicators.filter.slider.level" defaultMessage="Poverty Level"/></p>
      <Slider onChange={e=>{
          debugger;
        }} multiple color="red" settings={settings} />
    </div>

    <OptionList options={age2options(props.ageGroups)} selected={ageGroupsSelection} onChange={s=>{onChange(['filters','poverty','ageGroup'],s)}} text={<FormattedMessage id="inidicators.filter.option.age" defaultMessage="Age Range"/>}></OptionList>




    </div>


    <Plot data={[
        {
          x: [
            1, 2, 3
          ],
          y: [
            2, 6, 3
          ],
          type: 'scatter',
          mode: 'lines+points',
          marker: {
            color: 'red'
          }
        }, {
          type: 'bar',
          x: [
            1, 2, 3
          ],
          y: [2, 5, 3]
        }
      ]} layout={{
        width: 1000,
        height: 500,
        title: 'A Fancy Plot'
      }}/> < /div>)

}

export default Pooverty
