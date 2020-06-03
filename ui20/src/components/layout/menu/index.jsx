import PropTypes from "prop-types"
import React, {Component} from "react"
import {connect} from "react-redux"
import * as appActions from "../../../redux/actions/appActions"
import {cssClasses} from "../../ComponentUtil"
import "./menu.scss"
import {MenuNavButtonClosed, MenuNavButtonOpen} from "./MenuNavButton"

// TODO (SCROLLING_MENU) make them exportable for reuse from _base.scss
const HEADER_HEIGHT = 70
const MENU_HEIGHT = 366
const FOOTER_HEIGHT = 297
const fullMenuRequiredHeight = MENU_HEIGHT + HEADER_HEIGHT

class Menu extends Component {
  static propTypes = {
    toggleMenu: PropTypes.func.isRequired,
    isMenuOpened: PropTypes.bool.isRequired,
  }

  constructor(props) {
    super(props);
    const stickTo = document.documentElement.clientHeight < fullMenuRequiredHeight ? 'relative' : 'top'
    this.state = { stickTo }
  }

  componentDidMount() {
    window.addEventListener('scroll', this.handleScroll.bind(this));
  }

  componentWillUnmount() {
    window.removeEventListener('scroll', this.handleScroll.bind(this));
  }

  handleScroll(event) {
    const { scrollTop, scrollTopMax, clientHeight } = event.target.documentElement
    const remainingScroll = scrollTopMax - scrollTop

    let stickTo = 'top'
    if (clientHeight < fullMenuRequiredHeight) {
      stickTo = 'relative'
    } else if (remainingScroll < FOOTER_HEIGHT) {
      const heightRequiredWithVisibleFooter =  FOOTER_HEIGHT - remainingScroll +  fullMenuRequiredHeight
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

    return (
      <div className={cssClasses("menu-nav-bar", isMenuOpened ? "opened" : "closed")}>
        <div className={cssClasses("ui", "sticky", stickTo)}>
          <div className="top-menu">
            <MenuItem url="home" messageId="menu.home" icon="logo-anacim-small-optimized.png" className="home-icon"/>
            <MenuItem url="water-resources" messageId="home.pane.waterResources.title" icon="page_icon_water.svg"/>
            <MenuItem url="agriculture-and-market" messageId="home.pane.agricultureAndMarkets.title" icon="page_icon_agriculture.svg"/>
            <MenuItem url="livestock" messageId="home.pane.livestock.title" icon="page_icon_livestock.svg"/>
            <MenuItem url="bulletins" messageId="menu.bulletins" icon="page_icon_bulletins.svg"/>
            {/*
            <MenuNavButton lan={lan} url="about" messageId="home.header.menu.about" />
            */}
            {/*
            <MenuNavButton lan={lan} url="members" messageId="home.header.menu.members" />
            */}
          </div>
          <div className="open-or-close" onClick={() => toggleMenu(!isMenuOpened)}>
            <MenuItem messageId={`menu.${isMenuOpened ? "close" : "open"}`} icon="icon_close.svg"/>
          </div>
        </div>
      </div>)
  }
}


const mapStateToProps = state => {
  return {
    isMenuOpened: state.getIn(['app', 'isMenuOpened']),
  }
}

const mapActionCreators = {
  toggleMenu: appActions.toggleMenu,
}

export default connect(mapStateToProps, mapActionCreators)(Menu)
