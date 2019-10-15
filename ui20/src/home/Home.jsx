import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';


import Header from '../layout/Header'
import Main from './Intro'
import Links from './Links'
import Stories from './Sotries'
import Weather from './Weather'
import Newsletter from './NewsLetter'
import Footer from '../layout/Footer'

class Home extends Component {
  componentDidMount() {
    
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
  return { home:state.getIn(['home'])}
}

const mapDispatchToProps = dispatch => ({

})

const mapActionCreators = {



};

export default connect(mapStateToProps, mapActionCreators)(Home);
