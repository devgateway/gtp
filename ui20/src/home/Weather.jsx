/* eslint-disable */
import React, {Component} from 'react';
import {injectIntl, FormattedMessage, FormattedHTMLMessage} from 'react-intl';

import './weather.scss';


import messages from '../translations/messages'


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
            <TitleSubtitleBlock  showSeparator
              title={props.intl.formatMessage(messages.weather_title_1)}
              subtitle={props.intl.formatMessage(messages.weather_subtitle_1)}/>
          </ContentBlock>
        </div>
        <div className="st-column st-column-3">
          <ContentBlock big>
              <img src="icon_weather.png"></img>
              <TitleSubtitleBlock big
                title={props.intl.formatMessage(messages.weather_title_2)}
                subtitle={props.intl.formatMessage(messages.weather_subtitle_2)}/>

              <TitleSubtitleBlock showSeparator
                title={props.intl.formatMessage(messages.weather_title_3)}
                subtitle={props.intl.formatMessage(messages.weather_subtitle_3)}/>
              <div className="btn-learn"><FormattedMessage id="home.weather.learn.more" defaultMessage="Learn more"></FormattedMessage></div>
           </ContentBlock>
        </div>
        <div className="st-column st-column-3">
          <ContentBlock>
            <TitleSubtitleBlock showSeparator
              title={props.intl.formatMessage(messages.weather_title_4)}
              subtitle={props.intl.formatMessage(messages.weather_subtitle_4)}/>

          </ContentBlock>

        </div>

      </div>
    </div>
  </div>);
})

export default Weather
