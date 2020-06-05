import React, {Component} from 'react'
import {connect} from 'react-redux'
import Intro from './Intro'
import ModuleLinksBlock from "./ModuleLink"

class Home extends Component {

  render() {
    return (
      <div>
        <Intro {...this.props}></Intro>
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
  }
}

const mapActionCreators = {
}

export default connect(mapStateToProps, mapActionCreators)(Home)
