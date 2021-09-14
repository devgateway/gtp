import {FormattedHTMLMessage, injectIntl} from "react-intl";
import {GridColumn, Grid, GridRow} from "semantic-ui-react";
import React from "react";

import './ModuleLinksBlock.scss'
import {Link} from "react-router-dom";
import {PAGE_AGRICULTURE_AND_MARKET, PAGE_LIVESTOCK, PAGE_WATER_RESOURCES} from "../../modules/entities/FMConstants"
import FMCheck from "../common/FMCheck"

const Pane = injectIntl((props) => {
  const {intl, name, url, fmEntry} = props
  const paneFunc = () =>
    <GridColumn textAlign="center">
      <div className="home-links-image">
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
      </div>
    </GridColumn>
  return <FMCheck onEnabledComponentBuilder={paneFunc} fmEntry={fmEntry} />
});

const ModuleLinksBlock = injectIntl((props) => {
  return (<div className="home-links-container">

    <div>
      <Grid stackable columns={3} container centered>
        <GridRow centered>
          <Pane name='waterResources' {...props} url={'water-resources'} fmEntry={PAGE_WATER_RESOURCES}/>
          <Pane name='agricultureAndMarkets' {...props} url={'agriculture-and-market'} fmEntry={PAGE_AGRICULTURE_AND_MARKET}/>
          <Pane name='livestock' {...props} fmEntry={PAGE_LIVESTOCK}/>
        </GridRow>
      </Grid>
    </div>

    <div className="source-separator "></div>
  </div>);
});

export default ModuleLinksBlock;
