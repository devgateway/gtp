import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as bulletinActions from "../../redux/actions/bulletinActions"

class BulletinPage extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    isLoaded: PropTypes.bool.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }
  render() {
    const {isLoaded} = this.props;
    if (!isLoaded) {
      return <div/>
    }
    return 'TODO'
  }
}


const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['bulletin', 'isLoaded'])
  }
}

const mapActionCreators = {
  onLoadAll: bulletinActions.loadAllBulletins
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(BulletinPage))
