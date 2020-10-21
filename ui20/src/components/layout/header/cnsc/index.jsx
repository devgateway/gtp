import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import {CNSC_HEADER_LOGO} from "../../../../modules/api/EPConstants"
import * as appActions from "../../../../redux/actions/appActions"
import "./cnscHeader.scss";
import {cssClasses, getBrowserClass} from "../../../ComponentUtil"
import {CNSCMenu} from "./CNSCMenu"
import {CNSCSearch} from "./CNSCSearch"

class CNSCHeader extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    isCNSCHeaderLoaded: PropTypes.bool.isRequired,
  }

  constructor(props) {
    super(props)
    this.state = {
      isLocalStateChange: false,
    }
  }

  static getDerivedStateFromProps(props, state) {
    const {isLocalStateChange} = state
    if (isLocalStateChange) {
      return {
        isLocalStateChange: false
      }
    }
    return null
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    const {isCNSCHeaderLoaded, cnscHeader, intl} = this.props
    if (!isCNSCHeaderLoaded) {
      return <div className="cnsc-header"/>
    }

    const {hideMenu} = this.state

    return (
      <div className={cssClasses("cnsc-header-container", getBrowserClass())}>
        <div className="cnsc-header">
          <div className="cnsc-logo">
            <a href={cnscHeader.logoUrl}>
              <img src={`${CNSC_HEADER_LOGO}`} alt="CNSC"/>
            </a>
          </div>
          <div className={hideMenu ? "hidden" : ""}>
            <CNSCMenu menuTree={cnscHeader.menu}/>
          </div>
          {cnscHeader.isSearchUrlEnabled &&
          <CNSCSearch
            searchPrefix={cnscHeader.searchUrl}
            intl={intl}
            onStateChange={(isActive) => this.setState({hideMenu: isActive, isLocalStateChange: true})}/>}
        </div>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    isCNSCHeaderLoaded: state.getIn(['app', 'isCNSCHeaderLoaded']),
    cnscHeader: state.getIn(['app', 'data', 'cnscHeader']),
  }
}

const mapActionCreators = {
  onLoadAll: appActions.loadCNSCHeader,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(CNSCHeader))
