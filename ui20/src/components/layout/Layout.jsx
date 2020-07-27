import React, {useEffect} from "react"
import {useLocation} from "react-router"
import {Grid, GridColumn, GridRow} from "semantic-ui-react";
import "./layout.scss"
import Menu from "./menu"

const Layout = (props) => {
  const location = useLocation()

  useEffect(() => {
    window.scrollTo(0, 0)
  }, [location])

  return (
    <Grid className="page-layout" stackable={false}>
      <GridRow>
        <GridColumn className="page-menu menu">
          <Menu/>
        </GridColumn>
        <GridColumn className="page-content">
          {props.children}
        </GridColumn>
      </GridRow>
    </Grid>
  );
}

export default Layout
