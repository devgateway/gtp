import "./indicators.scss"

import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import {updateGlobalFilter,updateFilter,reset,apply} from '../modules/Indicator'

import {
  Dropdown,
  Grid,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky
} from 'semantic-ui-react'

import GlobalNumbers from './GlobalNumbers'

import MainFilter from './MainFilter'
import Poverty from './Poverty'
import Women from './Women'

import {ChartTableSwitcher, CustomFilterDropDown} from './Components'

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
          <FormattedMessage id="indicators.global.intro" defaultMessage={`Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled`}></FormattedMessage>
        </div>


          <GlobalNumbers  {...this.props}></GlobalNumbers>

        <Segment className="info-text">
          <div className="source-icon"></div>
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
        </Segment>
        <Grid columns={2} className="info-text table">
          <Grid.Row>
            {
              [1, 2].map(n => (<Grid.Column>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
            </Grid.Column>))
            }
            </Grid.Row>
        </Grid>
          <Poverty onChange={this.onChangeChartFilter} {...this.props}></Poverty>
          <Women onChange={this.onChangeChartFilter} {...this.props}></Women>
      </div>
    </div>)
  }
}

const mapStateToProps = state => {
  const globalFilters = state.getIn(['indicator', 'filters', 'global']);
  const regions = state.getIn(['data', 'items', 'region']);
  const crops = state.getIn(['data', 'items', 'cropType']);
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
  onApply:apply
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Indicators));
