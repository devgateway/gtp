import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {connect} from "react-redux"
import {withRouter} from "react-router"
import * as appActions from "../../../redux/actions/appActions"
import {cssClasses} from "../../ComponentUtil"
import "./menu.scss"
import MenuEntry, {APP_MENU} from "./MenuEntry"
import {MenuNavButtonClosed, MenuNavButtonOpen} from "./MenuNavButton"
import * as cssJS from '../../css'

// TODO (SCROLLING_MENU) make them exportable for reuse from _base.scss
const fullMenuRequiredHeight = cssJS.MENU_HEIGHT + cssJS.HEADER_HEIGHT

class Menu extends Component {
  static propTypes = {
    toggleMenu: PropTypes.func.isRequired,
    isMenuOpened: PropTypes.bool.isRequired,
  }

  constructor(props) {
    super(props);
    const stickTo = document.documentElement.clientHeight < fullMenuRequiredHeight ? 'relative' : 'top'
    this.state = { stickTo }
    this.handleScroll = this.handleScroll.bind(this)
  }

  componentDidMount() {
    window.addEventListener('scroll', this.handleScroll);
  }

  componentWillUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
  }

  handleScroll(event) {
    const { scrollTop, scrollTopMax, clientHeight } = event.target.documentElement
    const remainingScroll = scrollTopMax - scrollTop

    let stickTo = 'top'
    if (clientHeight < fullMenuRequiredHeight) {
      stickTo = 'relative'
    } else if (remainingScroll < cssJS.FOOTER_HEIGHT) {
      const heightRequiredWithVisibleFooter =  cssJS.FOOTER_HEIGHT - remainingScroll +  fullMenuRequiredHeight
      if (heightRequiredWithVisibleFooter > clientHeight) {
        stickTo = 'bottom'
      }
    }

    this.setState({ stickTo });
  }

  render() {
    const {isMenuOpened, toggleMenu} = this.props
    const { stickTo } = this.state
    const MenuItem = isMenuOpened ? MenuNavButtonOpen : MenuNavButtonClosed
    const menuEntries = getMenuEntries(this.props)
    const activeEntry = menuEntries.find(me => me.isActive)
    const isShowDescription = isMenuOpened && activeEntry && activeEntry.descriptionId

    return (
      <div className={cssClasses("menu-nav-bar", isMenuOpened ? "opened" : "closed")}>
        <div className={cssClasses("ui", "sticky", stickTo)}>
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
            <MenuItem messageId={`menu.${isMenuOpened ? "close" : "open"}`} icon="icon_close.svg"/>
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
