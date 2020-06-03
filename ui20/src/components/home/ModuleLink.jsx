import {FormattedHTMLMessage, injectIntl} from "react-intl";
import {GridColumn, Grid, GridRow} from "semantic-ui-react";
import React from "react";

import './ModuleLinksBlock.scss'
import {Link} from "react-router-dom";

const Pane = injectIntl((props) => {
  // eslint-disable-next-line no-unused-vars
  const {intl, name, url} = props
  return (<div className="home-links-image">
    <Link to={`/${intl.locale}/${url}`}>
      <img className="pane-icon" src={`icon_${name}.jpg`} alt={''}/>
      <div className="pane-text">
        <div className="pane-title">
          <FormattedHTMLMessage id={`home.pane.${name}.title`}/>
        </div>
        <div className="pane-description">
          <FormattedHTMLMessage id={`home.pane.${name}.text.short`}/>
        </div>
      </div>
    </Link>
  </div>);
});

const ModuleLinksBlock = injectIntl((props) => {
  return (<div className="home-links-container">

    <div>
      <Grid stackable columns={3} container centered>
        <GridRow centered>
          <GridColumn textAlign="center">
            <Pane name='waterResources' {...props} url={'water-resources'} />
          </GridColumn>
          <GridColumn textAlign="center">
            <Pane name='agricultureAndMarkets' {...props} url={'agriculture-and-market'}/>
          </GridColumn>
          <GridColumn textAlign="center">
            <Pane name='livestock' {...props}/>
          </GridColumn>
        </GridRow>
      </Grid>
    </div>

    <div className="source-separator "></div>
  </div>);
});

export default ModuleLinksBlock;
