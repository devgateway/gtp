import React, {Component} from 'react';
import {injectIntl, FormattedMessage, FormattedHTMLMessage} from 'react-intl';

import './stories.scss';

import messages from '../translations/messages'

const Image = (props) => {

  const css = {
    backgroundImage: `url(data:image/png;base64,${props.img})`
  }
  return (

    <div className="image" style={css}>
      <div className="image-text">
        <div className="image-title"><a href={props.link} target="_blank"> {props.title}</a></div>
        <div className="image-sub-title"><a href={props.link} target="_blank">{props.subtitle}</a></div>
      </div>
      <div className="mask"/>

  </div>)
}

const Stories = injectIntl((props) => {
  debugger;

  if (props.links){
  let subfix=''
  const lan=props.intl.locale;
  if (lan=='fr'){
    subfix='Fr'
  }

  const stories_title_1=  props.links[0]['title'+subfix]
  const stories_subtitle_1= props.links[0]['subtitle'+subfix]
  const image_1= props.links[0].base64


  const stories_title_2=  props.links[1]['title'+subfix]
  const stories_subtitle_2= props.links[1]['subtitle'+subfix]
  const image_2= props.links[1].base64


  const stories_title_3=  props.links[2]['title'+subfix]
  const stories_subtitle_3= props.links[2]['subtitle'+subfix]
  const image_3= props.links[2].base64


  const stories_title_4=  props.links[3]['title'+subfix]
  const stories_subtitle_4= props.links[3]['subtitle'+subfix]
  const image_4= props.links[3].base64


  const stories_title_5=  props.links[4]['title'+subfix]
  const stories_subtitle_5= props.links[4]['subtitle'+subfix]
  const image_5= props.links[4].base64


  return (<div className="home-stories">
    <div className="st-row">
      <div className="st-column st-column-6"><Image img={image_1}
        title={stories_title_1}
        link={props.links[0].link}
        subtitle={stories_subtitle_1}/>
    </div>
      <div className="st-column st-column-4"><Image img={image_2}

        title={stories_title_2}
        link={props.links[1].link}
        subtitle={stories_subtitle_2}/>
  </div>
    </div>
    <div className="st-row">
      <div className="st-column st-column-3"><Image img={image_3}
        title={stories_title_3}
        link={props.links[2].link}
        subtitle={stories_subtitle_3}/>
  </div>
      <div className="st-column st-column-3"><Image img={image_4}
        title={stories_title_4}
        link={props.links[3].link}
        subtitle={stories_subtitle_4}/>
  </div>
      <div className="st-column st-column-3">
       <Image img={image_5}
         link={props.links[4].link}
        title={stories_title_5}
        subtitle={stories_subtitle_5}/>
   </div>
    </div>

  </div>);
}else{
  return null;
}
})

export default Stories
