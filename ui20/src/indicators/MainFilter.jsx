import "./indicators.scss"
import ReactDOM from 'react-dom';
import React from 'react'
import {CustomFilterDropDown} from './Components'
import {FormattedMessage} from 'react-intl';

const region2options = (regions) => regions
  ? regions.sort((r1, r2) => r1.name.localeCompare(r2.name)).map(r => ({'key': r.id, 'text': r.name, 'value': r.id}))
  : []

const crop2options = (crops) => crops
  ? crops.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

const year2options = (years) => years?years.map(y => ({'key': y.id, 'text': y.label, 'value': y.id})):[]

const MainFilter = ({regions, years, crops, globalFilters,onChange,onApply,onReset}) => {


  return (<div className="core-filters">
    <div className="main filter nav">
      <div className="filter nav item title">
        <FormattedMessage id="indicators.main_filter_title" defaultMessage="Indicator Filter"></FormattedMessage>
      </div>
      <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('year',s)}  selected={globalFilters&&globalFilters.get('year')?globalFilters.get('year').toJS():[]} text={<FormattedMessage id = "indicators.main.filter.year" defaultMessage = "Campain/Years" > </FormattedMessage>} options={year2options(years)}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('region',s)} selected={globalFilters&&globalFilters.get('region')?globalFilters.get('region').toJS():[]} text={<FormattedMessage id = "indicators.main.filter.regions" defaultMessage = "Regions" > </FormattedMessage>} options={region2options(regions)}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('crop',s)} selected={globalFilters&&globalFilters.get('crop')?globalFilters.get('crop').toJS():[]} text={<FormattedMessage id = "indicators.main.filter.crops" defaultMessage = "Crop Type" > </FormattedMessage>} options={crop2options(crops)}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item btn" onClick={onApply}>
          <FormattedMessage id="indicators.main.filter.apply" defaultMessage="APPLY"></FormattedMessage>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item btn" onClick={onReset}>
        <FormattedMessage id="indicators.main.filter.resert" defaultMessage="Reset All"></FormattedMessage>
      </div>
      <div className="filter nav item download" >

        <div className="icon download xls"></div>
        <div className="icon download png"></div>
        <div className="icon download csv"></div>
        <div className="icon download json"></div>

      </div>

    </div>
  </div>)
}

export default MainFilter
