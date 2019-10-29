
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage} from 'react-intl';

import {
  Dropdown,
  Grid,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky
} from 'semantic-ui-react'


 const GlobalNumbers=(props)=>{
  const data=props.data || []


  return (
    <Grid className="indicator global numbers" columns={4} divided>
      <Grid.Row>
      {
        data.map(n=>(
        <Grid.Column>
          <div className="indicator big number">{n.value}</div>
          <div className="indicator description">
            <div className="indicator logo"><Image src={n.image}></Image></div>
            <div className="indicator name">{n.text}</div>
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

export default GlobalNumbers
