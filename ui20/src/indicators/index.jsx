import "./indicators.scss"

import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import {updateGlobalFilter,updateFilter,reset,apply, exportData, loadMetadata} from '../modules/Indicator'
import {Grid,Segment} from 'semantic-ui-react'

import MainFilter from './MainFilter'
import GlobalNumbers from './GlobalNumbers'
import Poverty from './Poverty'
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



  componentDidMount(){

    this.props.onLoadMetadata(this.props.intl.locale)
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
    return (<div className="png exportable">

      <MainFilter onChange={this.onChangeGlobalFilter} {...this.props } onApply={this.onAppllyFilters} onReset={this.onResetFilters}></MainFilter>


      <div className="indicators content fixed " >

        <div className="indicators title">
          <p>
            <FormattedMessage id="indicators.title" defaultMessage="Indicators"></FormattedMessage>
          </p>
        </div>
        <div className="indicators global intro">
          <FormattedMessage id="indicators.global.intro" defaultMessage={`This page displays the alignment of specific agricultural indicators against the SDGs and the ones managed by the International System for Agricultural Science and Technology (AGRIS). All tables and charts will be automatically updated as new information is entered in the system. Users can use the date and the region filters to refine their search criteria.`}></FormattedMessage>
        </div>

          <GlobalNumbers  {...this.props}></GlobalNumbers>
          <Poverty metadata={this.props.povertyMetadata} onChange={this.onChangeChartFilter} {...this.props}></Poverty>
          <Women  metadata={this.props.womenMetadata} onChange={this.onChangeChartFilter} {...this.props}></Women>


          <Food metadata={this.props.globalFoodMetadata}  onChange={this.onChangeChartFilter} {...this.props}></Food>
          <AgriculturalIndex metadata={this.props.aoiMetadata} onChange={this.onChangeChartFilter} {...this.props}></AgriculturalIndex>

      </div>
    </div>)
  }
}

const mapStateToProps = state => {
  const globalFilters = state.getIn(['indicator', 'filters', 'global']);
  const regions = state.getIn(['data', 'items', 'region']);
  const crops = state.getIn(['data', 'items', 'cropType']);

  
  const years = state.getIn(['data', 'items', 'year']);
  const filters = state.getIn(['indicator', 'filters'])
  const metadata = state.getIn(['indicator', 'metadata'])

  const povertyMetadata= metadata?  metadata.filter(f=>f.get('indicatorType')==1).get(0).toJS():null
  const womenMetadata= metadata?  metadata.filter(f=>f.get('indicatorType')==2).get(0).toJS():null
  const globalFoodMetadata= metadata?  metadata.filter(f=>f.get('indicatorType')==3).get(0).toJS():null
  const aoiMetadata= metadata?  metadata.filter(f=>f.get('indicatorType')==4).get(0).toJS():null



  return {
    globalFilters,
    filters,
    regions,
    crops,
    years,
    povertyMetadata,
    womenMetadata,
    globalFoodMetadata,
    aoiMetadata
  }
}

const mapActionCreators = {
  onChangeGlobalFilter: updateGlobalFilter,
  onChangeFilter:updateFilter,
  onReset:reset,
  onApply:apply,
  onExport:exportData,
  onLoadMetadata:loadMetadata
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Indicators));
