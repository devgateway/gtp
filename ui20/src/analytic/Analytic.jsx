import 'react-pivottable/pivottable.css';
import "./analytic.scss"

import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import {configure, loadDataSet} from '../modules/Analytic';
import {loadDataItems} from '../modules/Data';

import ReactDOM from 'react-dom';
import PivotTable from 'react-pivottable/PivotTable'
import PivotTableUI from 'react-pivottable/PivotTableUI';

import Plot from 'react-plotly.js';
import TableRenderers from 'react-pivottable/TableRenderers';
import createPlotlyRenderers from 'react-pivottable/PlotlyRenderers';
import {aggregatorTemplates} from 'react-pivottable/Utilities'

import {aggregators} from './PivotUtils'

const PlotlyRenderers = createPlotlyRenderers(Plot);

class Table extends Component {
  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    const {config, data} = this.props
    console.log(aggregatorTemplates)


    const fields = config
      ? config.get('fields').toJS()
      : null
    const extraFields = config
      ? config.get('extraFields').toJS()
      : null

    return (<div>
      <div className="analytic-container">

        {data && data.size > 0 && <PivotTableUI aggregators={aggregators(this.props.intl)}
         {...config.get('pivottable').toJS()} renderers={Object.assign({}, TableRenderers, PlotlyRenderers)}
         data={data.toJS()} onChange={s => this.setState(s)} {...this.state}></PivotTableUI>}
      </div>

    </div>)
  }
}

class Analytic extends Component {


  componentDidMount() {
    this.props.onLoadFilterData('region')
    this.props.onLoadFilterData('cropType')
    this.props.onLoadFilterData('department')
    this.props.onLoadFilterData('market')
    this.props.onConfigure(this.props.intl)
    this.props.onLoadData(this.props.dataset)
  }

  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    ;
    const {dataset, isDataReady} = this.props
    const data = this.props.data
      ? this.props.data.toJS()
      : []

    return (<div>
      <div className="analytic-container">
        {isDataReady && <Table {...this.props} ></Table>}
      </div>
    </div>)
  }
}

const mapStateToProps = state => {
  const dataset = state.getIn(['router', 'location', 'pathname']).split("/").pop()
  const isDataReady = (state.getIn(['data', 'items', 'region']) != null) && (state.getIn(['data', 'items', 'cropType']) != null) && (state.getIn(['data', 'items', 'department']) != null) && (state.getIn(['data', 'items', 'market']) != null)

  return {
    isDataReady,
    dataset,
    data: state.getIn(['analytic', dataset, 'data']),
    config: state.getIn(['analytic', dataset, 'config'])
  }
}

const mapActionCreators = {
  onLoadFilterData: loadDataItems,
  onConfigure: configure,
  onLoadData: loadDataSet
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Analytic));
