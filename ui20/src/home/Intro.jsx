import React, {Component} from 'react';
import {FormattedMessage,FormattedHTMLMessage} from 'react-intl';
import './intro.scss';




const MainIntro = (props) => {
  return <div className="main">{props.children}</div>
}


const IntroTitle=(props)=>{
    return (<div className="title"><FormattedMessage id="intro.title" defaultMessage={"Senegal Agridata Platform"} values={""}/> </div>)
}
const IntroText=(props)=>{
    return (<div className="text"><FormattedHTMLMessage id="intro.text" defaultMessage={"The Agridata Platform for the agricultural sector in Senegal<br> in collaboration between the Agricultural and Rural Prospective Initiative (IPAR)<br> and the National Agency of Statistics and Demography (ANSD)."} values={""}/> </div>)
}

const IntroLink=(props)=>{
    return (<div className="btn-link">  <FormattedMessage id="intro.learn_link" defaultMessage={"Learn more"} values={""}/></div>)
}



const Header=(props)=>{
  return (<div className="intro">
    <MainIntro>
        <IntroTitle></IntroTitle>
        <IntroText></IntroText>
        <IntroLink></IntroLink>
      </MainIntro>
  </div>);
}

export default Header
