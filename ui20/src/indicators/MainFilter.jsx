import "./indicators.scss"
import ReactDOM from 'react-dom';
import React from 'react'
import {CustomFilterDropDown} from './Components'
import {PngExport} from './Components'
import {FormattedMessage,injectIntl} from 'react-intl';
import {items2options} from './DataUtil'
const region2options = (regions) => regions
  ? regions.sort((r1, r2) => r1.name.localeCompare(r2.name)).map(r => ({'key': r.id, 'text': r.name, 'value': r.id}))
  : []

/*
<div className="icon download csv"></div>
<div className="icon download json"></div>

*/
const MainFilter =injectIntl( ({intl,regions, years, crops, globalFilters,onChange,onApply,onReset, onExport}) => {



  return (<div className="core-filters">
    <div className="main filter nav">
      <div className="filter nav item title">
        <FormattedMessage id="indicators.filters.title" defaultMessage="Indicator Filter"></FormattedMessage>
      </div>
      <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('year',s)}  selected={globalFilters&&globalFilters.get('year')?globalFilters.get('year').toJS():[]}
        text={<FormattedMessage id = "indicators.filters.year" defaultMessage = "Campaign/Years" > </FormattedMessage>}
        options={items2options(years,intl)}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('region',s)} selected={globalFilters&&globalFilters.get('region')?globalFilters.get('region').toJS():[]}
         text={<FormattedMessage id = "indicators.filters.regions" defaultMessage = "Regions" > </FormattedMessage>}
        options={items2options(regions,intl)}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item">
        <CustomFilterDropDown onChange={s=>onChange('crop',s)} selected={globalFilters&&globalFilters.get('crop')?globalFilters.get('crop').toJS():[]}
        text={<FormattedMessage id = "indicators.filters.crops" defaultMessage = "Crop Type" > </FormattedMessage>}
        options={items2options(crops,intl)}></CustomFilterDropDown>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item btn" onClick={onApply}>
          <FormattedMessage id="indicators.filters.apply" defaultMessage="APPLY"></FormattedMessage>
      </div>
        <div className="filter nav separator"></div>
      <div className="filter nav item btn" onClick={onReset}>
        <FormattedMessage id="indicators.filters.reset" defaultMessage="Reset All"></FormattedMessage>
      </div>
      <div className="filter nav item download" >

        <div className="icon download xls" onClick={e=>onExport('ALL', 'XLS',intl.locale)}></div>
        <PngExport id="root" name="map_image"/>

      </div>

    </div>
  </div>)
})

export default MainFilter
