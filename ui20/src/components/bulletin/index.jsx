import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {Segment} from "semantic-ui-react"
import * as bulletinActions from "../../redux/actions/bulletinActions"
import "./bulletin.scss"

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
    return (
      <div className="bulletins-container">
        <Segment className="bulletins-header">
          <Segment className="title">
            <FormattedMessage id="menu.bulletins.title" />
          </Segment>
        </Segment>
      </div>)
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
