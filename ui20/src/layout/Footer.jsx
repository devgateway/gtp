import React, {Component} from 'react';
import {FormattedMessage,FormattedHTMLMessage} from 'react-intl';

import './footer.scss';

const Footer = (props) => {
  return (<div className="footer">

    <div className="st-row">

      <div className="footer-share-bar">
        <a href="https://twitter.com/IPAR_ThinkTank" className="share-icon tw"/>
        <a href="https://www.facebook.com/IPARNEWS/" className="share-icon fb"/>
        <a className="share-icon in"/>
        <a className="share-icon inst"/>
      </div>
      <div className="footer-share-bar right">
        <a href="https://twitter.com/statsenegal" className="share-icon tw"/>
        <a href="https://www.facebook.com/ANSD.Senegal" className="share-icon fb"/>
        <a className="share-icon in"/>
        <a className="share-icon inst"/>
      </div>
    </div>
    <div className="st-row">
      <div className="footer-text">
        <FormattedHTMLMessage id="footer.text.address" defaultMessage={`
            1110 Vermont Ave NW  Suite 500
              Washington, DC, 20005 USA
              (at the Open Gov Hub) `} values={""}/>
      </div>

      <div className="footer-text">
        <FormattedHTMLMessage id="footer.text.copy_right" defaultMessage={`Copyright 2016. Fundamedios
All Rights Reserved.
Designed by Plastic
Develloped by Plastic.`} values={""}/>
      </div>

    </div>
  </div>);
}

export default Footer
