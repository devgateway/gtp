import React, {Component} from 'react';
import {connect} from 'react-redux';

import Main from './Intro'
// import {loadRapidLInks} from '../../redux/ipar/Data'
import ModuleLinksBlock from "./ModuleLink";

class Home extends Component {
  componentDidMount() {
      // this.props.onLoadRapidLinks()
  }

  render() {

    return (
      <div>
        <Main {...this.props}></Main>
        <ModuleLinksBlock {...this.props}></ModuleLinksBlock>
        {
          // <Links {...this.props}></Links>
        }
        {
          // <Stories {...this.props}></Stories>
        }
        {
          // <Weather {...this.props}></Weather>
        }
        {
          // <Newsletter {...this.props}></Newsletter>
        }

    </div>)
  }
}

const mapStateToProps = state => {
return {
    // links: state.getIn(['data','links','data'])
  }
}

const mapActionCreators = {
  // onLoadRapidLinks:loadRapidLInks
};

export default connect(mapStateToProps, mapActionCreators)(Home);
