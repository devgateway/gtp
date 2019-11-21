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

  const {stories_title_1,
      stories_subtitle_1,stories_title_2,
      stories_subtitle_2,
      stories_title_3,
      stories_subtitle_3,
      stories_title_4,
      stories_subtitle_4,
      stories_title_5,
      stories_subtitle_5,
      image_1,
      image_2,
      image_3,
      image_4,
      image_5,
    }=props
  return (<div className="home-stories">
    <div className="st-row">
      <div className="st-column st-column-6"><Image img={image_1}
        title={stories_title_1}
        subtitle={stories_subtitle_1}/>
    </div>
      <div className="st-column st-column-4"><Image img={image_2}

        title={stories_title_2}
        subtitle={stories_subtitle_2}/>
  </div>
    </div>
    <div className="st-row">
      <div className="st-column st-column-3"><Image img={image_3}
        title={stories_title_3}
        subtitle={stories_subtitle_3}/>
  </div>
      <div className="st-column st-column-3"><Image img={image_4}
        title={stories_title_3}
        subtitle={stories_subtitle_3}/>
  </div>
      <div className="st-column st-column-3"><Image img={image_5}
        title={stories_title_3}
        subtitle={stories_subtitle_3}/>
   </div>
    </div>

  </div>);
})

export default Stories
