import React, {Component} from "react"
import Menu from "./menu"
import "./layout.scss"

export default class Layout extends Component {

  render() {
    return (
      <div className="ui grid page-layout">
        <div className="four wide column">
          <Menu/>
        </div>
        <div className="twelve wide column page-content">
          {this.props.children}
        </div>
      </div>
    );
  }
}
