import React, {Component, createRef, useState} from 'react'
import {ResponsiveBar} from '@nivo/bar'
import {ResponsiveLine} from '@nivo/line'
import {injectIntl} from 'react-intl';
import messages from '../translations/messages'



export const BarChart = injectIntl(({data, intl,keys,indexBy,groupMode,colors}) => {



  return (
  <ResponsiveBar data={data} keys={keys} indexBy={indexBy}
    groupMode={groupMode}
    margin={{top: 20,right: 50,bottom: 160,left: 80}}
    padding={0.35}

    innerPadding={0}
    defs={[
      {
        id: 'dots',
        type: 'patternDots',
        background: 'inherit',
        color: '#38bcb2',
        size: 4,
        padding: 1,
        stagger: true
      },
      {
        id: 'lines',
        type: 'patternLines',
        background: 'inherit',
        color: '#eed312',
        rotation: -45,
        lineWidth: 6,
        spacing: 10
      }
    ]}

    fill={[]}

    borderColor={{
      from: 'color',
      modifiers: [
        ['darker', 4]
      ]
    }}

    tooltip={(s)=>{
        return (<div className="tooltips white">
              <div className="color" style={{'background-color':s.color}}></div>
              <div className="label">{s.indexValue}</div>
              <div className='x'>{s.id}</div>
              <div className='y' style={{'color':s.color}}>
              {intl.formatNumber(s.value)+intl.formatMessage(messages.billions_short).toUpperCase()}
              </div>
      </div>)
    }}

    axisTop={null}
    axisRight={null}
    axisBottom={{
      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend: intl.formatMessage(messages.year),
      legendPosition: 'middle',
      legendOffset: 32
    }}

    axisLeft={{

      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend:  intl.formatMessage(messages.billions),
      legendPosition: 'middle',
      legendOffset: -40
    }}


    label={d=>intl.formatNumber(d.value)+intl.formatMessage(messages.billions_short).toUpperCase()}
    labelSkipWidth={12}
    labelSkipHeight={12}
    labelTextColor={"#FFF"}
    legends={[{
        dataFrom: 'keys',
        anchor: 'bottom',
        direction: 'column',
        justify: false,
        translateX: -500,
        translateY: 150,
        itemsSpacing: 2,
        itemWidth: 100,
        itemHeight: 20,
        itemDirection: 'left-to-right',
        itemOpacity: 1,
        symbolSize: 18,
        effects: [
      {
        on: 'hover',
        style: {
          itemOpacity: 1
        }
      }
        ]
    }
    ]}
    animate={true}
    motionStiffness={90}
    motionDamping={15}/>)
})
