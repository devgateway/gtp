import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';


import './header.scss';


const HeaderNav = (props) => {
  return <div className="header-nav">{props.children}</div>
}

const HeaderImage = (props) => {
  return <div className="header-main">{props.children}</div>
}

const HeaderNavButtons=(props)=>{
  return (<div className="header-nav-bar">
      <div className="nav link">  <FormattedMessage id="nav.home" defaultMessage={"Home"} values={""}/></div>
      <div className="nav link">  <FormattedMessage id="nav.dashboard" defaultMessage={"Dashboard"} values={""}/></div>
      <div className="nav link">  <FormattedMessage id="nav.resources" defaultMessage={"Resources"} values={""}/></div>
      </div>)
}


const HeaderTitle = (props) => {
  return (
    <div className="header-title">
    <div className="title">
    <FormattedMessage id="header.title" defaultMessage={"Senegal Agridata Platform"} values={""}/>
    </div>
    <div className="legend"><FormattedMessage id="header.legend" defaultMessage={"Unleashing the power of agriculture statistics"} values={""}/></div>
  </div>)
}


const Header=(props)=>{
  return (<div className="header">
      <HeaderNav>
        <HeaderTitle {...props}/>
        <HeaderNavButtons {...props}/>
      </HeaderNav>

  </div>);
}

export default Header
