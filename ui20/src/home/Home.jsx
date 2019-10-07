import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import {simpleAction} from '../actions/SimpleAction';

import Header from './Header'
import Main from './Intro'
import Links from './Links'
import Stories from './Sotries'
import Weather from './Weather'
import Newsletter from './NewsLetter'
import Footer from './Footer'

class Home extends Component {
  componentDidMount() {
    this.props.simpleAction();
  }

  render() {
    return (<div>
      <Header {...this.props}></Header>
      <Main {...this.props}></Main>
      <Links {...this.props}></Links>
      <Stories {...this.props}></Stories>
      <Weather {...this.props}></Weather>
      <Newsletter {...this.props}></Newsletter>
      <Footer {...this.props}></Footer>
    </div>)
  }
}

const mapStateToProps = state => {
  return state.getIn(['home'])
}

const mapDispatchToProps = dispatch => ({
  doLoadData: () => simpleAction()
})

const mapActionCreators = {

  simpleAction

};

export default connect(mapStateToProps, mapActionCreators)(Home);
