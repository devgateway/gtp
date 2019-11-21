import React, {Component} from 'react';
import {injectIntl, FormattedMessage, FormattedHTMLMessage} from 'react-intl';

import './newsletter.scss';

const Weather = injectIntl((props) => {

  return (<div className="home-news-letter">

    <div className="news-letter-text">
      <FormattedMessage id="home.newsletter.title" defaultMessage="Subscribe to our newsletter"></FormattedMessage>
    </div>
    <div className="btn-group">
      <div className="btn-news-letter-suscribe">
        <FormattedMessage id="home.newsletter.sign.up" defaultMessage="Newsletter Sign-Up"></FormattedMessage>
      </div>
      <div className="btn-news-letter-unsuscribe">
        <FormattedMessage id="home.newsletter.sign.off" defaultMessage="Newsletter Sign-Off"></FormattedMessage>
      </div>
    </div>
  </div>);
})

export default Weather
