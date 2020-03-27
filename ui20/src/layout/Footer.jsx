import React, {Component} from 'react';
import {FormattedMessage,FormattedHTMLMessage} from 'react-intl';

import './footer.scss';

const Footer = (props) => {
  return (<div className="footer">

    <div className="st-row">

      <div className="footer-share-bar">
        <a href="https://twitter.com/meteosenegal" className="share-icon tw"/>
        <a href="https://www.facebook.com/Anacim-Prevision-498598026887517/" className="share-icon fb"/>
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
    </div>
  </div>);
}

export default Footer
