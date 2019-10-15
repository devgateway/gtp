import React, {Component} from 'react';
import {FormattedMessage, FormattedHTMLMessage} from 'react-intl';
import { Link } from "react-router-dom";

import './links.scss'

const PresnetedBy = (props) => {
  return (<div className="home-links">
    <div className="col_1">
      <div className="presented-title">
        <span><FormattedMessage id="home.presented.by" defaultMessage={"Presented by"} values={""}/></span>
        <div className="logo"/>
      </div>
      <div className="presented-text"><FormattedMessage id="home.presented.text" defaultMessage={`The AgriData platform tends to
          provision of actors in the agricultural sector
          Senegalese, comprehensive and reliable data
          to be taken into account in the definition of
          national policies and strategies.`} values={""}/></div>
    </div>
    <div className="col_2">
      <div className="presented-image"></div>
    </div>

  </div>)
}

const LinksBlock = (props) => {
  return (<div className="home-links-container">
    <PresnetedBy></PresnetedBy>
    <div className="links-nav-bar">

      <div className="link active microdata">
        <div className="btn">
          <div className="icon"></div>
          <div className="label"> <Link to="analytic"> <FormattedMessage id="home.links.microdata" defaultMessage={"Microdata"} values={""}/></Link>
          </div>
        </div>
      </div>

      <div className="link indicator">
        <div className="btn">
          <div className="icon"></div>
            <div className="label">
              <FormattedMessage id="home.links.indicators" defaultMessage={"Indicators"} values={""}/>
            </div>

        </div>

      </div>

      <div className="link market">
        <div className="btn">
          <div className="icon"></div>
            <div className="label">
              <FormattedMessage id="home.links.markets" defaultMessage={"Markets"} values={""}/>
          </div>
        </div>
      </div>

    </div>

    <div className="links explanation">
        <FormattedHTMLMessage id="home.links.explanation" defaultMessage={`The objective is to visualize the annual crop production.
            Different combinations can be achieved by displaying separately or combining production, yield and area seeded.<br><br>
            Production can be improved with the use of fertilizer or impacted by the use of pesticides.
            The information provides information on the type of inputs and the type of pesticides used by region and crop.`} values={""}/>


        <div className="links source"><div className="source-icon"></div>Data comes from DAPSA, ANSD and FAO</div>
    </div>
    <div className="source-separator "></div>
  </div>);
}

export default LinksBlock
