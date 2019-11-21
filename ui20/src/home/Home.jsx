import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';


import Main from './Intro'
import Links from './Links'
import Stories from './Sotries'
import Weather from './Weather'
import Newsletter from './NewsLetter'

import {loadDataItems} from '../modules/Data'

class Home extends Component {
  componentDidMount() {
    this.props.loadData('region')
    this.props.loadData('cropType')
  }

  render() {
    return (
      <div>
        <Main {...this.props}></Main>
        <Links {...this.props}></Links>
        <Stories {...this.props}></Stories>
        <Weather {...this.props}></Weather>
        <Newsletter {...this.props}></Newsletter>

    </div>)
  }
}

const mapStateToProps = state => {
  return {
    home: state.getIn(['home'])
  }
}

const mapActionCreators = {
  loadData:loadDataItems

};

export default connect(mapStateToProps, mapActionCreators)(Home);
