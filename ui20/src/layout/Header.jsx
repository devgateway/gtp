import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {withRouter} from "react-router";
import {

  Link
} from "react-router-dom";
import './header.scss';

const HeaderNav = (props) => {
  return <div className="header-nav">{props.children}</div>
}

const HeaderImage = (props) => {
  return <div className="header-main">{props.children}</div>
}

const HeaderNavButtons = (props) => {
  return (<div className="header-nav-bar">
    <div className="nav link">
      <FormattedMessage id="nav.home" defaultMessage={"Home"} values={""}/></div>
    <div className="nav link">
      <Link to={`/${props.lan}/analytic/production`}><FormattedMessage id="nav.production" defaultMessage={"Production"} values={""}/></Link>
    </div>
    <div className="nav link">
      <Link to={`/${props.lan}/analytic/marketPrice`}><FormattedMessage id="nav.market_price" defaultMessage={"Market Prices"} values={""}/></Link>
    </div>
    <div className="nav link">
      <Link to={`/${props.lan}/analytic/consumption`}><FormattedMessage id="nav.consumption" defaultMessage={"Consumption"} values={""}/></Link>
    </div>
    <div className="nav link" onClick={e => props.onChangeLanguage('fr')}>
      <FormattedMessage id="nav.lan.es" defaultMessage={"FR"} values={""}/></div>
    <div className="nav link" onClick={e => props.onChangeLanguage('en')}>
      <FormattedMessage id="nav.lan.es" defaultMessage={"EN"} values={""}/></div>
  </div>)
}

const HeaderTitle = (props) => {
  return (<div className="header-title">
    <div className="title">
      <FormattedMessage id="header.title" defaultMessage={"Senegal Agridata Platform"} values={""}/>
    </div>
    <div className="legend"><FormattedMessage id="header.legend" defaultMessage={"Unleashing the power of agriculture statistics"} values={""}/></div>
  </div>)
}

class Header extends React.Component {

  onChangeLanguage(lan) {

    const {location, history} = this.props;
    const newPath = '/'+lan + location.pathname.substr(3)
    this.props.history.push(newPath)
  }

  render() {
    const lan=this.props.location.pathname.split("/")[1];

    return (<div className="header">
      <HeaderNav>
        <HeaderTitle {...this.props}/>
        <HeaderNavButtons lan={lan} {...this.props}  onChangeLanguage={this.onChangeLanguage.bind(this)} />
      </HeaderNav>

    </div>);
  }
}
export default withRouter(Header)
