import React, {Component} from "react"
import PropTypes from "prop-types"
import {connect} from "react-redux"
import {withRouter} from "react-router"
import * as appActions from "../../../redux/actions/appActions"
import {cssClasses} from "../../ComponentUtil"
import "./menu.scss"
import {MenuNavButtonClosed, MenuNavButtonOpen} from "./MenuNavButton"

class Menu extends Component {
  static propTypes = {
    toggleMenu: PropTypes.func.isRequired,
    isMenuOpened: PropTypes.bool.isRequired,
  }

  render() {
    const lan = this.props.match.params.lan
    const {isMenuOpened, toggleMenu} = this.props
    const MenuItem = isMenuOpened ? MenuNavButtonOpen : MenuNavButtonClosed

    return (
      <div className={cssClasses("menu-nav-bar", isMenuOpened ? "opened" : "closed") }>
        <div className="ui sticky">
          <div className="top-menu">
            <MenuItem lan={lan} url="home" messageId="menu.home" icon="logo-anacim-small-optimized.png"/>
            <MenuItem lan={lan} url="water-resources" messageId="home.pane.waterResources.title" icon="page_icon_water.svg"/>
            <MenuItem lan={lan} url="agriculture-and-market" messageId="home.pane.agricultureAndMarkets.title" icon="page_icon_agriculture.svg"/>
            <MenuItem lan={lan} url="livestock" messageId="home.pane.livestock.title" icon="page_icon_livestock.svg"/>
            <MenuItem lan={lan} url="bulletins" messageId="menu.bulletins" icon="page_icon_bulletins.svg"/>
            {/*
            <MenuNavButton lan={lan} url="about" messageId="home.header.menu.about" />
            */}
            {/*
            <MenuNavButton lan={lan} url="members" messageId="home.header.menu.members" />
            */}
          </div>
          <div className="open-or-close" onClick={() => toggleMenu(!isMenuOpened)}>
            <MenuItem lan={lan} messageId={`menu.${isMenuOpened ? "close" : "open"}`} icon="icon_close.svg"/>
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

export default withRouter(connect(mapStateToProps, mapActionCreators)(Menu))
