import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {Segment} from "semantic-ui-react"
import BulletinReport from "../../modules/entities/bulletins/BulletinReport"
import Bulletins from "../../modules/entities/bulletins/Bulletins"
import * as bulletinActions from "../../redux/actions/bulletinActions"
import "./bulletin.scss"
import BulletinYear from "./BulletinYear"

class BulletinPage extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    isLoaded: PropTypes.bool.isRequired,
    report: PropTypes.object.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }
  render() {
    const {isLoaded} = this.props;
    if (!isLoaded) {
      return <div/>
    }

    const report: BulletinReport = this.props.report
    const bulletinsList = report.filter.years.sort().reverse().map(y => report.data.gtpMaterials.get(y))

    return (
      <div className="bulletins-container">
        <Segment className="bulletins-header">
          <Segment className="title">
            <FormattedMessage id="menu.bulletins.title" />
          </Segment>
        </Segment>
        <Segment className="bulletins-content">
          {bulletinsList.map((bs: Bulletins) => <BulletinYear key={bs.year} bulletins={bs} />)}
        </Segment>
      </div>)
  }
}


const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['bulletin', 'isLoaded']),
    report: state.getIn(['bulletin', 'data']),
  }
}

const mapActionCreators = {
  onLoadAll: bulletinActions.loadAllBulletins
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(BulletinPage))
