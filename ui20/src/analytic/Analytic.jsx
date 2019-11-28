import 'react-pivottable/pivottable.css';
import "./analytic.scss"
import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import {loadDataSet} from '../modules/Analytic';
import {loadDataItems} from '../modules/Data';
import ReactDOM from 'react-dom';
import PivotTable from 'react-pivottable/PivotTable'
import PivotTableUI from 'react-pivottable/PivotTableUI';
import Plot from 'react-plotly.js';
import TableRenderers from 'react-pivottable/TableRenderers';
import createPlotlyRenderers from 'react-pivottable/PlotlyRenderers';
import {aggregatorTemplates} from 'react-pivottable/Utilities'
import {aggregators} from './PivotUtils'

import productionConfigurator from './ProductionConf'
import marketPriceConfigurator from './MarketPriceConf'
import consumptionConfigurator from './ConsumptionConf'

const mapFields = (data, fields, extraFields, dataItems) => {

  return data.map(r => {
    let nr = {}
    Object.keys(r).forEach(k => {
      const name = fields[k];
      nr[name] = r[k]
    })

    Object.keys(extraFields).forEach(ex => {
      const extra = extraFields[ex];

      nr[extra.label] = extra.extractor(nr)(dataItems)
    })

    return nr
  });
}

 const configure = (intl) => (dispatch, getState) => {
  const production = productionConfigurator(intl)
  const consumption = consumptionConfigurator(intl)
  const marketPrice = marketPriceConfigurator(intl)

  const language=intl.locale

  return({
    production,
    consumption,
    marketPrice,
    language
  })
}



const PlotlyRenderers = createPlotlyRenderers(Plot);

class Table extends Component {
  debugger;
  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    const {config, data} = this.props

    return (<PivotTableUI data={data} {...config.pivottable} onChange={s => this.setState(s)} {...this.state}></PivotTableUI>)
  }
}



const TableWrapper =(props)=>{
  const {name, isDataReady, intl,items} = props
  const data = props.data? props.data.toJS():  null


  if(isDataReady && name=='production' && data){

      const configuration=productionConfigurator(intl)
      const preparedData = mapFields(data, configuration.fields,configuration.extraFields, items.toJS())
      return (<div>
        <div className="analytic table">

          <Table data={preparedData} config={configuration}></Table>
        </div>
      </div>)
  }
  if(isDataReady && name=='consumption' && data){
      debugger;
      const configuration=consumptionConfigurator(intl)
      const preparedData = mapFields(data, configuration.fields,configuration.extraFields, items.toJS())
      return (<div>
        <div className="analytic table">

          <Table data={preparedData} config={configuration}></Table>
        </div>
      </div>)
  }

  if(isDataReady && name=='marketPrice' && data){
      debugger;
      const configuration=marketPriceConfigurator(intl)
      const preparedData = mapFields(data, configuration.fields,configuration.extraFields, items.toJS())
      return (<div>

          <Table data={preparedData} config={configuration}></Table>

      </div>)
  }
  return null;
}


class Analytic extends Component{

  constructor(props) {
    super(props);
    this.state = {}
  }

  componentDidUpdate(prevProps, prevState, snapshot){

    if(this.props.isDataReady && (this.props.name!==prevProps.name || this.props.data==null) ){
      this.props.onLoadDataSet(this.props.name)
    }
  }

  componentDidMount() {
    this.props.onLoadDataSet(this.props.name)
  }

  render() {
    return (
    <div className="analytic container">
      <div className="analytic title">
        <p>
          <FormattedMessage id=" analytic.title" defaultMessage="Analysis and Visual Tools."></FormattedMessage>
        </p>
      </div>

      <div className="analytic subtitle">
          <p>{this.props.name=='production' && (<FormattedMessage id="analytic.title.production" defaultMessage="Production"/>)}</p>
        <p>{this.props.name=='consumption' && (<FormattedMessage id="analytic.title.consumption" defaultMessage="Consumption"/>)}</p>
        <p>{this.props.name=='marketPrice' && (<FormattedMessage id="analytic.title.market" defaultMessage="Market"/>)}</p>
      </div>
      <div className="analytic description">
        <p>
        <FormattedMessage id="analytic.description" defaultMessage="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum eget mauris ut lectus laoreet volutpat. Sed dictum, sapien interdum finibus tristique, ex lectus varius leo, at tempor sapien odio sed dolor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum eget mauris ut lectus laoreet volutpat. Sed dictum, sapien interdum finibus tristique, ex lectus varius leo, at tempor sapien odio sed dolor."/>
        </p>
      </div>
      <TableWrapper {...this.props}/>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {

  const region = state.getIn(['data', 'items', 'region']);
  const cropType = state.getIn(['data', 'items', 'cropType']);
  const department = state.getIn(['data', 'items', 'market']);
  const market = state.getIn(['data', 'items', 'department']);
  const items = state.getIn(['data', 'items']);

  const isDataReady = (region!=null && cropType !=null&& department !=null&& market!=null)

  const name = state.getIn(['router', 'location', 'pathname']).split("/").pop()
  const data = state.getIn(['analytic',name,'data'])

  return {
    data,
    items,
    isDataReady,
    name
  }
}

const mapActionCreators = {
  onLoadDataSet:loadDataSet
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Analytic));
