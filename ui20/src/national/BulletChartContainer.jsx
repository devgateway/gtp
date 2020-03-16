

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

    let maxValue= indicator.yearValues.map(z=>z.value).sort().pop()
    const referenceValue=indicator.referenceValue;
    const targetValue=indicator.targetValue;

    maxValue=referenceValue && maxValue < referenceValue?referenceValue:maxValue
    maxValue=targetValue && maxValue < targetValue?targetValue:maxValue

    const markers=[] //adding reference valued as marker

    const ranges=[]


    if(indicator.referenceValue){
      markers.push(indicator.referenceValue)



    }else{
      markers.push(-1000)

    }

    if(indicator.targetValue){
      markers.push(indicator.targetValue)

    }



    const chartData= indicator.yearValues.map(yv=>{

      return {
            id: yv.year+" ",
            measure:indicator.measure,
            ranges:[0, maxValue],
            markers,
            measures: [yv.value],
          }
        });

    const refData=[]

    if(indicator.referenceValue){
      refData.push(  {
              id: indicator.referenceYear+" ",
              measure:indicator.measure,
              ranges,
              markers,
              measures: [indicator.referenceValue],
            })
    }
            return  {refData,data:chartData,metadata:indicator }
    }
}



const DropDownLabel=()=>(<FormattedMessage id = "national.indicator.name" defaultMessage = "Indicator"  > </FormattedMessage>)

const PairOfMaps=({intl ,id, data, n})=>{

  if (data){



    const options=getOptions(data.toJS())

    const defaultSelection = options[n > options.length-1?options.length-1:n ]

    const [currentSelection, setSeCurrentSelection] = useState([defaultSelection.key]);
    const [color, setColor] = useState(['accent']);
    const [chartData, setChartData] = useState(null);

    useEffect(() => {
      if (data){
          setChartData(getDataByKeys(data,currentSelection) )
      }
    }, [currentSelection,data]);





    return (
      <div className="national chart container">
            <div className="national filter container">

                <CustomFilterDropDown single className="dropdown indicator"  options={options} onChange={s => {
                    if(s.length>0){
                                setSeCurrentSelection(s)
                              }
                            }} selected={currentSelection} text={""}/>
                            </div>



                 {chartData?<Bullet {...chartData}  color={color[0]}/>:null}

     </div>)
   }else{
     return null
   }

}


export default injectIntl(PairOfMaps )
