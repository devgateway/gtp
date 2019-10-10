import 'react-pivottable/pivottable.css';
import "./analytic.scss"

import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import {loadDataSet} from '../modules/Analytic';

import Header from '../layout/Header'
import Footer from '../layout/Footer'

import ReactDOM from 'react-dom';
import PivotTable from 'react-pivottable/PivotTable'
import PivotTableUI from 'react-pivottable/PivotTableUI';

import Plot from 'react-plotly.js';
import TableRenderers from 'react-pivottable/TableRenderers';
import createPlotlyRenderers from 'react-pivottable/PlotlyRenderers';
import {sortAs,aggregatorTemplates,numberFormat,localeStrings} from 'react-pivottable/Utilities';

const PlotlyRenderers = createPlotlyRenderers(Plot);
// aggregator templates default to US number formatting but this is overrideable
var usFmt = numberFormat();
var usFmtInt = numberFormat({ digitsAfterDecimal: 0 });
var usFmtPct = numberFormat({
  digitsAfterDecimal: 1,
  scaler: 100,
  suffix: '%'
});


// default aggregators & renderers use US naming and number formatting
var aggregators = function (tpl) {
  return {
    Count: tpl.count(usFmtInt),
    'Count Unique Values': tpl.countUnique(usFmtInt),
    'List Unique Values': tpl.listUnique(', '),
    Sum: tpl.sum(usFmt),
    'Integer Sum': tpl.sum(usFmtInt),
    Average: tpl.average(usFmt),
    Median: tpl.median(usFmt),
    'Sample Variance': tpl.var(1, usFmt),
    'Sample Standard Deviation': tpl.stdev(1, usFmt),
    Minimum: tpl.min(usFmt),
    Maximum: tpl.max(usFmt),
    First: tpl.first(usFmt),
    Last: tpl.last(usFmt),
    'Sum over Sum': tpl.sumOverSum(usFmt),
    'Sum as Fraction of Total': tpl.fractionOf(tpl.sum(), 'total', usFmtPct),
    'Sum as Fraction of Rows': tpl.fractionOf(tpl.sum(), 'row', usFmtPct),
    'Sum as Fraction of Columns': tpl.fractionOf(tpl.sum(), 'col', usFmtPct),
    'Count as Fraction of Total': tpl.fractionOf(tpl.count(), 'total', usFmtPct),
    'Count as Fraction of Rows': tpl.fractionOf(tpl.count(), 'row', usFmtPct),
    'Count as Fraction of Columns': tpl.fractionOf(tpl.count(), 'col', usFmtPct)
  };
}(aggregatorTemplates);
//renderers, aggregators and localeStrings


var locales = {
  en: {
    aggregators: aggregators,
    localeStrings: {
      renderError: 'An error occurred rendering the PivotTable results.',
      computeError: 'An error occurred computing the PivotTable results.',
      uiRenderError: 'An error occurred rendering the PivotTable UI.',
      selectAll: 'Select All',
      selectNone: 'Select None',
      tooMany: '(too many to list)',
      filterResults: 'Filter values',
      apply: 'Apply',
      cancel: 'Cancelar',
      totals: 'totales',
      vs: 'vs',
      by: 'by'
    }
  }
};


class Home extends Component {
  componentDidMount() {

    this.props.onLoad(this.props.dataset)
  }

  constructor(props) {
        super(props);
        this.state = props;
    }

  render() {
    console.log(aggregators)
    const {dataset}=this.props
    const data=this.props[dataset]?this.props[dataset].toJS():[]
    return (<div>
      <Header/>
      <div className="analytic-container">
      {data&&data.length > 0  &&  <PivotTableUI
        rows={["year","region"]}
          cols={["sourface"]}
       aggregatorName="Sum"
       vals={["production"]}
       language="fr"
       aggregatorNames={["Moyenne","Nombre","Somme"]}
       renderers={Object.assign({}, TableRenderers, PlotlyRenderers)}
                 data={data}
             onChange={s => this.setState(s)}  {...this.state}></PivotTableUI>
        }
        </div>
      <Footer></Footer>
    </div>)
  }
}

const mapStateToProps = state => {

  return {
    dataset:state.getIn(['router','location','pathname']).split("/").pop(),
    production: state.getIn(['analytic', 'production']),
    marketPrice: state.getIn(['analytic', 'market']),
    consumption: state.getIn(['analytic', 'consumption'])
  }
}

const mapActionCreators = {
  onLoad: loadDataSet
};

export default connect(mapStateToProps, mapActionCreators)(Home);
