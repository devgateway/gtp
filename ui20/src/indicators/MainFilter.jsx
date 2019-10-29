import "./indicators.scss"
import ReactDOM from 'react-dom';
import React from 'react'
import {CustomFilterDropDown} from './Components'
import {FormattedMessage} from 'react-intl';

const region2options = (regions) => regions
  ? regions.sort((r1, r2) => r1.name.localeCompare(r2.name)).map(r => ({'key': r.code, 'text': r.name, 'value': r.id}))
  : []

const crop2options = (crops) => crops
  ? crops.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

const year2options = () => [2017, 2018, 2019].map(y => ({'key': y, 'text': y, 'value': y}))

const MainFilter = ({regions, crops, globalFilters,onChange,onApply,onReset}) => {


  return (<div className="core-filters">
    <div className="main filter nav">
      <div className="filter nav item title">
        <FormattedMessage id="indicators.main_filter_title" defaultMessage="Indicator Filter"></FormattedMessage>
      </div>
      <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('years',s)}  selected={globalFilters&&globalFilters.get('years')?globalFilters.get('years').toJS():[]} text={<FormattedMessage id = "indicators.main.filter.year" defaultMessage = "Campain/Years" > </FormattedMessage>} options={year2options()}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('regions',s)} selected={globalFilters&&globalFilters.get('regions')?globalFilters.get('regions').toJS():[]} text={<FormattedMessage id = "indicators.main.filter.regions" defaultMessage = "Regions" > </FormattedMessage>} options={region2options(regions)}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('crops',s)} selected={globalFilters&&globalFilters.get('crops')?globalFilters.get('crops').toJS():[]} text={<FormattedMessage id = "indicators.main.filter.crops" defaultMessage = "Crop Type" > </FormattedMessage>} options={crop2options(crops)}></CustomFilterDropDown>
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
