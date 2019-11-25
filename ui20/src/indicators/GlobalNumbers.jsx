
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState, useEffect} from 'react'
import {FormattedMessage, FormattedNumber, injectIntl} from 'react-intl';

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
import CountUp from 'react-countup';

import {connect} from 'react-redux';



class GlobalNumbers extends Component {


  componentDidUpdate(prevProps, prevState, snapshot) {
      if (this.props.globalFiltersReady && !this.props.data){
        this.props.onLoad()
      }

  }


   render(){
       const {intl,error}=this.props

       const data=this.props.data?this.props.data.toJS():null
        return (
<div>
          <Grid className="indicator global numbers" columns={4} divided>
            {error?<div className="message error">Can't load data - {error.message}</div>:null}
            <Grid.Row>
            {data && data.map(n=>(
              <Grid.Column key={n.key}>
                <div className="indicator big number">
                  <CountUp  redraw={true} start={0} end={n.value*100} delay={0} useEasing={true}  formattingFn={(f)=>intl.formatNumber(f/100, {style: 'percent', minimumFractionDigits: 0,maximumFractionDigits: 0})}/>
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
              <Grid.Column key={n.key+'_link'}>
                <div className="indicator link">
                  <div className="btn">
                    <div className="icon go"/>
                    <a onClick={e=>{

                     document.getElementById("anchor."+n.key).scrollIntoView({
                        behavior:  "smooth",
                        block:    "end" ,
                    })

                  }}><FormattedMessage id="indicator.go.chart" defaultMessage="Go to Chart"/></a>
                </div>
                </div>
              </Grid.Column>))}
            </Grid.Row>

          </Grid>
          <Segment className="info-text">
            <div className="source-icon"></div>
            <FormattedMessage id="indicators.global.notes" defaultMessage={`  Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.`}></FormattedMessage>
          </Segment>


</div>
        )
      }
}


const mapStateToProps = state => {
  const data=state.getIn(['indicator','globalNumbers','data'])
  const error=state.getIn(['indicator','globalNumbers','error'])


  return {data,error}
}

const mapActionCreators = {};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(GlobalNumbers)) ;
