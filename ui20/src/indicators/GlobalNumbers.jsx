
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage, FormattedNumber} from 'react-intl';
import {getGlobalIndicators}  from '../modules/Indicator'
import {
  Dropdown,
  Grid,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky
} from 'semantic-ui-react'


import {connect} from 'react-redux';


 const GlobalNumbers=(props)=>{
 const data=props.data || []

  return (
    <Grid className="indicator global numbers" columns={4} divided>
      <h2>{props.globalFiltersReady?"Global Numbers Ready To Load":""}</h2>
      <Grid.Row>
      {
        data.map(n=>(
        <Grid.Column>
          <div className="indicator big number">
              <FormattedNumber minimumFractionDigits={0}  maximumFractionDigits={0} style={n.style} value={n.value}></FormattedNumber>
                <span className="indicator big number year">{n.year}</span>
          </div>
          <div className="indicator description">
            <div className="indicator logo"><Image src={n.image}></Image></div>
            <div className="indicator name"><FormattedMessage id={n.key} defaultMessage={n.text}/></div>
          </div>
        </Grid.Column>))
      }
      </Grid.Row>
      <Grid.Row>
        {data.map(n=>(
        <Grid.Column>
          <div className="indicator link">
            <div className="btn">
              <div className="icon go"/>
              <a href=""><FormattedMessage id="indicator.go.chart" defaultMessage="Go to Chart"/></a>
          </div>
          </div>
        </Grid.Column>))}
      </Grid.Row>

    </Grid>)
}



const mapStateToProps = state => {

  return {}
}

const mapActionCreators = {
  getGlobalIndicators,
};

export default connect(mapStateToProps, mapActionCreators)(GlobalNumbers);
