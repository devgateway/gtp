import React, {Component} from 'react';
import {injectIntl, FormattedMessage, FormattedHTMLMessage} from 'react-intl';

import './stories.scss';

import messages from '../translations/messages'

const Image = (props) => {

  const css = {
    backgroundImage: `url(${props.img})`
  }
  return (<div className="image" style={css}>
    <div className="image-text">
      <div className="image-title">{props.title}</div>
      <div className="image-sub-title">{props.subtitle}</div>
    </div>
    <div className="mask"/>

  </div>)
}

const Stories = injectIntl((props) => {

  return (<div className="home-stories">
    <div className="st-row">
      <div className="st-column st-column-6"><Image img={'tmp/image6x1.jpg'}
        title={props.intl.formatMessage(messages.stories_title_1)}
        subtitle={props.intl.formatMessage(messages.stories_subtitle_1)}/>
    </div>
      <div className="st-column st-column-4"><Image img={'tmp/image4x1.jpg'}

        title={props.intl.formatMessage(messages.stories_title_2)}
        subtitle={props.intl.formatMessage(messages.stories_subtitle_2)}/>
  </div>
    </div>
    <div className="st-row">
      <div className="st-column st-column-3"><Image img={'tmp/image3x1a.jpg'}
        title={props.intl.formatMessage(messages.stories_title_3)}
        subtitle={props.intl.formatMessage(messages.stories_subtitle_3)}/>
  </div>
      <div className="st-column st-column-3"><Image img={'tmp/image3x1b.jpg'}
        title={props.intl.formatMessage(messages.stories_title_3)}
        subtitle={props.intl.formatMessage(messages.stories_subtitle_3)}/>
  </div>
      <div className="st-column st-column-3"><Image img={'tmp/image3x1c.jpg'}
        title={props.intl.formatMessage(messages.stories_title_3)}
        subtitle={props.intl.formatMessage(messages.stories_subtitle_3)}/>
   </div>
    </div>

  </div>);
})

export default Stories
