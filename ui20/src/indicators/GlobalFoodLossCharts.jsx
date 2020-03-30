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
    margin={{top: 10,right: 50,bottom: 190,left: 50}}
    padding={0.35}
    innerPadding={0}
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
        anchor: 'bottom',
        direction: 'column',
        justify: false,
        translateX: -500,
        translateY: 180,
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
          tooltip={(s)=>{
              console.log(label)

              return (<div className="tooltips white">
                    <div className="color" style={{'background-color':s.color}}></div>
                    <div className="label">{s.indexValue}</div>
                    <div className='x'>{s.id}</div>
                    <div className='y' style={{'color':s.color}}>{label(s)}</div>
            </div>)
          }}


    animate={true}
    motionStiffness={90}
    motionDamping={15}/>)
})
