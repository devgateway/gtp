

import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import AD3Map from './Map'
import './map.scss'
import { Grid, Image } from 'semantic-ui-react'
import Map from './Map.jsx'
import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'
var regions = require('../json/regions.json'); //with path



export default const PairOfMaps=injectIntl((intl, data, indicator1,selection, indicator2,selection1, selection2,onChangeSelection,json1,json2})=>{

  debugger;

  return (  <Grid columns={2}>

    <Grid.Column>
    <div className="gis filter container  ">
        <div className="gis filter item">
        {data&&<CustomFilterDropDown single
        options={data.map(d=>{return {key:d.id ,text:d.name}})}
        onChange={s => onChangeSelection('selection1',s)}
        selected={selection1} text={<FormattedMessage id = "gis.indicator.name" defaultMessage = "Indicator"  > </FormattedMessage>} />}
        </div>

      </div>

         { indicator1 &&
         <Map
         key="map1"
         name={indicator1.name}
         selection={selection}
         key={indicator1.id}
         max={indicator1.maxValue}
         min={indicator1.minValue}
         intl={intl}
         json={json1}
         color="Reds"
         onClick={this.onMapClick}/>}

       </Grid.Column>
       <Grid.Column>
       <div className="gis filter container  ">
           <div className="gis filter item">
           {data&&<CustomFilterDropDown single
            options={data.map(d=>{return {text:d.name, key:d.id}})}
           onChange={s => onChangeSelection('selection2',s)}
           selected={selection2} text={<FormattedMessage id = "gis.indicator.name" defaultMessage = "Indicator"  > </FormattedMessage>} />}
           </div>
         </div>

         { indicator2 &&
           <Map key="map2"
           name={indicator2.name}
           selection={state.selection}
           key={indicator2.id}
           max={indicator2.maxValue}
           min={indicator2.minValue}
           intl={intl}
          json={json2}
          color="Blues"
          onClick={this.onMapClick}/>}
       </Grid.Column>

     </Grid>))

}
