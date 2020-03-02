

import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState, useEffect} from 'react'


import { Grid, Image } from 'semantic-ui-react'
import {Bullet} from './Charts.jsx'
import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'
import {PngExport} from '../indicators/Components'

var regions = require('../json/regions.json'); //with path

const getOptions=(data, percents)=> {

    return data.map(d=>{return {key:d.id ,text:d.name}})
}




const getDataByKeys=(data, keys)=>{
  const elements=data.filter(p=>keys.indexOf(p.get('id')) > -1)

  if (elements){

    const indicator=elements.get(0).toJS()
    const maxValue= indicator.yearValues.map(z=>z.value).sort().pop()
    const referenceValue=indicator.referenceValue;
    const markers=[] //adding reference valued as marker

    const ranges=[]


    if(indicator.referenceValue){
      markers.push(indicator.referenceValue)
    }

    if (referenceValue){

      if(referenceValue < maxValue){
          const newMaxValue=  maxValue-referenceValue
          ranges.push(referenceValue/100*0)
          ranges.push(referenceValue/100*25)
          ranges.push(referenceValue/100*50)

          ranges.push(referenceValue/100*100)
          ranges.push(maxValue)



      }else{

          ranges.push(referenceValue/100*0)
          ranges.push(referenceValue/100*25)
          ranges.push(referenceValue/100*50)
          ranges.push(referenceValue/100*100)
      }

    }else{
        ranges.push(maxValue/100*0)
        ranges.push(maxValue/100*20)
        ranges.push(maxValue/100*40)
        ranges.push(maxValue/100*70)
        ranges.push(maxValue/100*100)
      }

    const chartData= indicator.yearValues.map(yv=>{
      return {
            id: yv.year+" ",
            measure:'%',
            ranges,
            markers,
            measures: [yv.value],
          }
        });

    const refData=[]

    if(indicator.referenceValue){
      refData.push(  {
              id: indicator.referenceYear+" ",
              measure:'%',
              ranges,
              markers,
              measures: [indicator.referenceValue],
            })
    }
      return  {refData,data:chartData}
    }
}



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


    const options=getOptions(data.toJS())

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

                <CustomFilterDropDown single className="dropdown indicator"  options={options} onChange={s => {
                    if(s.length>0){
                                setSeCurrentSelection(s)
                              }
                            }} selected={currentSelection} text={""}/>
                            </div>


                 {chartData?<Bullet {...chartData} color={color[0]}/>:null}

     </div>)
   }else{
     return null
   }

}


export default injectIntl(PairOfMaps )
