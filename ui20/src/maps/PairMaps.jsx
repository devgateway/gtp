

import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState, useEffect} from 'react'

import './map.scss'
import { Grid, Image } from 'semantic-ui-react'
import Map from './Map.jsx'
import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomGroupedDropDown, CustomFilterDropDown,items2options} from '../indicators/Components'
import {PngExport} from '../indicators/Components'

var regions = require('../json/regions.json'); //with path
var departments = require('../json/departments.json'); //with path

const getOptions=(data)=> {

    return data.map(d=>{return {
      key:d.id ,
      text:d.name,
      description:d.description ,
      leftMap:d.leftMap,
      rightMap:d.rightMap,
      measure:d.measure,
      reverse:d.reverse,
      source:d.source,

    }
    })
}


const getGroupedOptions=(data)=> {
  const groups=[...new Set(data.map(d=>d.indicatorGroup))]

  const level1=groups.map(g=>{
      const level2=  data.filter(d1=>d1.indicatorGroup==g).map(d=>{return {
        key:d.id ,
        text:d.name,
        description:d.description ,
        leftMap:d.leftMap,
        rightMap:d.rightMap,
        measure:d.measure,
        reverse:d.reverse,
        source:d.source,

      }
      });
        return {
          group:g,
          options:level2
        };
  })


  return level1;
}


const getMapData=(data,id)=>{

  return data.filter(d=>d.id==id)[0]
}


export const joinData = (json, data = [], getCode,getName) => {
  if (data) {

      json.features.forEach(f=>{



        var rData=data.stats.filter(s=>s.code==getCode(f))
        Object.assign(f.properties,{
            'NAME':getName(f)})

        if (rData.length >0){
          let props=rData[0]

          const newProps={
              'indicator':data.name,
              'value':props.value,
              'year':data.year,
              'minValue': data.minValue,
              'maxValue': data.maxValue,
              'measure': data.measure
          }
          Object.assign(f.properties,newProps)
        }
      })
  }
  return json;
}

const getOptionByKey=(options, key)=>{
  return options.filter(p=>p.key==key)[0]
}
//https://observablehq.com/@d3/color-schemes



const DropDownLabel=()=>(<FormattedMessage id = "gis.indicator.name" defaultMessage = "Indicator"  > </FormattedMessage>)

const PairOfMaps=({intl,id, data, selection,level})=>{


if (data){

    const colors=[
    {key:'Blues' ,text: intl.formatMessage(messages.blues)},
    {key:'Greens' ,text:  intl.formatMessage(messages.greens)},
    {key:'Greys' ,text:  intl.formatMessage(messages.greys)},
    {key:'Oranges' ,text:  intl.formatMessage(messages.oranges)},
    {key:'Purples' ,text:  intl.formatMessage(messages.purples)},
    {key:'Reds' ,text:  intl.formatMessage(messages.reds)},
    {key:'BuGn' ,text:  intl.formatMessage(messages.blue_to_green)},
    {key:'BuPu' ,text:  intl.formatMessage(messages.blue_to_purple)},
    {key:'OrRd' ,text:  intl.formatMessage(messages.orange_to_red)}]


    const options=getOptions(data.toJS())
    const groupedOptions=getGroupedOptions(data.toJS())

    const defLeft =  options.find(o=>o.leftMap==true) || options[0]
    const defRigth=  options.find(o=>o.rightMap==true)  || options[0]

    const [left, setLeft] = useState([defLeft.key]);
    const [right, setRight] = useState([defRigth.key]);

    const [leftColor, setLeftColor] = useState(['Reds']);
    const [rightColor, setRightColor] = useState(['Blues']);

    const [selection, setSelection]= useState(null);



    const leftIndicator=getOptionByKey(options,left[0])
    const rightIndicator=getOptionByKey(options,right[0])

    const leftData=data && left? getMapData(data.toJS(),left):null
    const rightData=data && right? getMapData(data.toJS(),right):null

    const shapes=(level=='region')?regions:departments;
    const getCode=(level=='region')?(f)=>f.properties.HASC_1.substr(3):(f)=>f.properties.HASC_2.substr(6,2)

    const getName=(level=='region')?(f)=>f.properties.NAME_1:(f)=>f.properties.NAME_2




    const [leftGeoJson, setLeftGeoJson] = useState(leftData?joinData(Immutable.fromJS(shapes).toJS(), Immutable.fromJS(leftData).toJS(),getCode,getName):null);

    const [rightGeojson, setRightGeojson] = useState(rightData?joinData(Immutable.fromJS(shapes).toJS(), Immutable.fromJS(rightData).toJS(),getCode,getName):null);


    useEffect(() => {
      setLeftGeoJson(joinData(Immutable.fromJS(shapes).toJS(), getMapData(data.toJS(),left),getCode,getName))
    }, [left]);

    useEffect(() => {
      setRightGeojson(joinData(Immutable.fromJS(shapes).toJS(), getMapData(data.toJS(),right),getCode,getName))
    }, [right]);




    return (
      <div className="pairs maps">
      <div className="export area">
        <PngExport name={intl.formatMessage({id:'indicators.chart.aoi.title'})} id={id} filters={[]} includes={[]}/>

      </div>

      <Grid  className="pairs maps" columns={2} id={id}>
      <Grid.Row className="png exportable">
      <Grid.Column>
      <div className="wrapper">
          <div className="gis filter container  ">
              <div className="gis filter item indicator">
                <CustomGroupedDropDown className="dropdown indicator" single options={groupedOptions} onChange={s => {
                  if(s.length>0){
                    setLeft(s)
                  }
                }} selected={left} text={""}/>
                </div>
                <div className="gis filter item color">
                <CustomFilterDropDown className="dropdown colors" single options={colors} onChange={s => {
                  if (s.length>0){
                    setLeftColor(s)
                  }
                }} selected={leftColor} text={""}/>
              </div>
            </div>
               <Map
                 name={leftIndicator.text}
                 selection={selection}
                 key={leftIndicator.id}
                 max={leftData.maxValue}
                 min={leftData.minValue}
                 measure={leftData.measure}
                 reverse={leftIndicator.reverse}
                 source={leftIndicator.source}

                 intl={intl}
                 json={leftGeoJson}
                 color={leftColor}
                 indicator={leftIndicator}

                 sideColor={rightColor}
                 onClick={e=>setSelection(selection&&selection.fid==e.fid?null:e)}/>
            </div>
         </Grid.Column>
         <Grid.Column>
         <div className="map wrapper">

             <div className="gis filter container">
                   <div className="gis filter item indicator">
                   <CustomGroupedDropDown className="dropdown indicator" single options={groupedOptions}
                    onChange={s => {
                      if(s.length>0){
                        setRight(s)
                      }
                   }} selected={right} text={""}/>
                   </div>

                   <div className="gis filter item color">
                   <CustomFilterDropDown className="dropdown colors"  single options={colors}
                     onChange={s => {
                       if (s.length>0){
                         setRightColor(s)
                       }
                      }} selected={rightColor} text={""}/>
                 </div>
               </div>
                 <Map
                      name={rightIndicator.text}
                      selection={selection}
                      key={rightIndicator.id}
                      max={rightData.maxValue}
                      min={rightData.minValue}
                      intl={intl}
                      indicator={rightIndicator}
                      json={rightGeojson}
                      color={rightColor}

                      reverse={rightData.reverse}
                      measure={rightData.measure}
                      source={rightData.source}

                      sideColor={leftColor}
                      onClick={e=>setSelection(selection&&selection.fid==e.fid?null:e)}/>

            </div>
         </Grid.Column>
         </Grid.Row>
       </Grid>

     </div>)
   }else{
     return null
   }

}


export default injectIntl(PairOfMaps )
