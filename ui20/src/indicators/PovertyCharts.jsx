import React, {Component, createRef, useState} from 'react'
import { ResponsiveBar } from '@nivo/bar'
import { generateCountriesData } from '@nivo/generators'
import { ResponsiveLine } from '@nivo/line'
import { Tab } from 'semantic-ui-react'
import {FormattedMessage, FormattedNumber, injectIntl} from 'react-intl';

const curveOptions = ['linear', 'monotoneX', 'step', 'stepBefore', 'stepAfter'];

const CustomSymbol = ({ size, color, borderWidth, borderColor }) => (
  <g>
    <circle fill="#fff" r={size / 2} strokeWidth={borderWidth} stroke={borderColor} />
    <circle r={size / 5} strokeWidth={borderWidth} stroke={borderColor} fill={color} fillOpacity={0.35} />
  </g>
);


const LineChart =injectIntl( ({intl, data }) => (
      <ResponsiveLine
          enableGridY={true}
          enableGridX={true}

          data={data}
          margin={{ top: 50, right: 110, bottom: 50, left: 60 }}

             yScale={{ type: 'linear', stacked: false, min: 'auto', max: 'auto' }}

          xScale={{ type: 'point' }}
          colors={{ scheme: 'set1' }}
          useMesh={true}

          pointSymbol={CustomSymbol}
          pointSize={16}

          pointBorderWidth={1}
          pointBorderColor={{
            from: 'color',
            modifiers: [['darker', 0.3]],
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
              legend: 'Year',
              legendOffset: 36,
              legendPosition: 'middle'
          }}
          axisLeft={{
              orient: 'left',
              tickSize: 5,
              tickPadding: 5,
              tickRotation: 0,
              legend: 'count',
              legendOffset: -40,
              legendPosition: 'middle'
          }}

          pointSize={10}
          pointColor={{ theme: 'background' }}
          pointBorderWidth={2}
          pointBorderColor={{ from: 'serieColor' }}


          enablePointLabel={true}

          pointLabel={(s)=>intl.formatNumber(s.y/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0}) }
          pointLabelYOffset={-12}
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

const BarChart =injectIntl(({ data , intl/* see data tab */, keys,indexBy , groupMode, colors}) => {

return (
    <ResponsiveBar
        data={data}
        keys={keys}
        indexBy={indexBy}
        groupMode={groupMode}
        colors={colors}
        margin={{ top: 50, right: 130, bottom: 50, left: 60 }}
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
        borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
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
        label={(s)=>intl.formatNumber(s.value/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0}) }
        labelSkipWidth={12}
        labelSkipHeight={12}
        labelTextColor={"#FFF"}
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
    />

)}
)




const generateYearlyBarData = (data) => {
  let barData = []
  data.forEach(r => {
    const key = r.region;
    if (!barData[key]) {
      barData[key] = {};
    }
    if (r.povertyLevel != 'Not poor') {
      barData[key][r.year] = barData[key][r.year] ? barData[key][r.year] : 0 + r.percentage*100;
      barData[key]['region'] = r.region;
    }
  })
  return Object.keys(barData).map(k => barData[k]);
}



const generateStackedData = (data) => {

  let stackedData = []
  data.forEach(r => {
    const key = r.region;
    if (!stackedData[key]) {
      stackedData[key] = {};
    }
    stackedData[key]['region'] = r.region;
    stackedData[key][r.povertyLevel] = r.percentage *100;

  })
  return Object.keys(stackedData).map(k => stackedData[k]);
}

const generateLineData=(data)=>{

  const regions=Array.from(new Set(data.map(d=>d.region)))

  const lineData=regions.map(r=>{
    const subData=data.filter(d=>d.region==r).map(f=>{return {x:f.year, y:(f.percentage*100), level:f.povertyLevel}}).filter(f=>f.level!='Not poor')
    const series=Array.from(new Set(subData.map(r=>r.x))).map(year=>{
      let y=0;
      subData.filter(s=>s.x==year).forEach(val=>y=y+val.y)
      return {x:year, y:y}
    })

    return {id:r,  "color": "hsl(332, 70%, 50%)", data:series}

  })

      /*
      {
        id:'Dakar',
        data=[
        {x=2017, y=10},{x=2018, y=12},{x=2019), y=18},
      ]
      }
  */

    return Object.keys(lineData).map(k=>lineData[k])
}

const PovertyChart=({data,intl})=>{

  const yearlyData=generateYearlyBarData(data)
  const yearlyKeys=Array.from(new Set(data.map(d=>d.year)))
  const maxYear=yearlyKeys.sort()[yearlyKeys.length-1];

  const stackedData=generateStackedData(data.filter(d=>d.year==maxYear))
  const povertyCategories=Array.from(new Set(data.map(d=>d.povertyLevel)))


  const lineData=generateLineData(data)

    const panes = [
      {
        menuItem: 'Yearly Regional',
        render: () =>   <div className="indicators chart poverty"><BarChart data={yearlyData} keys={yearlyKeys} indexBy="region" groupMode="grouped" colors={{ scheme: 'red_yellow_blue' }}/></div>,
      },
      {
        menuItem: `${maxYear} Stacked`,
        render: () =>   <div className="indicators chart poverty"><BarChart data={stackedData} keys={povertyCategories} indexBy="region"  groupMode="stacked"
        colors={["#228E58","#C25E7F","#C15E50"]}/></div>,

      },
      {
        menuItem: 'Time Line',
        render: () => <div className="indicators chart poverty"><LineChart data={lineData}/></div>,
      }
    ]


  return (<Tab menu={{ pointing: true }} panes={panes} />)
}






export default injectIntl(PovertyChart)
