import React, {Component, createRef, useState} from 'react'
import { ResponsiveBar } from '@nivo/bar'
import { ResponsiveLine } from '@nivo/line'
import { ResponsiveBullet } from '@nivo/bullet'
import { BasicTooltip } from '@nivo/tooltip'

import {injectIntl, FormattedMessage} from 'react-intl';
import messages from '../translations/messages'


const CustomSymbol = ({ size, color, borderWidth, borderColor }) => (
  <g>
    <circle fill="#fff" r={size / 2} strokeWidth={borderWidth} stroke={borderColor} />
    <circle r={size / 5} strokeWidth={borderWidth} stroke={borderColor} fill={color} fillOpacity={0.35} />
  </g>
);


const makeLineData=(data)=>{

  return data.map(d=>{
      return {
          id:d.name,
          data:d.yearValues.map(yv=>{return {x:yv.year,y:yv.value}})
      }
  })
}

export const LineChart =injectIntl( ({intl, data,color }) => {

  return ((
            <div className="national chart tall">
            <ResponsiveLine
          enableGridY={true}
          enableGridX={true}
          data={makeLineData(data)}
          margin={{ top: 10, right: 30, bottom: 210, left: 60 }}
          yScale={{ type: 'linear', stacked: false, min: 'auto', max: 'auto' }}
          xScale={{ type: 'point' }}
          colors={{ scheme: color?color:'nivo' }}
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
            tickPadding: 7,
            tickRotation: 0,
            legend:intl.formatMessage(messages.percent),
            legendOffset: -50,
            legendPosition: 'middle'
          }}


          legends={[
            {
              anchor: 'bottom-left',
              direction: 'column',
              translateX: -11,
              translateY: 130,
              itemsSpacing: 0,
              itemWidth: 120,
              itemHeight: 20,
              itemOpacity: 0.75,
              symbolSize: 12,
              symbolShape: 'circle',
              symbolBorderColor: 'rgba(0, 0, 0, .5)',

            }
          ]}
        /></div>
    ))
})


const handleMarkerTooltip = (showTooltip, marker, event) => {

    debugger;
       showTooltip(
           <BasicTooltip
               id={<strong>{marker.value}</strong>}
               enableChip={true}
               color={marker.color}

               //format={format}
               //renderContent={typeof tooltip === 'function' ? tooltip.bind(null, { ...node }) : null}
           />,
           event
       )
   }




const CustomMarker = ({ x, size, color, onMouseEnter, onMouseMove, onMouseLeave }) => {


  return (

      <g
          transform={`translate(${x},0)`}>

          <line
              x1={0}
              x2={0}
              y1={0}
              y2={size}
              stroke={color}
              strokeWidth={2}
              strokeDasharray="2,3"
              fill="none"
          />
          <path d="M0 -10 L 10 0 L 0 10 L -10 0 Z" fill={color} />
      </g>
  )
}


const CustomRange = ({ x, y, width, height, color, onMouseEnter, onMouseMove, onMouseLeave }) => (
    <rect
        x={x + 2}
        y={y + 2}
        rx={5}
        ry={5}
        width={width - 4}
        height={height - 4}
        fill={color}
        onMouseEnter={onMouseEnter}
        onMouseMove={onMouseMove}
        onMouseLeave={onMouseLeave}
    />
)


const CustomMeasure = ({ x, y, width, height, color, onMouseEnter, onMouseMove, onMouseLeave }) => (
    <rect
        x={x + 2}
        y={y + 2}
        rx={height / 4}
        ry={height / 2}
        width={width - 4}
        height={height - 4}
        fill={color}
        onMouseEnter={onMouseEnter}
        onMouseMove={onMouseMove}
        onMouseLeave={onMouseLeave}
    />
)
export const Bullet =injectIntl(({ data ,refData, intl/* see data tab */, keys,indexBy , groupMode, color}) => {
  console.log(refData)
  return(
  <div>


          <div className="national chart">
        <ResponsiveBullet
              data={data}
              margin={{ top: 20, right: 20, bottom: 30, left: 45 }}
              spacing={70}
              titleAlign="start"
              titleOffsetX={-70}
              measureSize={0.2}
              rangeColors={["#e31a1c","#ff7f00","#fdbf6f","#b2df8a"]}
              measureColors={["#4D80C2"]}
              measureSize={0.4}
              titleOffsetX={-41}
              animate={true}
              motionStiffness={90}
              motionDamping={12}
              markerComponent={CustomMarker}
               measureComponent={CustomMeasure}
          />
          </div>
    </div>)
})
