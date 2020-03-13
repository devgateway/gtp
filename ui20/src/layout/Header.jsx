import React, {Component} from 'react';
import {injectIntl,FormattedMessage} from 'react-intl';
import {withRouter} from "react-router";
import { Dropdown } from 'semantic-ui-react'
import messages from '../translations/messages'
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


const AnalystDropdown = withRouter(injectIntl((props) => (

  <Dropdown text={<FormattedMessage id="home.header.menu.analysis" defaultMessage={"Analysis"} values={""}/>}>
    <Dropdown.Menu>
      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/national`)} text={<FormattedMessage id="home.header.menu.indicators.national" defaultMessage={"National Indicators"} values={""}/>} />
      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/indicators`)} text={<FormattedMessage id="home.header.menu.indicators.sdg" defaultMessage={"SDG Indicators"} values={""}/>}/>
      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/gis/regional`)} text={<FormattedMessage id="home.header.menu.gis.regional" defaultMessage={"Regional Indicators Map"} values={""}/>} />
      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/gis/departmental`)} text={<FormattedMessage id="home.header.menu.gis.departamental" defaultMessage={"Departamental Indicators Map"} values={""}/>} />
      <Dropdown.Divider/>
      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/analytic/production`)} text={<FormattedMessage id="home.header.menu.production" defaultMessage={"Production"} values={""}/>}/>
      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/analytic/consumption`)} text={<FormattedMessage id="home.header.menu.consumption" defaultMessage={"Consumption"} values={""}/>} />
      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/analytic/marketPrice`)} text={<FormattedMessage id="home.header.menu.market" defaultMessage={"Market Price"} values={""}/>} />
      <Dropdown.Divider/>

      <Dropdown.Item onClick={e=>props.history.push(`/${props.match.params.lan}/microdata`)} text={<FormattedMessage id="home.header.menu.microdata" defaultMessage={"Microdata"} values={""}/>}/>


    </Dropdown.Menu>
  </Dropdown>
)))




const LanSwitcher = withRouter((props) => (
  <div>
    <div class="ui toggle checkbox">
      <div className={props.match.params.lan.toUpperCase()=='FR'?'active':''}>FR</div>
        <input type="checkbox" onChange={e=>{
              const newPath=(e.target.checked==true)? '/en' + props.location.pathname.substr(3): '/fr' + props.location.pathname.substr(3)
               props.history.push(newPath)
            }
        } name="lan" defaultChecked={props.match.params.lan.toUpperCase()=='EN'?'checked':''}/><label></label>
      <div className={props.match.params.lan.toUpperCase()=='EN'?'active':''}>EN</div>
      </div>
  </div>))

const HeaderNavButtons = withRouter((props) => {
  return (<div className="header-nav-bar">
    <div className="nav link"><Link to={`/${props.match.params.lan}/home`}><FormattedMessage id="home.header.menu.home" defaultMessage={"Home"} values={""}/></Link></div>
    <div className="nav link"><Link to={`/${props.match.params.lan}/initiatives`}><FormattedMessage id="home.header.menu.agriculutural.initiatives" defaultMessage={"Agricultural Initiatives"} values={""}/></Link></div>
    <div className="nav link menu"><AnalystDropdown></AnalystDropdown></div>
    <div className="nav link"><Link to={`/${props.match.params.lan}/partners`}><FormattedMessage id="home.header.menu.partners" defaultMessage={"Partners"} values={""}/></Link></div>
    <div className="nav  separator"></div>
    <div className="nav link"><LanSwitcher/></div>
    <div className="nav  separator"></div>
    <div className="nav link">  <a href="/admin">  <img className="logo admin" src="/header_admin_logo.png"/></a></div>


  </div>)
})

const HeaderTitle = (props) => {
  return (<div className="header-title">
    <div className="title">
      <FormattedMessage id="home.header.title" defaultMessage={"Senegal Agridata Platform"} values={""}/>
    </div>
    <div className="legend"><FormattedMessage id="home.header.sub.title" defaultMessage={"Unleashing the power of agricultural statistics"} values={""}/></div>
  </div>)
}

class Header extends React.Component {

    constructor(props) {
      super(props);
    }

  onChangeLanguage(lan) {
    const {location, history} = this.props;
    const newPath = '/'+lan + location.pathname.substr(3)
    this.props.history.push(newPath)
  }

  render() {
    const lan=this.props.location.pathname.split("/")[1];

    return (<div className={`header ${this.props.className}` } ref={this.props.divRef}>
      <HeaderNav>
        <HeaderTitle {...this.props}/>
        <HeaderNavButtons lan={lan} {...this.props}  />
      </HeaderNav>

    </div>);
  }
}
export default withRouter(Header)
