import React, {Component, createRef, useState} from 'react'
import { ResponsiveBar } from '@nivo/bar'
import { ResponsiveLine } from '@nivo/line'
import {injectIntl} from 'react-intl';
import messages from '../translations/messages'
const curveOptions = ['linear', 'monotoneX', 'step', 'stepBefore', 'stepAfter'];
const CustomSymbol = ({ size, color, borderWidth, borderColor }) => (
  <g>
    <circle fill="#fff" r={size / 2} strokeWidth={borderWidth} stroke={borderColor} />
    <circle r={size / 5} strokeWidth={borderWidth} stroke={borderColor} fill={color} fillOpacity={0.35} />
  </g>
);


export const LineChart =injectIntl( ({intl, data }) => (
      <ResponsiveLine
        enableGridY={true}
        enableGridX={true}
        data={data}
        margin={{ top: 50, right: 50, bottom: 110, left: 60 }}
        yScale={{ type: 'linear', stacked: false, min: 'auto', max: 'auto' }}
        xScale={{ type: 'point' }}
        colors={{ scheme: 'set1' }}
        useMesh={true}
        pointSymbol={CustomSymbol}
        pointSize={10}
        pointColor={{ theme: 'background' }}

         pointBorderWidth={2}
         pointBorderColor={{ from: 'serieColor' }}
         enablePointLabel={false}
         pointLabel={(s)=>intl.formatNumber(s.y/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0}) }
         pointLabelYOffset={-10}
         pointLabelXOffset={20}
         tooltip={(s)=><div className="tooltip"><div className='x'>{s.point.data.x}</div> <div className=' y'>{intl.formatNumber(s.point.data.y/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0})}</div></div>}
         onMouseEnter={s=>{
         }}


        enableSlices={false}
        curve="monotoneX"
        axisTop={null}
        axisRight={null}
        axisBottom={{
          orient: 'bottom',
          tickSize: 5,
          tickPadding: 5,
          tickRotation: 0,
          legend: intl.formatMessage(messages.year),
          legendOffset: 36,
          legendPosition: 'middle'
        }}
        axisLeft={{
          orient: 'left',
          tickSize: 5,
          tickPadding: 5,
          tickRotation: 0,
          legend:intl.formatMessage(messages.percent),
          legendOffset: -40,
          legendPosition: 'middle'
        }}


        legends={[
          {
            anchor: 'bottom-left',
            direction: 'row',
            justify: false,
            translateX: -11,
            translateY: 70,
            itemsSpacing: 0,
            itemDirection: 'left-to-right',
            itemWidth: 120,
            itemHeight: 20,
            itemOpacity: 0.75,
            symbolSize: 12,
            symbolShape: 'circle',
            symbolBorderColor: 'rgba(0, 0, 0, .5)',
            effects: [
            {
              on: 'hover',
              style: {
                itemBackground: 'rgba(0, 0, 0, .03)',
                itemOpacity: 1
              }
            }
            ]
          }
        ]}
      />
  ))

export const BarChart =injectIntl(({ data , intl/* see data tab */, keys,indexBy , groupMode, colors}) => {



return (
    <ResponsiveBar
      data={data}
      keys={keys}
      indexBy={indexBy}
      groupMode={groupMode}
      colors={colors}
      margin={{ top: 50, right: 130, bottom: 50, left: 60 }}
      padding={0.1}
      innerPadding={0.5}

      fill={[]}
      borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
      axisTop={null}
      axisRight={null}
      axisBottom={{
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend: intl.formatMessage(messages.region),
        legendPosition: 'middle',
        legendOffset: 32
      }}
      axisLeft={{
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend:  intl.formatMessage(messages.percent),
        legendPosition: 'middle',
        legendOffset: -40
      }}
      label={(s)=>intl.formatNumber(s.value/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0}) }
      labelSkipWidth={25}
      labelSkipHeight={22}
      labelTextColor={"#FFF"}

      tooltip={(s)=>{
          return (<div className="tooltip white">
                <div className="color" style={{'background-color':s.color}}></div>
                <div className="label">{s.indexValue}</div>
                <div className='x'>{s.id}</div>
                <div className='y' style={{'color':s.color}}>{intl.formatNumber(s.value/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0})}</div>
        </div>)
      }}

      legends={[
        {
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
          itemOpacity:1,
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
      motionDamping={15}
    />)
  })
