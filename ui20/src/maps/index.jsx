
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import './map.scss'
import {loadGISData} from '../modules/Gis'
import { Grid, Image } from 'semantic-ui-react'

import Map from './Map.jsx'
import PairOfMaps from './Map.jsx'


import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'
var regions = require('../json/regions.json'); //with path

var i=0

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

const getMapData=(data,id)=>{
  return data.filter(d=>d.id==id)[0]
}

class GIS extends Component {
  constructor(props) {
    super(props);
    this.state = { selection1:[], selection2:[] };
    this.onChangeSelection=this.onChangeSelection.bind(this)
    this.onMapClick=this.onMapClick.bind(this)
  }

  componentDidMount(){
    this.props.onLoad()
  }

  onChangeSelection(selector, value){
    const newState={}
    newState[selector]=value
    this.setState(Object.assign({selection:null},this.state,newState))
  }

  onMapClick(props) {
    this.setState({selection: props})
  }

  render() {
    const {data,intl, onExport} = this.props
    const { selection1, selection2,selection }=this.state;
    const indicator1=data && selection1.length ? getMapData(data,selection1[0]):null
    const indicator2=data && selection2.length ? getMapData(data,selection2[0]):null
    const json1=joinData(Immutable.fromJS(regions).toJS(), indicator1)
    const json2=joinData(Immutable.fromJS(regions).toJS(), indicator2)

    const pairOptions={
      indicator1,
      indicator2,
      selection1,
      selection2,
      selection,
      data,
      json1,
      json2
    }

    return (
      <div className="gis container">
          <div className="gis title">
            <p>
              <FormattedMessage id="gis.page.title" defaultMessage="Gis Data"></FormattedMessage>
            </p>
          </div>

          <div className="gis description">
            <p><FormattedMessage id='gis.page.description' defaultMessage="The GIS page will display some indicators  that have been preloaded by each responsible partner organization.
            The site will also display, non-official data sources that users can access by clicking on the links provided. Where available, a given dataset will be displaying a link that will connect the ANSD data repository when users can consult reports, studies and other metadata related to a specific dataset."/></p>
          </div>

            <PairOfMaps {...pairOptions}/>
         <br/>
         <br/>
         </div>
      )
  }
}


const mapStateToProps = state => {
  const data=state.getIn(['gis','data']);
  return {data}
}

const mapActionCreators = {onLoad:loadGISData};


export default injectIntl(connect(mapStateToProps, mapActionCreators)(GIS));
