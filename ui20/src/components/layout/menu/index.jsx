import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {connect} from "react-redux"
import {withRouter} from "react-router"
import * as appActions from "../../../redux/actions/appActions"
import * as ComponentUtil from "../../ComponentUtil"
import {cssClasses} from "../../ComponentUtil"
import "./menu.scss"
import MenuEntry, {APP_MENU} from "./MenuEntry"
import {MenuNavButtonClosed, MenuNavButtonOpen} from "./MenuNavButton"


class Menu extends Component {
  static propTypes = {
    toggleMenu: PropTypes.func.isRequired,
    isMenuOpened: PropTypes.bool.isRequired,
  }

  menuRef = React.createRef()

  constructor(props) {
    super(props);
    this.state = {
      stickTo: 'relative',
      bottom: 'unset',
    }
    this.handleScroll = this.handleScroll.bind(this)
  }

  componentDidMount() {
    window.addEventListener('scroll', this.handleScroll);
  }

  componentWillUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
  }

  handleScroll(event) {
    const { scrollTop, scrollHeight, clientHeight } = document.scrollingElement || event.target.documentElement
    const remainingScroll = scrollHeight - clientHeight - scrollTop
    const menuAndHeaderHeight = this.menuRef.current.firstChild.clientHeight + ComponentUtil.getHeaderHeight()
    const footerHeight = ComponentUtil.getFooterHeight()

    let stickTo = 'top'
    let bottom = 'unset'
    if (!scrollTop) {
      if (this.state.stickTo === 'bottom') {
        stickTo = 'relative'
      }
    } else if (clientHeight < menuAndHeaderHeight) {
      stickTo = 'relative'
    } else if (remainingScroll < footerHeight) {
      const visibleFooter = footerHeight - remainingScroll
      const heightRequiredWithVisibleFooter = visibleFooter  +  menuAndHeaderHeight
      if (heightRequiredWithVisibleFooter > clientHeight) {
        stickTo = 'bottom'
        bottom = visibleFooter
      }
    }

    this.setState({ stickTo, bottom });
  }

  render() {
    const {isMenuOpened, toggleMenu} = this.props
    const { stickTo, bottom } = this.state
    const MenuItem = isMenuOpened ? MenuNavButtonOpen : MenuNavButtonClosed
    const menuEntries = getMenuEntries(this.props)
    const activeEntry = menuEntries.find(me => me.isActive)
    const isShowDescription = isMenuOpened && activeEntry && activeEntry.descriptionId

    return (
      <div ref={this.menuRef} className={cssClasses("menu-nav-bar", isMenuOpened ? "opened" : "closed")}>
        <div className={cssClasses("ui", "sticky", stickTo)} style={{bottom}}>
          <div className="top-menu">
            {menuEntries.map(me => <MenuItem key={me.messageId} {...me}/>)}
            {/*
            <MenuNavButton lan={lan} url="about" messageId="home.header.menu.about" />
            */}
            {/*
            <MenuNavButton lan={lan} url="members" messageId="home.header.menu.members" />
            */}
          </div>
          {isShowDescription &&
          <div className="entry-description"><FormattedMessage id={activeEntry.descriptionId} /></div>}
          <div className="open-or-close" onClick={() => toggleMenu(!isMenuOpened)}>
            <MenuItem messageId={`menu.${isMenuOpened ? "close" : "open"}`} icon="icon_close.svg" isActive={false}/>
          </div>
        </div>
      </div>)
  }
}

const getMenuEntries = (props) => {
  const lan = props.match.params.lan
  const pathName = props.location.pathname

  return APP_MENU.map((me: MenuEntry) => {
    const url = `/${lan}/${me.url}`
    return Object.assign({}, me, {
      url,
      isActive: pathName === url
    })
  })
}


const mapStateToProps = state => {
  return {
    isMenuOpened: state.getIn(['app', 'isMenuOpened']),
  }
}

const mapActionCreators = {
  toggleMenu: appActions.toggleMenu,
}

export default connect(mapStateToProps, mapActionCreators)(withRouter(Menu))
