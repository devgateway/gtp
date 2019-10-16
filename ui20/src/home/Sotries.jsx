import React, {Component} from 'react';
import {injectIntl,FormattedMessage,FormattedHTMLMessage} from 'react-intl';

import './stories.scss';


  const Image=(props)=>{

    const css={
      backgroundImage:`url(${props.img})`
    }
  return (
        <div className="image" style={css}>
        <div className="image-text">
        <div className="image-title">{props.title}</div>
        <div className="image-sub-title">{props.subtitle}</div>
</div>
            <div className="mask"/>

        </div>)
  }


const Stories=injectIntl( (props)=>{

  return (

    <div className="home-stories">
      <div className="st-row">
        <div className="st-column st-column-6"><Image img={'tmp/image6x1.jpg'} title={props.intl.formatMessage({ id:"stories.title_1", defaultMessage:"Main crops by zone"})} subtitle={props.intl.formatMessage({ id:"stories.subtitle_1", defaultMessage:"A short descriptive line will go here"})}/></div>
        <div className="st-column st-column-4"><Image img={'tmp/image4x1.jpg'} title={props.intl.formatMessage({ id:"stories.title_2" ,defaultMessage:"Main crops by zone"})} subtitle={props.intl.formatMessage({ id:"stories.subtitle_2", defaultMessage:"A short descriptive line will go here"})}/></div>
      </div>
      <div className="st-row">
        <div className="st-column st-column-3"><Image img={'tmp/image3x1a.jpg'} title={props.intl.formatMessage({ id:"stories.title_3" ,defaultMessage:"Main crops by zone"})} subtitle={props.intl.formatMessage({ id:"stories.subtitle_3", defaultMessage:"A short descriptive line will go here"})}/></div>
        <div className="st-column st-column-3"><Image img={'tmp/image3x1b.jpg'} title={props.intl.formatMessage({ id:"stories.title_4" ,defaultMessage:"Main crops by zone"})} subtitle={props.intl.formatMessage({ id:"stories.subtitle_4", defaultMessage:"A short descriptive line will go here"})}/></div>
        <div className="st-column st-column-3"><Image img={'tmp/image3x1c.jpg'} title={props.intl.formatMessage({ id:"stories.title_5" ,defaultMessage:"Main crops by zone"})} subtitle={props.intl.formatMessage({ id:"stories.subtitle_5", defaultMessage:"A short descriptive line will go here"})}/></div>
      </div>

    </div>);
})

export default Stories
