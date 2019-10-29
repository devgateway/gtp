import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage} from 'react-intl';
import {ChartTableSwitcher, CustomFilterDropDown} from './Components'
import Plot from 'react-plotly.js';
import {
  Dropdown,
  Grid,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky
} from 'semantic-ui-react'

const Pooverty = (props) => {

  return (<div className="indicator-chart-container">

    <div className="indicator chart title poverty">
      <p>
        <FormattedMessage id="inidicators.chart.poverty.title" defaultMessage="Proportion of population below the international poverty line"></FormattedMessage>
      </p>
      <ChartTableSwitcher mode='chart'></ChartTableSwitcher>
    </div>
    <div className="indicator chart description poverty">
      <p>
        <FormattedMessage id="inidicators.chart.poverty.description" defaultMessage="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen."></FormattedMessage>
      </p>
      <div className="indicator chart icon download xls"></div>
      <div className="indicator chart icon download png"></div>
      <div className="indicator chart icon download csv"></div>
    </div>
    <div className="indicator chart filter  poverty">

      <CustomFilterDropDown selected={[]} text={<FormattedMessage id = "indicators.main.filter.year" defaultMessage = "Campain/Years" > </FormattedMessage>} options={[]}></CustomFilterDropDown>
      <CustomFilterDropDown selected={[]} text={<FormattedMessage id = "indicators.main.filter.year" defaultMessage = "Campain/Years" > </FormattedMessage>} options={[]}></CustomFilterDropDown>

    </div>
    <Plot data={[
        {
          x: [
            1, 2, 3
          ],
          y: [
            2, 6, 3
          ],
          type: 'scatter',
          mode: 'lines+points',
          marker: {
            color: 'red'
          }
        }, {
          type: 'bar',
          x: [
            1, 2, 3
          ],
          y: [2, 5, 3]
        }
      ]} layout={{
        width: 1000,
        height: 500,
        title: 'A Fancy Plot'
      }}/> < /div>)

}

export default Pooverty
