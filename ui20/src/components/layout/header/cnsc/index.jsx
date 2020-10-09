import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import {CNSC_HEADER_LOGO} from "../../../../modules/api/EPConstants"
import * as appActions from "../../../../redux/actions/appActions"
import "./cnscHeader.scss";

class CNSCHeader extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    isCNSCHeaderLoaded: PropTypes.bool.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    const {isCNSCHeaderLoaded, intl} = this.props
    if (!isCNSCHeaderLoaded) {
      return <div className="cnsc-header" />
    }
    // TODO configure logo URL
    return (
      <div className="cnsc-header">
        <div className="cnsc-logo">
          <a href="http://www.anacim.sn/cnsc/">
            <img src={`${CNSC_HEADER_LOGO}`} alt="CNSC" />
          </a>
        </div>
        <div>
          TODO
        </div>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    isCNSCHeaderLoaded: state.getIn(['app', 'isCNSCHeaderLoaded']),
  }
}

const mapActionCreators = {
  onLoadAll: appActions.loadCNSCHeader,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(CNSCHeader))
