
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import './national.scss'
import {loadNationalIndicators} from '../modules/National'
import { Grid, Label } from 'semantic-ui-react'

import PercentChartContainer from './PercentChartContainer'
import UnitChartContainer from './UnitChartContainer'



import Immutable from 'immutable'
import messages from '../translations/messages'
import {CustomFilterDropDown,items2options} from '../indicators/Components'


class GIS extends Component {
  constructor(props) {
    super(props);
     this.state = { nCharts: 2 };
     this.addnewOne=this.addnewOne.bind(this)
     this.removeLast=this.removeLast.bind(this)
  }

  componentDidMount(){
    this.props.onLoad()
  }

  addnewOne(){
    this.setState({nCharts :this.state.nCharts +2})
  }

  removeLast(){
    this.setState({nCharts :this.state.nCharts -2})
  }


  render() {
    const {data, intl, onExport} = this.props

    const {nCharts}=this.state

    const range = Array.from({length: nCharts}, (value, key) => key)
    return (
      <div className="national container">
          <div className="national title">
            <p>
              <FormattedMessage id="national.page.title" defaultMessage="National Indicators"></FormattedMessage>
            </p>
          </div>

          <div className="national description">
            <p><FormattedMessage id='national.page.description' defaultMessage="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"/></p>
          </div>


          <Grid fluid  columns='equal'>

            {range.map(n=>{
              return  (
                <Grid.Column width={8}>
                  <UnitChartContainer data={data}/>
                </Grid.Column>
            )

            })}
            </Grid>

          <div className="aling rigth buttons">
                <Label className="add"  color="olive" onClick={this.addnewOne}>
                  <FormattedMessage id='national.page.add_row' defaultMessage="Add row"/>
                </Label>

              {nCharts > 1&&
                <Label className="remove" color="black" onClick={this.removeLast}>
                  <FormattedMessage id='national.page.remove_row' defaultMessage="Remove last row"/>
                </Label>}


           </div>

         </div>
      )
  }
}


const mapStateToProps = state => {
  const data=state.getIn(['national','data']);

  return {data}
}

const mapActionCreators = {onLoad:loadNationalIndicators};


export default injectIntl(connect(mapStateToProps, mapActionCreators)(GIS));
