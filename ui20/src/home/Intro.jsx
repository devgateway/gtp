import React from 'react';
import {FormattedMessage,FormattedHTMLMessage} from 'react-intl';
import './intro.scss';




const MainIntro = (props) => {
  return <div className="main">{props.children}</div>
}


const IntroTitle=(props)=>{
    return (<div className="title"><FormattedMessage id="home.intro.title" values={""}/> </div>)
}
const IntroText=(props)=>{
    return (<div className="text"><FormattedHTMLMessage id="home.intro.text" defaultMessage={"Senegal AgriData Platform - a joint initiative between Initiative Prospective Agricole et Rurale (IPAR) and the National Statistical Agency (ANSD) to foster agricultural development in Senegal"} values={""}/> </div>)
}

// eslint-disable-next-line no-unused-vars
const IntroLink=(props)=>{
    return (<div className="btn-link">  <FormattedMessage id="home.intro.link.learn" defaultMessage={"Click here to learn more "} values={""}/></div>)
}



const Header=(props)=>{
  return (<div className="intro">
    <MainIntro>
        <IntroTitle></IntroTitle>
        <IntroText></IntroText>

      </MainIntro>
  </div>);
}

export default Header
