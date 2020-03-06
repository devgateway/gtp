
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import './map.scss'
import {loadGISData} from '../modules/Gis'
import { Grid, Label , Tab} from 'semantic-ui-react'

import Map from './Map.jsx'
import PairOfMaps from './PairMaps.jsx'


import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'


class GIS extends Component {
  constructor(props) {
    super(props);
     this.state = { nMapsRegion: 1, nMapsDepartments: 1 };
     this.addnewOne=this.addnewOne.bind(this)
     this.removeLast=this.removeLast.bind(this)
  }

  componentDidMount(){
    const lang=this.props.intl.locale
    this.props.onLoad(lang)
  }

  addnewOne(wich){
    if(wich=='region'){
      this.setState({nMapsRegion :this.state.nMapsRegion +1, nMapsDepartments:this.state.nMapsDepartments})
    }else{
      this.setState({nMapsRegion :this.state.nMapsRegion - 1, nMapsDepartments:this.state.nMapsDepartments +1})

    }
  }

  removeLast(wich){
    if(wich=='region'){
      this.setState({nMapsRegion :this.state.nMapsRegion -1, nMapsDepartments:this.state.nMapsDepartments})
    }else{
      this.setState({nMapsRegion :this.state.nMapsRegion -1, nMapsDepartments:this.state.nMapsDepartments +1})

    }
  }


  render() {
    const {regionalIndicator, departamentalIndicators, intl, onExport} = this.props

    const {nMapsRegion,nMapsDepartments}=this.state

    const range1 = Array.from({length: nMapsRegion}, (value, key) => key)
    const range2 = Array.from({length: nMapsDepartments}, (value, key) => key)


    const panes = [
      {
        menuItem: 'Regional',
        render: () => <Tab.Pane attached='top'>

        {range1.map(n=>{
            return <PairOfMaps key={`map.region_${n}`} level="region" id={`map.region_${n}`} data={regionalIndicator}/>
        })}

        <div className="aling rigth buttons">
          <Label className="add"  color="olive" onClick={e=>this.addnewOne('region')}>
          <FormattedMessage id='gis.page.add' defaultMessage="Add new pair of maps"/></Label>

          {range1.length > 1&&<Label className="remove" color="black" onClick={e=>this.removeLast('region')}><FormattedMessage id='gis.page.remove' defaultMessage="Remove last one"/></Label>}
         </div>

        </Tab.Pane>,
      },
      {
        menuItem: 'Departamental',
        render: () => <Tab.Pane attached='top'>

                {range2.map(n=>{
                    return <PairOfMaps key={`map.department_${n}`} level="department" id={`map.department_${n}`} data={departamentalIndicators}/>
                })}
                <div className="aling rigth buttons">
                  <Label className="add"  color="olive" onClick={e=>this.addnewOne('repartment')}><FormattedMessage id='gis.page.add' defaultMessage="Add new pair of maps"/></Label>
                  {range2.length > 1&&<Label className="remove" color="black" onClick={e=>this.removeLast('department')}><FormattedMessage id='gis.page.remove' defaultMessage="Remove last one"/></Label>}
                 </div>

        </Tab.Pane>,
      },

    ]



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



            <Tab panes={panes} />
         </div>
      )
  }
}


const mapStateToProps = state => {

  const regionalIndicator=state.getIn(['gis','region']);
  const departamentalIndicators=state.getIn(['gis','department']);

  return {regionalIndicator,departamentalIndicators}
}

const mapActionCreators = {
  onLoad:loadGISData};


export default injectIntl(connect(mapStateToProps, mapActionCreators)(GIS));
