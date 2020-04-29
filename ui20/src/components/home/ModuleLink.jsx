import {FormattedHTMLMessage, injectIntl} from "react-intl";
import {GridColumn, Grid, GridRow} from "semantic-ui-react";
import React from "react";

import './ModuleLinksBlock.scss'
import {Link} from "react-router-dom";

const Pane = injectIntl((props) => {
  // eslint-disable-next-line no-unused-vars
  const {intl, name, url} = props;
  console.log(`home.pane.title.${name}`);
  return (<div>
    <Link to={`/${intl.locale}/${url}`}>
    <img className="pane-icon" src={`icon_${name}.png`} alt={''}/>
    <div className="pane-title">
      <FormattedHTMLMessage id={`home.pane.${name}.title`} />
    </div>
    <FormattedHTMLMessage id={`home.pane.${name}.text.short`}/>
    </Link>
  </div>);
});

const ModuleLinksBlock = injectIntl((props) => {
  return (<div className="home-links-container">

    <div className="home-links-image">
      <Grid columns={3}>
        <GridRow>
          <GridColumn>
            <Pane name='waterResources' {...props} url={'water-resources'} />
          </GridColumn>
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
