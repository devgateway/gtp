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
import {sortAs, aggregatorTemplates, numberFormat, localeStrings} from 'react-pivottable/Utilities';

const PlotlyRenderers = createPlotlyRenderers(Plot);
// aggregator templates default to US number formatting but this is overrideable
var usFmt = numberFormat();
var usFmtInt = numberFormat({digitsAfterDecimal: 0});
var usFmtPct = numberFormat({digitsAfterDecimal: 1, scaler: 100, suffix: '%'});

function intl(key, values) {
  return formattedMessage({id:"123", defaultMessage:"some message"});
}
// default aggregators & renderers use US naming and number formatting
var aggregators = function(tpl) {
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

  const options = {
    "regionNames": {
      "6": "Dakar",
      "7": "Diourbel",
      "8": "Fatick",
      "9": "Kaolack",
      "10": "Kolda",
      "11": "Louga",
      "12": "Matam",
      "13": "Saint-Louis",
      "14": "Tambacounda",
      "15": "Thiès",
      "16": "Ziguinchor",
      "17": "Kaffrine",
      "18": "Kédougou",
      "19": "Sédhiou"
    },
    "regionCodes": {
      "6": "DK",
      "7": "DL",
      "8": "FK",
      "9": "KL",
      "10": "KD",
      "11": "LG",
      "12": "MT",
      "13": "SL",
      "14": "TC",
      "15": "TH",
      "16": "ZG",
      "17": "KF",
      "18": "KG",
      "19": "SD"
    },
    "cropTypeNames": {
      "96": "Millet",
      "97": "Rice",
      "98": "Imported Rice",
      "99": "Imported Scented Rice",
      "100": "Sorghum",
      "101": "Imported Sorghum",
      "110": "Peanut",
      "111": "Irrigated Rice",
      "112": "Rainfed Rice",
      "113": "Mock Tomato",
      "153": "Fonio",
      "154": "Peanut Oil",
      "155": "Cotton",
      "156": "Cowpea",
      "157": "Cassava",
      "94": "Corn",
      "158": "Watermelon",
      "95": "Imported Corn",
      "159": "Sesame"
    }
  }

  const mapProductionData = (data) => {
    debugger;
    return data.map(r => {
      let nr={}
      nr['Region Code'] = options.regionCodes[r.region]
      nr['Region'] = options.regionNames[r.region]
      nr['Crop'] = options.cropTypeNames[r.cropType]
      nr['Year'] =r.year
      nr['Yield'] =r.yield
      return nr
    });
  }

  class Production extends Component {

    constructor(props) {
      super(props);
      this.state = {}
    }

    render() {
      console.log(aggregators)
      const {dataset} = this.props
      const data = this.props[dataset]
        ? this.props[dataset].toJS()
        : []
      return (<div>
        <div className="analytic-container">
          {data && data.length > 0 && <PivotTableUI renderers={Object.assign({}, TableRenderers, PlotlyRenderers)} data={mapProductionData(data)} onChange={s => this.setState(s)} {...this.state}></PivotTableUI>}
        </div>

      </div>)
    }
  }

  class Home extends Component {
    componentDidMount() {

      this.props.onLoad(this.props.dataset)
    }

    constructor(props) {
      super(props);
      this.state = {}
    }

    render() {
      console.log(aggregators)
      const {dataset} = this.props
      const data = this.props[dataset]
        ? this.props[dataset].toJS()
        : []
      return (<div>
        <Header/>
        <div className="analytic-container">
          {dataset == 'production' && <Production {...this.props}></Production>}
        </div>
        <Footer></Footer>
      </div>)
    }
  }

  const mapStateToProps = state => {

    return {
      dataset: state.getIn(['router', 'location', 'pathname']).split("/").pop(),
      production: state.getIn(['analytic', 'production']),
      marketPrice: state.getIn(['analytic', 'market']),
      consumption: state.getIn(['analytic', 'consumption'])
    }
  }

  const mapActionCreators = {
    onLoad: loadDataSet
  };

  export default connect(mapStateToProps, mapActionCreators)(Home);
