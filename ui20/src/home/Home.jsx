import React, {Component} from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import './header.scss';
import './main.scss';

import {simpleAction} from '../actions/SimpleAction';

import {Header} from './header'

class Home extends Component {
  componentDidMount() {
    this.props.simpleAction();
  }

  render() {
      return (<div>
        <Header {...this.props}></Header>
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
