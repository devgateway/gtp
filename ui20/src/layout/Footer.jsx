import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';

import './footer.scss';

const Footer = (props) => {
  return (<div className="footer">

    <div className="st-row">

      <div className="footer-share-bar">
        <div className="share-icon tw"/>
        <div className="share-icon fb"/>
        <div className="share-icon in"/>
        <div className="share-icon inst"/>
      </div>

      <div className="footer-nav-bar">
        <div className="nav link">
          <FormattedMessage id="footer.lik.home" defaultMessage={"Home"} values={""}/></div>
        <div className="nav link">
          <FormattedMessage id="footer.lik.dashboard" defaultMessage={"Dashboard"} values={""}/></div>
        <div className="nav link">
          <FormattedMessage id="footer.lik.resources" defaultMessage={"Resources"} values={""}/>
        </div>
        <div className="nav link">
          <FormattedMessage id="footer.lik.about" defaultMessage={"About"} values={""}/>
        </div>
      </div>
    </div>
    <div className="st-row">
      <div className="footer-text">
        <FormattedMessage id="footer.text.address" defaultMessage={`
            1110 Vermont Ave NW  Suite 500
              Washington, DC, 20005 USA
              (at the Open Gov Hub) `} values={""}/>
      </div>

      <div className="footer-text">
        <FormattedMessage id="footer.text.copy.right" defaultMessage={`Copyright 2016. Fundamedios
All Rights Reserved.
Designed by Plastic
Develloped by Plastic.`} values={""}/>
      </div>

    </div>
  </div>);
}

export default Footer
