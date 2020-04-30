/* eslint-disable */
import React, {Component} from 'react'
import {FormattedMessage, injectIntl} from 'react-intl';
import messages from '../../../translations/messages'

import {
  Grid,
  Image,
  Segment,
} from 'semantic-ui-react'
import CountUp from 'react-countup';

import {connect} from 'react-redux';


const getIndicatorName=(intl,key)=>{

  if(key== 'indicator.global.population.short'){
    return intl.formatMessage(messages.indicator_global_population_short)
  }
  if(key== 'indicator.global.women.short'){
    return intl.formatMessage(messages.indicator_global_women_short)
  }
  if(key== 'indicator.global.food.short'){
    return intl.formatMessage(messages.indicator_global_food_short)
  }
  if(key== 'indicator.global.aoi.short'){
    return intl.formatMessage(messages.indicator_global_aoi_short)
  }
}


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
                  <div className="indicator name">{getIndicatorName(intl,n.key)}</div>
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

                      if(document.getElementById("anchor."+n.key)){
                          var topOfElement = document.getElementById("anchor."+n.key).offsetTop - 170;
                          window.scroll({ top: topOfElement, behavior: "smooth" });

                    }
                  }}><FormattedMessage id="indicators.go.to.chart" defaultMessage="Go to Chart"/></a>
                </div>
                </div>
              </Grid.Column>))}
            </Grid.Row>

          </Grid>
          <Segment className="info-text">

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