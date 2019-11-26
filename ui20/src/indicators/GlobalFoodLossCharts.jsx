import React, {Component, createRef, useState} from 'react'
import {ResponsiveBar} from '@nivo/bar'
import {ResponsiveLine} from '@nivo/line'
import {injectIntl} from 'react-intl';
import messages from '../translations/messages'
const curveOptions = ['linear', 'monotoneX', 'step', 'stepBefore', 'stepAfter'];


export const BarChart = injectIntl(({intl,data,keys,indexBy,groupMode,colors,label, yLegend,xLegend}) => {
  return (
  <ResponsiveBar data={data} keys={keys} indexBy={indexBy}
    groupMode={groupMode}
    margin={{top: 50,right: 130,bottom: 50,left: 60}}
    padding={0.35}
    innerPadding={3}
    axisTop={null}
    axisRight={null}
    label={label}
    axisBottom={{
      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend: xLegend,
      legendPosition: 'middle',
      legendOffset: 32
    }}

    axisLeft={{
      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend: yLegend,
      legendPosition: 'middle',
      legendOffset: -40
    }}


    labelSkipWidth={12}
    labelSkipHeight={12}
    labelTextColor={"#FFF"}
    legends={[{
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
    ]}
    animate={true}
    motionStiffness={90}
    motionDamping={15}/>)
})
