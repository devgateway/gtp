import 'react-pivottable/pivottable.css';
import "./analytic.scss"

import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {loadDataSet} from '../modules/Analytic';

import ReactDOM from 'react-dom';
import PivotTable from 'react-pivottable/PivotTable'
import PivotTableUI from 'react-pivottable/PivotTableUI';

import Plot from 'react-plotly.js';
import TableRenderers from 'react-pivottable/TableRenderers';
import createPlotlyRenderers from 'react-pivottable/PlotlyRenderers';

import {mapFields} from './PivotUtils'

const PlotlyRenderers = createPlotlyRenderers(Plot);


class Production extends Component {
  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    const {config,data}=this.props
    const fields=config.get('fields').toJS()
    const extraFields=config.get('extraFields').toJS()

    return (<div>
      <div className="analytic-container">

        {data && data.size > 0 && <PivotTableUI
            {...config.get('pivottable').toJS()}

          renderers={Object.assign({}, TableRenderers, PlotlyRenderers)}
          data={data.toJS()}
         onChange={s => this.setState(s)} {...this.state}></PivotTableUI>}
      </div>

    </div>)
  }
}

class Analytic extends Component {
  componentDidMount() {
    this.props.onLoad(this.props.dataset)
  }

  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {

    const {dataset} = this.props
    const data = this.props.data? this.props.data.toJS(): []
    return (<div>

      <div className="analytic-container">
        {dataset == 'production' && <Production {...this.props}></Production>}
      </div>
    </div>)
  }
}

const mapStateToProps = state => {

  const dataset = state.getIn(['router', 'location', 'pathname']).split("/").pop()
  return {
    dataset,
    data: state.getIn(['analytic', dataset, 'data']),
    config: state.getIn(['analytic', dataset, 'config'])
  }
}

const mapActionCreators = {
  onLoad: loadDataSet
};

export default connect(mapStateToProps, mapActionCreators)(Analytic);
