import "./indicators.scss"

import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import React, {Component} from 'react'
import {apply, exportData, reset, updateFilter, updateGlobalFilter} from '../modules/Indicator'

import MainFilter from './MainFilter'
import Poverty from './PovertyMaps'
import Women from './Women'
import Food from './GlobalFoodLoss'
import AgriculturalIndex from './AgricutureIndex'

class Indicators extends Component {

  constructor(props) {
    super(props);
    this.onChangeGlobalFilter = this.onChangeGlobalFilter.bind(this);
    this.onAppllyFilters = this.onAppllyFilters.bind(this);
    this.onResetFilters = this.onResetFilters.bind(this);
    this.onChangeChartFilter = this.onChangeChartFilter.bind(this);
  }

  onChangeGlobalFilter(name, selection) {
    this.props.onChangeGlobalFilter(name, selection)
  }

  onAppllyFilters() {
      this.props.onApply()
  }

  onResetFilters() {
    this.props.onReset();
  }

  onChangeChartFilter(path, value, options) {
    this.props.onChangeFilter(path, value,options)
  }

  render() {
    return (<div>
      <MainFilter onChange={this.onChangeGlobalFilter} {...this.props } onApply={this.onAppllyFilters} onReset={this.onResetFilters}></MainFilter>


      <div className="indicators content fixed ">

        <div className="indicators title">
          <span>
            <FormattedMessage id="indicators.title" defaultMessage="Indicators"></FormattedMessage>
          </span>
        </div>
        <div className="indicators global intro">
          <FormattedMessage id="indicators.global.intro" defaultMessage={`This page displays the alignment of specific agricultural indicators against the SDGs and the ones managed by the International System for Agricultural Science and Technology (AGRIS). All tables and charts will be automatically updated as new information is entered in the system. Users can use the date and the region filters to refine their search criteria.`}></FormattedMessage>
        </div>
          <Poverty onChange={this.onChangeChartFilter} {...this.props}></Poverty>
          <Women onChange={this.onChangeChartFilter} {...this.props}></Women>
          <Food onChange={this.onChangeChartFilter} {...this.props}></Food>
          <AgriculturalIndex onChange={this.onChangeChartFilter} {...this.props}></AgriculturalIndex>
      </div>
    </div>)
  }
}

const mapStateToProps = state => {
  const globalFilters = state.getIn(['indicator', 'filters', 'global']);
  const regions = state.getIn(['data', 'items', 'region']);
  const crops = state.getIn(['data', 'items', 'cropType']);
  // eslint-disable-next-line no-unused-vars
  const gender = state.getIn(['data', 'items', 'gender']);
  const years = state.getIn(['data', 'items', 'year']);
  const filters = state.getIn(['indicator', 'filters'])

  return {
    globalFilters,
    filters,
    regions,
    crops,
    years,
  }
}

const mapActionCreators = {
  onChangeGlobalFilter: updateGlobalFilter,
  onChangeFilter:updateFilter,
  onReset:reset,
  onApply:apply,
  onExport:exportData,
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Indicators));
