import React, {Component, createRef, useState} from 'react'
import { ResponsiveBar } from '@nivo/bar'
import { ResponsiveLine } from '@nivo/line'
import { ResponsiveBullet } from '@nivo/bullet'
import { BasicTooltip } from '@nivo/tooltip'
import * as d3 from "d3";
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
              ]}/>
          </div>
    ))
})

var tooltip = d3.select("body")
    .append("div")
      .style("opacity", 0)
      .attr("class", "tooltip")





var showTooltip = function(e,html,color, xOffset=0, yOffset=0) {

  tooltip
    .transition()
    .duration(200)

  tooltip
    .style("opacity", 1)
    .attr("class","tooltip")
    .html("<div class='square' style='background-color:"+color+"'></div><div class='label'>" +html+"</div>")
    .style("left", ((e.pageX  +xOffset)  + "px"))
    .style("top", ((e.pageY+yOffset )+  "px"))
}


var moveTooltip = function(e, xOffset=0, yOffset=0) {

  tooltip
  .style("left", ((e.pageX  + xOffset)  + "px"))
  .style("top", ((e.pageY+ yOffset )+  "px"))
}


var hideTooltip = function(d) {
  tooltip
    .transition()
    .duration(200)
    .style("opacity", 0)
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
        onMouseEnter={e=>null}
        onMouseMove={e=>null}
        onMouseLeave={e=>null}
    />
)


  const getConditionalColor=(condition)=>{

       switch (condition) {
         case 'GOOD':
          return '#addd8e'
         case 'REGULAR':
          return '#fad976'
         case 'BAD':
         return '#ef6548'
         case 'NONE':
         return '#bababa'
           break;

       }

  }


const getCondition = (reverse, current, target, reference) => {

  let condition = 'NONE'
  const compare = (a, b) => {
    return (reverse) ? a < b : a > b
  }



  if (target && reference) {
    //current has meet target or is better than target
    if (current == target || compare(current, target)) {
        condition = 'GOOD'
    } else {
      //current is worse  than taget but equal or better than reference
      if (current == reference || compare(current, reference)) {
        condition = 'REGULAR'
      } else {
        condition = 'BAD'
      }

    }
  } else if (target && !reference) {
    //if there no ference just check it agains target
    if (current == target || compare(current, target)) {
      condition = 'GOOD'
    } else {
      condition = 'BAD'
    }
  } else if (!target && reference) {

    //if there no target just check it agains reference
    if (current == reference || compare(current, reference)) {
      condition = 'GOOD'
    } else {
      condition = 'BAD'
    }
  } else {
    condition = 'NONE'
  }

  return condition

}


export const Bullet =injectIntl(({ data , metadata ,refData, intl, keys,indexBy , groupMode, color}) => {

  const CustomMeasure = (props) =>{

    const { x, y, width,metadata, height, onMouseEnter, onMouseMove, onMouseLeave } = props
    let color2='#FFF'
    const reverse=metadata.reverse;
    const current=props.data.v1;
    const target=metadata.targetValue;
    const reference = metadata.referenceValue

    const condition=getCondition(reverse,current,target,reference)
    const color=getConditionalColor(condition);


    return (
      <g onMouseEnter={e=>{

        const index=[...e.target.parentElement.parentElement.parentElement.parentElement.getElementsByClassName("measure")].indexOf(e.target)
        const d=data[index]
        var dElement=d3.select(e.target)
        if(d){
        const {measure,measures,id}=d;
          debugger;
        showTooltip(e,`${intl.formatMessage(messages.national_indicators_actual)}: ${id} - <b>${measures[0]}${measure}</b>  `, color,0,0)
      }else{

      }
      }}
      onMouseMove={e=>moveTooltip(e,30,0)}
      onMouseLeave={hideTooltip}
      >
      <rect
          className="measure"
          x={x + 2}
          y={y + 2}
          rx={height / 4}
          ry={height / 2}
          width={width - 4}
          height={height - 4}
          fill={color}

      />
          </g>
  )
  }
  const CustomMarker = (props) => {
      const { x, y,size,value, index,width,rotation,onClick, height, color, onMouseEnter, onMouseMove, onMouseLeave , intl} = props

      return (

        <g
          className="marker"
          onMouseEnter={e=>{

              showTooltip(e,`${index==1?`Target`:`Reference ${refData[0].id}`} <b>${value} ${metadata.measure}</b> `,color)
          }}
          onMouseMove={e=>moveTooltip(e,0,0)}
          onMouseLeave={hideTooltip}
          >

          <rect   transform={`rotate(${rotation}, ${x}, ${y})`} x={x} y={y - size / 2} width="7" height={size}
                        fill={color}
                        rx="5"
                        ry="5"/>


        </g>
    )
  }

  return(
  <div className="national chart wrapper">

          <div className="yLeyend">{intl.formatMessage(messages.year)}</div>
          <div className="national chart">

                    <ResponsiveBullet
                          data={data}
                          margin={{ top: 20, right: 20, bottom: 30, left: 70 }}
                          spacing={70}
                          titleAlign="start"
                          titleOffsetX={-70}
                          measureSize={0.2}
                          measureSize={0.4}
                          titleOffsetX={-41}
                          animate={true}
                          motionStiffness={90}
                          motionDamping={12}
                          rangeColors={["#d1e5f0"]}
                          rangeComponent={(props)=>CustomRange({...props, metadata})}
                          measureComponent={(props)=>CustomMeasure({...props, metadata})}
                          measureColors={["#b2182b","#a6dba0"]}
                          markerSize={.9}
                          markerColors={["#f88d59","#72a9e0"]}
                          markerComponent={CustomMarker}

                      />

          </div>
          <div className="xLeyend">{metadata.name}  ({metadata.measure})</div>
          <div className="marketLegend">
            <div className="market reference"><FormattedMessage id="national.indicator.chart.legend.reference" defaultMessage="Reference data"/></div>
            <div className="market target"><FormattedMessage id="national.indicator.chart.legend.target" defaultMessage="Target data"/></div>
          </div>

          <div className="measureLegend">
            <div className="measure good"><FormattedMessage id="national.indicator.chart.legend.good" defaultMessage="Target and/or reference value reached / exceeded"/></div>
            <div className="measure regular"><FormattedMessage id="national.indicator.chart.legend.regular" defaultMessage="Actual value is behind target value and ahead of reference value"/></div>
            <div className="measure bad"><FormattedMessage id="national.indicator.chart.legend.bad" defaultMessage="Actual value is behind target and/or reference value"/></div>
            <div className="measure none"><FormattedMessage id="national.indicator.chart.legend.none" defaultMessage="There's no reference or target values to calculate indicator status"/></div>
          </div>
          <div className="description">{metadata.description}</div>
    </div>)
})
