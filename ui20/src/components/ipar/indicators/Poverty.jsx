/* eslint-disable */
import './poverty.scss'
import 'rc-slider/assets/index.css'
import {connect} from 'react-redux';
import React, {Component} from 'react'
import {FormattedMessage,injectIntl} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown, RangeSlider} from './Components'
import {BarChart,LineChart} from './PovertyCharts'

import { Tab , Label} from 'semantic-ui-react'
import {getPovertyRegionalYearly,getPovertyRegionalStackedByPovertyLevel, getPovertyTimeLine, items2options} from './DataUtil'
import messages from '../../../translations/messages'
import './poverty.scss'
import {PngExport} from './Components'


const PovertyFitlers=injectIntl((props)=>{

  const {intl,filters, onChange,range={},genders=[],activities=[],ageGroups=[]} = props
  const genderSelection = filters && filters.getIn(['poverty', 'gender'])? filters.getIn(['poverty', 'gender']).toJS(): []
  const activitySelection = filters && filters.getIn(['poverty', 'activity'])? filters.getIn(['poverty', 'activity']).toJS() :[]
  const ageGroupsSelection = filters && filters.getIn(['poverty', 'ageGroup'])? filters.getIn(['poverty', 'ageGroup']).toJS() :[]

  //age and score limits
  const minAge =range.age?range.age.min:0
  const maxAge = range.age?range.age.max:0
  const minScore = range.score?range.score.min:0
  const maxScore = range.score?range.score.max:0
  //age and score selection
  const age= [filters.getIn(['poverty','minAge']),filters.getIn(['poverty','maxAge'])]
  const score=[ filters.getIn(['poverty','minScore']),filters.getIn(['poverty','maxScore'])]



  return (<div className="indicator chart filter poverty">

    <div className="filter item">
      <CustomFilterDropDown options={items2options(genders,intl)}  onChange={s => {onChange([ 'filters', 'poverty', 'gender'], s,['POVERTY'])}}
       selected={genderSelection} text={<FormattedMessage id = "indicators.filters.gender" defaultMessage = "Gender"  > </FormattedMessage>} />
    </div>

    <div className="filter item">
      <CustomFilterDropDown options={items2options(activities,intl)} onChange={s => {onChange(['filters', 'poverty', 'activity'], s,['POVERTY'])}}
       selected={activitySelection} text={<FormattedMessage id = "indicators.filters.activity" defaultMessage = "Professional Activity" > </FormattedMessage>} />
    </div>

    <div className="filter item">
      {<RangeSlider onChange={s => {
        onChange(['filters', 'poverty', 'minScore'],s[0])
        onChange(['filters', 'poverty', 'maxScore'],s[1],['POVERTY'])

      }} max={maxScore} min={minScore}  selected={score} text={<FormattedMessage id="indicators.filters.slider.score" defaultMessage="Score Range"/>}></RangeSlider>}
    </div>

    <div className="filter item">
      {<RangeSlider onChange={s => {
        onChange(['filters', 'poverty', 'minAge'],s[0])
        onChange(['filters', 'poverty', 'maxAge'],s[1],['POVERTY'])

      }} max={maxAge} min={minAge}  selected={age} text={<FormattedMessage id="indicators.filters.slider.age" defaultMessage="Age Range"/>}></RangeSlider>}
    </div>
  </div>)
})


const ChartSection = injectIntl((props)=>{
  return null
})


class Pooverty extends Component {
  render() {

    const {data=[],intl, onExport, metadata} = this.props
    const years = Array.from(new Set(data.map(r => r.year)))
    const maxYear=years.pop()

    const intro=metadata? (this.props.intl.locale=='fr')?metadata.introFr:metadata.intro:null
    const ansdLink=metadata?metadata.ansdLink:null
    const source=metadata?metadata.source:null



  const panes1 = [
      {
        menuItem:{ key: 'poverty_chart_1', icon: '', content:`${intl.formatMessage(messages.indicator_poverty_chart_by_region_and_year)}`},
        render: () =><div className="indicators chart poverty">
                      <PovertyFitlers {...this.props}/>
                        <div className="chart container">
                        {data.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted><FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:<BarChart {...getPovertyRegionalYearly(data,intl)}/>}

                          </div>
                      </div>

      },
      {
        menuItem:{ key: 'poverty_chart_2', icon: '', content:`${intl.formatMessage(messages.indicator_poverty_chart_by_poor_no_poor_rencet_year,{year:maxYear})}`},

        render: () => <div className="indicators chart poverty">
        <PovertyFitlers {...this.props} />
                        <div className="chart container">

                        {data.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted><FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:<BarChart    {...getPovertyRegionalStackedByPovertyLevel(data,intl)}/>}

                          </div>
                      </div>

      },
      {
        menuItem:{ key: 'poverty_chart_3', icon: '', content:`${intl.formatMessage(messages.indicator_poverty_chart_historical_by_region)}`},
        render: () =>  (<div className="indicators chart poverty" > <PovertyFitlers {...this.props}/><div className="chart container">

          {data.length == 0?<Label   ribbon="right" className="no data centered" basic color="olive" inverted><FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>:<LineChart {...getPovertyTimeLine(data,intl)}/>}

        </div></div>),
      }
    ]

    return (<div className="indicator chart section" id="anchor.indicator.global.population.short">
    <div className="png exportable">
      <div className="indicator chart title poverty ">

      <img className="sdg icon" src='/sdg/1.svg' width="48px" height="48px"/>

        <p>
          <FormattedMessage id="indicators.chart.poverty.title" defaultMessage="Proportion of population below the international poverty line."></FormattedMessage>
        </p>
          <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
      </div>
      <div className="indicator chart description poverty">
        <p>
        {intro}
        </p>
        <div className="indicator chart icon group">

          <div className="indicator chart icon download xls" onClick={e=>onExport('POVERTY', 'XLS',intl.locale)}></div>
          <PngExport name={intl.formatMessage({id:'indicators.chart.poverty.title'})} id="anchor.indicator.global.population.short" filters={['filter','item','download']} includes={['active']}/>
          <div className="indicator chart icon download csv" onClick={e=>onExport('POVERTY', 'CSV',intl.locale)}></div>
        </div>
      </div>

        <Tab key="poverty" menu={{ pointing: true }} panes={panes1} />

        <div className="source"><span className="source label"> <FormattedMessage id="data.fields.source_label" defaultMessage="Source :"></FormattedMessage></span> {source?source:<FormattedMessage id="data.fields.source_undefined" defaultMessage="Not specified"></FormattedMessage>} </div>

      </div>
      </div>
    )
  }

}

const mapStateToProps = state => {

  const filters = state.getIn(['indicator', 'filters'])
  const activities = state.getIn(['data', 'items', 'professionalActivity']);
  const genders = state.getIn(['data', 'items', 'gender']);
  const ageGroups = state.getIn(['data', 'items', 'ageGroup']);
  const range = state.getIn(['data', 'items', 'range']);
  const data=state.getIn(['indicator','poverty','data'])?state.getIn(['indicator','poverty','data']).toJS():[]
  return {filters,activities, genders, ageGroups, range,data}
}

const mapActionCreators = {};

export default connect(mapStateToProps, mapActionCreators)(Pooverty);
