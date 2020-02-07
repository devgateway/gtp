
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import './map.scss'
import {loadGISData} from '../modules/Gis'
import { Grid, Label } from 'semantic-ui-react'

import Map from './Map.jsx'
import PairOfMaps from './PairMaps.jsx'


import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'


class GIS extends Component {
  constructor(props) {
    super(props);
     this.state = { nMaps: 1 };
     this.addnewOne=this.addnewOne.bind(this)
     this.removeLast=this.removeLast.bind(this)
  }

  componentDidMount(){
    this.props.onLoad()
  }

  addnewOne(){
    this.setState({nMaps :this.state.nMaps +1})
  }

  removeLast(){
    this.setState({nMaps :this.state.nMaps -1})
  }


  render() {
    const {data, intl, onExport} = this.props

    const {nMaps}=this.state

    const range = Array.from({length: nMaps}, (value, key) => key)
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
            {range.map(n=>{
                return <PairOfMaps key={n} id={`map.pairs${n}`} data={data}/>
            })}

            <div className="aling rigth">
         <Label  color="olive" onClick={this.addnewOne}>Add new pairs of maps</Label>
          {nMaps > 1&&<Label color="black" onClick={this.removeLast}>Remove last one</Label>}
         </div>

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
