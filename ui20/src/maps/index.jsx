
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import AD3Map from './Map'
import './map.scss'
import { Grid, Image } from 'semantic-ui-react'

var regions = require('../json/regions.json'); //with path
var departments = require('../json/departments.json'); //with path

const regionClick=(e)=>{
    debugger;
}

const TheGrid = () => (
  <Grid celled='internally' className="gis grid table">
    <Grid.Row>
        <Grid.Column width={16}>
          <AD3Map json={regions} color="Reds" onClick={regionClick}/>
        </Grid.Column>


        </Grid.Row>
<Grid.Row>
        <Grid.Column width={16} >
          <AD3Map json={departments} color="Blues"/>
        </Grid.Column>

</Grid.Row>
  </Grid>
)


class GIS extends Component {

  constructor(props) {
    super(props);
  }

  render() {
      return (<div className="gis container">
                <TheGrid/>


        </div>)
  }
}


const mapStateToProps = state => {

  return {
    
  }
}

const mapActionCreators = {

};
export default injectIntl(connect(mapStateToProps, mapActionCreators)(GIS));
