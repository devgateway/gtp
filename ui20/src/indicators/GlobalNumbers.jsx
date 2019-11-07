
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import {FormattedMessage, FormattedNumber} from 'react-intl';

import { getGlobalIndicators} from '../modules/Indicator'

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

class GlobalNumbers extends Component {


  componentDidUpdate(prevProps, prevState, snapshot) {
      if (this.props.globalFiltersReady && !this.props.data){
        this.props.onLoad()
      }

  }


   render(){
       const {error}=this.props

       const data=this.props.data?this.props.data.toJS():null
        return (
          <Grid className="indicator global numbers" columns={4} divided>
            {error?<div className="message error">Can't load data - {error.message}</div>:null}
            <Grid.Row>
            {data && data.map(n=>(
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
              {data && data.map(n=>(
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
}


const mapStateToProps = state => {
  const data=state.getIn(['indicator','globalNumbers','data'])
  const error=state.getIn(['indicator','globalNumbers','error'])


  return {data,error}
}

const mapActionCreators = {};

export default connect(mapStateToProps, mapActionCreators)(GlobalNumbers);
