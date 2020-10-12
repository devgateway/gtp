/* eslint-disable jsx-a11y/anchor-has-content */
import React from 'react';
import {FormattedHTMLMessage} from 'react-intl';

import './footer.scss';

const Footer = (props) => {
  return (<div id="ad3-footer" className="footer">

    <div className="st-row">

      <div className="footer-share-bar">
        <a href="https://twitter.com/meteosenegal" className="share-icon tw"/>
        <a href="https://www.facebook.com/Anacim-Prevision-498598026887517/" className="share-icon fb"/>
        {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
        <a className="share-icon in"/>
        {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
        <a className="share-icon inst"/>
      </div>
    </div>
    <div className="st-row">
      <div className="footer-text">
        <FormattedHTMLMessage id="footer.text.address" defaultMessage={`
            1110 Vermont Ave NW  Suite 500
              Washington, DC, 20005 USA
              (at the Open Gov Hub) `} values={""}/>
        <div className="credits">
          <a href="http://www.onlinewebfonts.com">oNline Web Fonts</a>
        </div>
      </div>
    </div>
  </div>);
}

export default Footer
