

import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState, useEffect} from 'react'


import { Grid, Image } from 'semantic-ui-react'
import {LineChart} from './Charts.jsx'
import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'
import {PngExport} from '../indicators/Components'

var regions = require('../json/regions.json'); //with path

const getOptions=(data, measure)=> {

    return data.filter(d=>measure?d.measure==measure:true).map(d=>{return {key:d.name ,text:d.name}})
}


const getDataByKeys=(data, keys)=>{

  return data.filter(p=>keys.indexOf(p.get('name')) > -1)
}
//https://observablehq.com/@d3/color-schemes



const DropDownLabel=()=>(<FormattedMessage id = "national.indicator.name" defaultMessage = "Indicator"  > </FormattedMessage>)

const PairOfMaps=({intl,id, data})=>{
  if (data){
    const colors=[
    {key:'accent' ,text: intl.formatMessage(messages.accent)},
    {key:'dark2' ,text:  intl.formatMessage(messages.dark2)},
    {key:'paired' ,text:  intl.formatMessage(messages.paired)},
    {key:'pastel1' ,text:  intl.formatMessage(messages.pastel1)},
    {key:'pastel2' ,text: intl.formatMessage(messages.pastel2)},
    {key:'set1' ,text:  intl.formatMessage(messages.set1)},
    {key:'set2' ,text:  intl.formatMessage(messages.set2)},
    {key:'set3' ,text:  intl.formatMessage(messages.set3)},
  ]


    const options=getOptions(data.toJS(),"%")

    const defaultSelection =  options.find(o=> options[0])
    const [currentSelection, setSeCurrentSelection] = useState([defaultSelection.key]);
    const [color, setColor] = useState(['accent']);
    const [chartData, setChartData] = useState(null);



    useEffect(() => {
      if (data){
            setChartData(getDataByKeys(data,currentSelection) )
      }
    }, [currentSelection]);




    return (
      <div className="national chart container">


                    <div className="national filter container">

                          <div className="national filter item indicator">

                            <CustomFilterDropDown className="dropdown indicator"  options={options} onChange={s => {

                              if(s.length>0){
                                setSeCurrentSelection(s)
                              }
                            }} selected={currentSelection} text={""}/>
                            </div>
                            <div className="national filter item color">

                            <CustomFilterDropDown className="dropdown colors" single options={colors} onChange={s => {
                              if (s.length>0){
                                setColor(s)
                              }

                            }} selected={color} text={""}/>


                          </div>

                        </div>

              {chartData?<LineChart data={chartData.toJS()} color={color[0]}/>:null}


       
     </div>)
   }else{
     return null
   }

}


export default injectIntl(PairOfMaps )
