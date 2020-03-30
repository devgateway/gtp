import {FormattedHTMLMessage, injectIntl} from "react-intl";
import messages from "../translations/messages";
import {GridColumn, Grid, Tab, GridRow} from "semantic-ui-react";
import React from "react";

import './ModuleLinksBlock.scss'

const Pane = injectIntl((props) => {
  const {intl, name} = props;
  console.log(`home.pane.title.${name}`);
  return (<div>
    <img className="pane-icon" src={`icon_${name}.png`}/>
    <div className="pane-title">
      <FormattedHTMLMessage id={`home.pane.${name}.title`} />
    </div>
    <FormattedHTMLMessage id={`home.pane.${name}.text.short`}/>
  </div>);
});

const ModuleLinksBlock = injectIntl((props) => {
  return (<div className="home-links-container">

    <div className="home-links-image">
      <Grid columns={2}>
        <GridRow>
          <GridColumn>
            <Pane name='climateAndMeteorology' {...props}/>
          </GridColumn>
          <GridColumn>
            <Pane name='environmentAndHydrology' {...props}/>
          </GridColumn>
        </GridRow>
        <GridRow>
          <GridColumn>
            <Pane name='agricultureAndMarkets' {...props}/>
          </GridColumn>
          <GridColumn>
            <Pane name='livestock' {...props}/>
          </GridColumn>
        </GridRow>
      </Grid>
    </div>

    <div className="source-separator "></div>
  </div>);
});

export default ModuleLinksBlock;
