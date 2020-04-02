
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import React, {Component} from 'react'

import {loadGISData} from '../modules/Gis'
import {  Label} from 'semantic-ui-react'

import PairOfMaps from './PairMaps.jsx'



class GIS extends Component {
  constructor(props) {
    super(props);
     this.state = { mapCount: 1 };
     this.addnewOne=this.addnewOne.bind(this)
     this.removeLast=this.removeLast.bind(this)
  }

  componentDidMount(){
    const lang=this.props.intl.locale
    this.props.onLoad(lang)
  }

  addnewOne(wich){
      this.setState({mapCount :this.state.mapCount +1})

  }

  removeLast(wich){
      this.setState({mapCount :this.state.mapCount -1})

  }


  render() {
    const {regionalIndicator} = this.props

    const {mapCount}=this.state

    const range = Array.from({length: mapCount}, (value, key) => key)






    return (
      <div className="gis container">
          <div className="gis title">
            <p>
              <FormattedMessage id="gis.regional.title" defaultMessage="Gis Data"></FormattedMessage>
            </p>
          </div>

          <div className="gis description">
            <p><FormattedMessage id='gis.regional.description' defaultMessage="The GIS page will display some indicators  that have been preloaded by each responsible partner organization.
            The site will also display, non-official data sources that users can access by clicking on the links provided. Where available, a given dataset will be displaying a link that will connect the ANSD data repository when users can consult reports, studies and other metadata related to a specific dataset."/></p>
          </div>

          <div className="gis description">
            <p><FormattedMessage id='gis.usage_text' defaultMessage="Select the indicator(s) from the available options in the dropdown menus. You can also select the color scheme to display indicator variations where applicable."/></p>
          </div>

          <div className="clear block">
          {range.map(n=>{
              return <PairOfMaps key={`map.region_${n}`} level="region" id={`map.region_${n}`} data={regionalIndicator}/>
          })}

          <div className="aling rigth buttons">


            {range.length > 1&&<Label className="remove" color="red" onClick={e=>this.removeLast('region')}><FormattedMessage id='gis.remove' defaultMessage="Remove last one"/></Label>}
            <Label className="add"  color="olive" onClick={e=>this.addnewOne('region')}><FormattedMessage id='gis.add' defaultMessage="Add new pair of maps"/></Label>

           </div>
           </div>
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
