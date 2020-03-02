

import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState, useEffect} from 'react'

import './map.scss'
import { Grid, Image } from 'semantic-ui-react'
import Map from './Map.jsx'
import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'
import {PngExport} from '../indicators/Components'

var regions = require('../json/regions.json'); //with path

const getOptions=(data)=> {

    return data.map(d=>{return {key:d.id ,text:d.name}})
}


const getMapData=(data,id)=>{
  return data.filter(d=>d.id==id)[0]
}


export const joinData = (json, data = [], intl) => {
  if (data) {

      json.features.forEach(f=>{

        var rData=data.stats.filter(s=>s.code==f.properties.HASC_1.substr(3))
        if (rData.length >0){
          let props=rData[0]
          const newProps={
              'indicator':data.name,
              'value':props.value,
              'year':data.year,
              'minValue': data.minValue,
              'maxValue': data.maxValue
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

const PairOfMaps=({intl,id, data, selection})=>{
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


    const [leftGeoJson, setLeftGeoJson] = useState(leftData?joinData(Immutable.fromJS(regions).toJS(), Immutable.fromJS(leftData).toJS()):null);
    const [rightGeojson, setRightGeojson] = useState(rightData?joinData(Immutable.fromJS(regions).toJS(), Immutable.fromJS(rightData).toJS()):null);


    useEffect(() => {
      setLeftGeoJson(joinData(Immutable.fromJS(regions).toJS(), getMapData(data.toJS(),left)))
    }, [left]);

    useEffect(() => {
      setRightGeojson(joinData(Immutable.fromJS(regions).toJS(), getMapData(data.toJS(),right)))
    }, [right]);


    
    return (
      <div>
        <div>

        </div>
        <div className="map description">
          <p>
            <FormattedMessage id="pair.maps.description" defaultMessage="Use both maps to compare two indicators "></FormattedMessage>
          </p>
          <div className="icons container">
          <PngExport id={id} name="map_image"/>
          </div>
        </div>


      <Grid  className="pairs maps" columns={2} id={id}>


      <Grid.Row className="png exportable">

      <Grid.Column>
      <div className="gis filter container  ">
          <div className="gis filter item indicator">

            <CustomFilterDropDown className="dropdown indicator" single options={options} onChange={s => {


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
             key="map1"
             name={leftIndicator.name}
             selection={selection}
             key={leftIndicator.id}
             max={leftData.maxValue}
             min={leftData.minValue}
             intl={intl}
             json={leftGeoJson}
             color={leftColor}
            indicator={leftIndicator}
             sideColor={rightColor}
             onClick={e=>setSelection(selection&&selection.fid==e.fid?null:e)}/>

         </Grid.Column>
         <Grid.Column>
         <div className="gis filter container">
               <div className="gis filter item indicator">
               <CustomFilterDropDown className="dropdown indicator" single options={options}
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

             <Map key="map2"
                  name={rightIndicator.name}
                  selection={selection}
                  key={rightIndicator.id}
                  max={rightData.maxValue}
                  min={rightData.minValue}
                  intl={intl}

                 indicator={rightIndicator}
                  json={rightGeojson}
                  color={rightColor}
                   sideColor={leftColor}
                   onClick={e=>setSelection(selection&&selection.fid==e.fid?null:e)}/>
         </Grid.Column>
         </Grid.Row>
       </Grid>
     </div>)
   }else{
     return null
   }

}


export default injectIntl(PairOfMaps )
