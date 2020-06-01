import React, {Component} from "react"
import {withRouter} from "react-router"
import MenuNavButton from "./MenuNavButton"
import "./menu.scss"

class Menu extends Component {
  render() {
    const lan = this.props.match.params.lan

    return (
      <div className="menu-nav-bar">
        <MenuNavButton lan={lan} url="home" messageId="home.header.menu.home"/>
        <MenuNavButton lan={lan} url="water-resources" messageId="home.pane.waterResources.title"/>
        <MenuNavButton lan={lan} url="agriculture-and-market" messageId="home.pane.agricultureAndMarkets.title"/>
        <MenuNavButton lan={lan} url="livestock" messageId="home.pane.livestock.title"/>
        <MenuNavButton lan={lan} url="bulletins" messageId="home.header.menu.bulletins"/>
        {/*
        <MenuNavButton lan={lan} url="about" messageId="home.header.menu.about" />
        */}
        {/*
        <MenuNavButton lan={lan} url="members" messageId="home.header.menu.members" />
        */}
      </div>)
  }
}

export default withRouter(Menu)
