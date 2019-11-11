import React, {Component, createRef, useState} from 'react'
import {ResponsiveBar} from '@nivo/bar'
import {generateCountriesData} from '@nivo/generators'
import {ResponsiveLine} from '@nivo/line'
import {Tab} from 'semantic-ui-react'
import {FormattedMessage, FormattedNumber, injectIntl} from 'react-intl';

const curveOptions = ['linear', 'monotoneX', 'step', 'stepBefore', 'stepAfter'];

const BarChart = ({
  data, intl,
  /* see data tab */
  keys,
  indexBy,
  groupMode,
  colors
}) => {
  return (<ResponsiveBar data={data} keys={keys} indexBy={indexBy} groupMode={groupMode} colors={colors} margin={{
      top: 50,
      right: 130,
      bottom: 50,
      left: 60
    }} padding={0.35} innerPadding={3} defs={[
      {
        id: 'dots',
        type: 'patternDots',
        background: 'inherit',
        color: '#38bcb2',
        size: 4,
        padding: 1,
        stagger: true
      }, {
        id: 'lines',
        type: 'patternLines',
        background: 'inherit',
        color: '#eed312',
        rotation: -45,
        lineWidth: 6,
        spacing: 10
      }
    ]} fill={[]} borderColor={{
      from: 'color',
      modifiers: [
        ['darker', 1.6]
      ]
    }} axisTop={null} axisRight={null} axisBottom={{
      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend: 'Region',
      legendPosition: 'middle',
      legendOffset: 32
    }} axisLeft={{
      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend: 'Count',
      legendPosition: 'middle',
      legendOffset: -40
    }} label={(s) => intl.formatNumber(s.value / 100, {
      style: 'percent',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    })} labelSkipWidth={12} labelSkipHeight={12} labelTextColor={"#FFF"} legends={[{
        dataFrom: 'keys',
        anchor: 'bottom-right',
        direction: 'column',
        justify: false,
        translateX: 120,
        translateY: 0,
        itemsSpacing: 2,
        itemWidth: 100,
        itemHeight: 20,
        itemDirection: 'left-to-right',
        itemOpacity: 1,
        symbolSize: 20,
        effects: [
          {
            on: 'hover',
            style: {
              itemOpacity: 1
            }
          }
        ]
      }
    ]} animate={true} motionStiffness={90} motionDamping={15}/>)
}

const generateStackedData = (data = []) => {
  debugger;
  let stackedData = []
  data.forEach(r => {
    const key = r.group;
    if (!stackedData[key]) {
      stackedData[key] = {};
    }
    stackedData[key]['group'] = r.group;
    stackedData[key]['value'] = r.percentage;
    stackedData[key]['year'] = r.year;

  })
  return Object.keys(stackedData).map(k => stackedData[k]);
}

const ChartContainer = (props) => {

  const {data} = props

  const ageData=data.filter(d=>d.group='Age Group')


  if (ageData) {
    const keys = Array.from(new Set(ageData.map(d => {return d.groupType})))
    const options = {
      keys,
      data: generateStackedData(ageData)
    }


    return <div>
      <BarChart {...options}></BarChart>
    </div>

  } else {
    return <div></div>
  }
}

export default injectIntl(ChartContainer)
