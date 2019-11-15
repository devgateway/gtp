import React, {Component, createRef, useState} from 'react'
import {ResponsiveBar} from '@nivo/bar'
import {generateCountriesData} from '@nivo/generators'
import {ResponsiveLine} from '@nivo/line'
import {Tab} from 'semantic-ui-react'
import {FormattedMessage, FormattedNumber, injectIntl} from 'react-intl';

const curveOptions = ['linear', 'monotoneX', 'step', 'stepBefore', 'stepAfter'];

const CustomSymbol = ({ size, color, borderWidth, borderColor }) => (
  <g>
    <circle fill="#fff" r={size / 2} strokeWidth={borderWidth} stroke={borderColor} />
    <circle r={size / 5} strokeWidth={borderWidth} stroke={borderColor} fill={color} fillOpacity={0.35} />
  </g>
);


const CustomBarComponent = (props) => {
  const { x, y, width, height, color }=props
  return (<circle cx={x + width / 2} cy={y + height / 2} r={Math.min(width, height) / 2} fill={color} />)
  };

export const LineChart =injectIntl( ({intl, data }) => (
        <ResponsiveLine
          pointSymbol={CustomSymbol}

            enableGridY={true}
            enableGridX={true}
            data={data}
            margin={{ top: 50, right: 130, bottom: 50, left: 60 }}

            yScale={{ type: 'linear', stacked: false, min: 'auto', max: 'auto' }}

            xScale={{ type: 'point' }}


            tooltip={a=>{return<div className="tooltip">{a.point.serieId}  : {intl.formatNumber(a.point.data.y/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0})}</div>}}
            colors={{scheme:'spectral'}}

            pointBorderWidth={0}


            curve="monotoneX"

            axisTop={null}
            axisRight={null}
            axisBottom={{
                orient: 'bottom',
                tickSize: 5,
                tickPadding: 5,
                tickRotation: 0,
                legend: 'Year',
                legendOffset: 36,
                legendPosition: 'middle'
            }}
            axisLeft={{
                orient: 'left',
                tickSize: 5,
                tickPadding: 5,
                tickRotation: 0,
                legend: 'Percent',
                legendOffset: -40,
                legendPosition: 'middle'
            }}

            pointSize={10}
            pointColor={'#FFF'}

            pointBorderWidth={2}
            pointBorderColor={{ from: 'serieColor' }}
            enablePointLabel={false}
            pointLabelYOffset={-12}
            animate={true}
             motionStiffness={90}
              motionDamping={15}
            useMesh={true}
            legends={[
                {

                      anchor: 'bottom-right',
                    direction: 'column',
                    justify: false,
                    translateX: 100,
                    translateY: 0,
                    itemsSpacing: 0,
                    itemDirection: 'left-to-right',
                    itemWidth: 80,
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

export const BarChart = ({data, intl,keys,indexBy,groupMode,colors}) => {
  return (
  <ResponsiveBar data={data} keys={keys} indexBy={indexBy}

     groupMode={groupMode}
      margin={{top: 50,right: 130,bottom: 50,left: 60
    }}
    padding={0.35}

    innerPadding={3}

    defs={[
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
    ]}

     fill={[]} borderColor={{
      from: 'color',
      modifiers: [
        ['darker', 1.6]
      ]
    }}

    axisTop={null}
    axisRight={null}
    axisBottom={{
      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend: 'Region',
      legendPosition: 'middle',
      legendOffset: 32
    }}

    axisLeft={{
      tickSize: 5,
      tickPadding: 5,
      tickRotation: 0,
      legend: 'Count',
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
}
