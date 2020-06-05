import React, {Component} from "react"
import {Grid, GridColumn, GridRow} from "semantic-ui-react";
import "./layout.scss"
import Menu from "./menu"

export default class Layout extends Component {

  render() {
    return (
      <Grid className="page-layout" stackable={false}>
        <GridRow>
          <GridColumn className="menu">
            <Menu/>
          </GridColumn>
          <GridColumn className="page-content">
            {this.props.children}
          </GridColumn>
        </GridRow>
      </Grid>
    );
  }
}
