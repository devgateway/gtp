import React, {Component} from 'react';
import {injectIntl, FormattedMessage, FormattedHTMLMessage} from 'react-intl';

import './weather.scss';

const Weather = injectIntl((props) => {

  const TitleSubtitleBlock = (props) => {

    return (
      <div  className={"weather-content-text "+(props.big?'big':'')}>
        <div className={"weather-content-title "+(props.big?'big':'')}>
          {props.title}
        </div>
        {props.showSeparator&&  <div className="weather-separator"></div>}

        <div className={"weather-content-subtitle "+(props.big?'big':'')}>
          {props.subtitle}
        </div>
      </div>)
  }

  const ContentBlock = (props) => {
    return (<div  className={"weather-content "+(props.big?'big':'')}>
      {props.children}
    </div>)
  }

  return (<div className="home-weather">
    <div className="st-row">
      <div className="st-row">
        <div className="st-column st-column-3">
          <ContentBlock>
            <TitleSubtitleBlock  showSeparator title={props.intl.formatMessage({id: "weather.title_1", defaultMessage: "Main crops by zone"})} subtitle={props.intl.formatMessage({id: "weather.subtitle_1", defaultMessage: "This is where the short descriptive line will go here to describe the category"})}/>
          </ContentBlock>
        </div>
        <div className="st-column st-column-3">
          <ContentBlock big>
              <img src="icon_weather.png"></img>
              <TitleSubtitleBlock big title={props.intl.formatMessage({id: "weather.title_3", defaultMessage: "Meteorological data"})} subtitle={props.intl.formatMessage({id: "weather.subtitle_3", defaultMessage: "Weather forecast for next 10 days"})}/>
              <TitleSubtitleBlock showSeparator title={props.intl.formatMessage({id: "weather.title_4", defaultMessage: "Main crops by zone"})} subtitle={props.intl.formatMessage({id: "weather.subtitle_4", defaultMessage: "This is where the short descriptive line will go here to describe the category"})}/>
              <div className="btn-learn"><FormattedMessage id="wather.learn" defaultMessage="Learn more"></FormattedMessage></div>
           </ContentBlock>
        </div>
        <div className="st-column st-column-3">
          <ContentBlock>
            <TitleSubtitleBlock showSeparator title={props.intl.formatMessage({id: "weather.title_5", defaultMessage: "Main crops by zone"})} subtitle={props.intl.formatMessage({id: "weather.subtitle_3", defaultMessage: "This is where the short descriptive line will go here to describe the category"})}/>
          </ContentBlock>

        </div>

      </div>
    </div>
  </div>);
})

export default Weather
