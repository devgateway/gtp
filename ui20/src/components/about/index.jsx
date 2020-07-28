import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as memberActions from "../../redux/actions/memberActions"
import AboutIntro from "./AboutIntro"
import GTPMembers from "./GTPMembers"

class About extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    const {isLoaded} = this.props
    if (!isLoaded) {
      return <div/>
    }

    return (
      <div>
        <AboutIntro />
        <GTPMembers {...this.props} />
      </div>
    )
  }

}

const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['member', 'isLoaded']),
    memberData: state.getIn(['member', 'data']),
  }
}

const mapActionCreators = {
  onLoadAll: memberActions.loadAllMembers,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(About))
