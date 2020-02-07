

import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState, useEffect} from 'react'

import './map.scss'
import { Grid, Image } from 'semantic-ui-react'
import Map from './Map.jsx'
import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'
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

        var rData=data.stats.filter(s=>s.regionCode==f.properties.HASC_1.substr(3))
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
const colors=[
  {key:'Blues' ,text:'Blues'},
{key:'Greens' ,text:'Greens'},
{key:'Greys' ,text:'Greys'},
{key:'Oranges' ,text:'Oranges'},
{key:'Purples' ,text:'Purples'},
{key:'Reds' ,text:'Reds'},
{key:'BuGn' ,text:'Blue to Green'},
{key:'BuPu' ,text:'Blue to Purple'},
{key:'OrRd' ,text:'Orange to Red'}]



const DropDownLabel=()=>(<FormattedMessage id = "gis.indicator.name" defaultMessage = "Indicator"  > </FormattedMessage>)

const PairOfMaps=({intl, data, selection})=>{
  if (data){

    const options=getOptions(data)

    const [left, setLeft] = useState([options[0].key]);
    const [right, setRight] = useState([options[0].key]);
    const [leftColor, setLeftColor] = useState(['Reds']);
    const [rightColor, setRightColor] = useState(['Blues']);

    const [selection, setSelection]= useState(null);



    const leftIndicator=getOptionByKey(options,left[0])
    const rightIndicator=getOptionByKey(options,right[0])

    const leftData=data && left? getMapData(data,left):null
    const rightData=data && right? getMapData(data,right):null


    const [leftGeoJson, setLeftGeoJson] = useState(leftData?joinData(Immutable.fromJS(regions).toJS(), leftData):null);
    const [rightGeojson, setRightGeojson] = useState(rightData?joinData(Immutable.fromJS(regions).toJS(), rightData):null);


    useEffect(() => {
      debugger;
      setLeftGeoJson(joinData(Immutable.fromJS(regions).toJS(), leftData))
    }, [left]);

    useEffect(() => {
      setRightGeojson(joinData(Immutable.fromJS(regions).toJS(), leftData))
    }, [right]);



    return (<Grid  className="pairs maps" columns={2}>

      <Grid.Column>
      <div className="gis filter container  ">
          <div className="gis filter item indicator">

            <CustomFilterDropDown className="dropdown indicator" single options={options} onChange={s => {
                setSelection(null)
              setLeft(s)

            }} selected={left} text={""}/>
            </div>
            <div className="gis filter item color">

            <CustomFilterDropDown className="dropdown colors" single options={colors} onChange={s => {
                setLeftColor(s)
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
           onClick={setSelection}/>

         </Grid.Column>
         <Grid.Column>
         <div className="gis filter container  ">
               <div className="gis filter item indicator">
               <CustomFilterDropDown className="dropdown indicator" single options={options} onChange={s => {

                 setSelection(null)
                 setRight(s)
               }} selected={right} text={""}/>
               </div>
                  <div className="gis filter item color">
               <CustomFilterDropDown className="dropdown colors"  single options={colors} onChange={s => {
                   setRightColor(s)
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
             json={rightGeojson}
              color={rightColor}
             onClick={setSelection}/>
         </Grid.Column>

       </Grid>)
   }else{
     return null
   }

}


export default injectIntl(PairOfMaps )
