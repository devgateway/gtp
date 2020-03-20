import 'react-pivottable/pivottable.css';
import "./analytic.scss"
import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import {loadDataSet} from '../modules/Analytic';
import {loadDataItems} from '../modules/Data';
import ReactDOM from 'react-dom';
import PivotTable from 'react-pivottable/PivotTable'
import PivotTableUI from './PivotTableUI.js';
import Plot from 'react-plotly.js';
import {TableRenderers, TableRenderersWithIntl} from './TableRenders';
import createPlotlyRenderers from 'react-pivottable/PlotlyRenderers';
import {aggregatorTemplates} from 'react-pivottable/Utilities'
import {aggregators} from './PivotUtils'

import productionConfigurator from './ProductionConf'
import marketPriceConfigurator from './MarketPriceConf'
import consumptionConfigurator from './ConsumptionConf'

import  messages from '../translations/messages'
import { Label } from 'semantic-ui-react'

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

  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    const {config, data,intl} = this.props
    const renders={};
      renders[intl.formatMessage(messages.pivot_renders_table)]=TableRenderersWithIntl(this.props.intl)['Table'].bind({intl:this.props.intl})
      renders[intl.formatMessage(messages.pivot_renders_table_heatmap)]=TableRenderersWithIntl(this.props.intl)['Table Heatmap']
      renders[intl.formatMessage(messages.pivot_renders_table_col_heatmap)]=TableRenderersWithIntl(this.props.intl)['Table Col Heatmap']
      renders[intl.formatMessage(messages.pivot_renders_table_row_heatmap)]=TableRenderersWithIntl(this.props.intl)['Table Row Heatmap']

      renders[intl.formatMessage(messages.pivot_renders_exportable_tsv)]=TableRenderersWithIntl(this.props.intl)['Exportable TSV']
      renders[intl.formatMessage(messages.pivot_renders_stacked_column_chart)]=PlotlyRenderers['Stacked Column Chart']
      renders[intl.formatMessage(messages.pivot_renders_grouped_column_chart)]=PlotlyRenderers['Grouped Column Chart']
      renders[intl.formatMessage(messages.pivot_renders_grouped_bar_chart)]=PlotlyRenderers['Grouped Bar Chart']
      renders[intl.formatMessage(messages.pivot_renders_stacked_bar_chart)]=PlotlyRenderers['Stacked Bar Chart']
      renders[intl.formatMessage(messages.pivot_renders_line_chart)]=PlotlyRenderers['Line Chart']
      renders[intl.formatMessage(messages.pivot_renders_dot_chart)]=PlotlyRenderers['Dot Chart']
      renders[intl.formatMessage(messages.pivot_renders_area_chart)]=PlotlyRenderers['Area Chart']
      renders[intl.formatMessage(messages.pivot_renders_scatter_chart)]=PlotlyRenderers['Scatter Chart']
      renders[intl.formatMessage(messages.pivot_renders_multiple_pie_chart)]=PlotlyRenderers['Multiple Pie Chart']


    return (<PivotTableUI data={data}
        aggregators={aggregators(intl)}
        locale={intl.locale}
        renderers={renders} {...config.pivottable} onChange={s => this.setState(s)} {...this.state}></PivotTableUI>)
  }
}



const TableWrapper =(props)=>{
  const {name, isDataReady, intl,items} = props
  const data = props.data? props.data.toJS():  null

  debugger;
  if(isDataReady && name=='production' && data && data.length > 0){
      const configuration=productionConfigurator(intl)
      const preparedData = mapFields(data, configuration.fields,configuration.extraFields, items.toJS())
      return (<div>
        <div className="analytic table">

          <Table intl={intl} data={preparedData} config={configuration}></Table>
        </div>
      </div>)
  }

  if(isDataReady && name=='consumption' && data && data.length > 0){

      const configuration=consumptionConfigurator(intl)
      const preparedData = mapFields(data, configuration.fields,configuration.extraFields, items.toJS())
      return (<div>
        <div className="analytic table">
          <Table intl={intl} data={preparedData} config={configuration}></Table>
        </div>
      </div>)
  }

  if(isDataReady && name=='marketPrice' && data && data.length > 0){
    const configuration=marketPriceConfigurator(intl)
      const preparedData = mapFields(data, configuration.fields,configuration.extraFields, items.toJS())
      return (<div><div className="analytic table"><Table intl={intl} data={preparedData} config={configuration}></Table></div></div>)
    }


    return <div>
      <div className="no data message container">
        <Label   ribbon="right" className="no data centered" basic color="olive" inverted><FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage></Label>
      </div>
    </div>
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
    <div className={`analytic container ${this.props.name}`}>
      <div className={`analytic title ${this.props.name}`}>
        <p>
          <FormattedMessage id="analytic.page.title" defaultMessage="Analysis and Visual Tools."></FormattedMessage>
        </p>
      </div>


      <div className="analytic subtitle">
        <p>{this.props.name=='production' && (<FormattedMessage id="analytic.title.production" defaultMessage="Production"/>)}</p>
        <p>{this.props.name=='consumption' && (<FormattedMessage id="analytic.title.consumption" defaultMessage="Consumption"/>)}</p>
        <p>{this.props.name=='marketPrice' && (<FormattedMessage id="analytic.title.market" defaultMessage="Market"/>)}</p>
      </div>
      <div className="analytic description">
      <p>{this.props.name=='production' && (<FormattedMessage id="analytic.description.production" defaultMessage="Visualize the annual production statistics. Different combinations can be made by displaying separately or combining the production figures with the yield and / or seeded area."/>)}</p>
      <p>{this.props.name=='consumption' && (<FormattedMessage id="analytic.description.consumption" defaultMessage="The objective is to visualize the national level of cereal consumption. Which can be disaggregated at regional and departmental level."/>)}</p>
      <p>{this.props.name=='marketPrice' && (<FormattedMessage id="analytic.description.market" defaultMessage="The Market page shows the price evolution of cereals. Prices are collected in rural markets located in agricultural production areas."/>)}</p>

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
