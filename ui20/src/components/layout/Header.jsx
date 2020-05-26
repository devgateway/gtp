import React, {Component} from 'react';
import {injectIntl,FormattedMessage} from 'react-intl';
import {withRouter} from "react-router";
import { Dropdown } from 'semantic-ui-react'
import {
  Link
} from "react-router-dom";
import './header.scss';

const HeaderNav = (props) => {
  return <div className="header-nav">{props.children}</div>
}

// eslint-disable-next-line no-unused-vars
const HeaderImage = (props) => {
  return <div className="header-main">{props.children}</div>
}


const AnalystDropdown = withRouter(injectIntl((props) => {
  const { intl } = props
  return (
    <Dropdown text={intl.formatMessage({id: "home.header.menu.graphics"})}>
      <Dropdown.Menu>
        <Dropdown.Item onClick={e => props.history.push(`/${props.match.params.lan}/water-resources`)}
                       text={<FormattedMessage id="home.pane.waterResources.title" values={""}/>}/>
        <Dropdown.Item onClick={e => props.history.push(`/${props.match.params.lan}/agriculture-and-market`)}
                       text={<FormattedMessage id="home.pane.agricultureAndMarkets.title" values={""}/>}/>
        <Dropdown.Item onClick={e => props.history.push(`/${props.match.params.lan}/livestock`)}
                       text={<FormattedMessage id="home.pane.livestock.title" values={""}/>}/>
      </Dropdown.Menu>
    </Dropdown>)
}))

// eslint-disable-next-line no-unused-vars
const LanSwitcher = withRouter((props) => (
  <div>
    <div class="ui toggle checkbox">
      <div className={props.match.params.lan.toUpperCase() === 'FR' ? 'active' : ''}>FR</div>
        <input type="checkbox" onChange={e=>{
              const newPath = (e.target.checked === true) ? '/en' + props.location.pathname.substr(3) : '/fr' + props.location.pathname.substr(3)
               props.history.push(newPath)
            }
        } name="lan" defaultChecked={props.match.params.lan.toUpperCase() === 'EN' ? 'checked' : ''}/><label></label>
      <div className={props.match.params.lan.toUpperCase() === 'EN' ? 'active' : ''}>EN</div>
      </div>
  </div>))

const HeaderNavButtons = withRouter((props) => {
  return (<div className="header-nav-bar">
    <div className="nav link"><Link to={`/${props.match.params.lan}/home`}><FormattedMessage id="home.header.menu.home" defaultMessage={"Home"} values={""}/></Link></div>
    <div className="nav link"><Link to={`/${props.match.params.lan}/about`}>
      <FormattedMessage id="home.header.menu.about" values={""}/></Link>
    </div>
    <div className="nav link"><Link to={`/${props.match.params.lan}/bulletins`}>
      <FormattedMessage id="home.header.menu.bulletins" values={""}/></Link>
    </div>
    <div className="nav link"><Link to={`/${props.match.params.lan}/members`}>
      <FormattedMessage id="home.header.menu.members" values={""}/></Link>
    </div>
    <div className="nav link menu"><AnalystDropdown></AnalystDropdown></div>
    {
      // <div className="nav  separator"></div>
      // <div className="nav link"><LanSwitcher/></div>
    }
    <div className="nav  separator"></div>
    <div className="nav link">
      <a href="/admin">
        <img className="logo admin" src="/header_admin_logo.jpg" alt={""}/>
      </a>
    </div>
  </div>)
})

const HeaderTitle = (props) =>
    (<div className="header-title">
    <div className="title">
      <FormattedMessage id="home.header.title" defaultMessage={"Senegal Agridata Platform"} values={""}/>
    </div>
    <div className="legend"><FormattedMessage id="home.header.sub.title" defaultMessage={"Unleashing the power of agricultural statistics"} values={""}/></div>
  </div>);


class Header extends Component {

  onChangeLanguage(lan) {
    // eslint-disable-next-line no-unused-vars
    const {location, history} = this.props;
    const newPath = '/' + lan + location.pathname.substr(3)
    this.props.history.push(newPath)
  }

  render() {
    const lan = this.props.location.pathname.split("/")[1];

    return (<div className={`header ${this.props.className}` } ref={this.props.divRef}>
      <HeaderNav>
        <HeaderTitle {...this.props}/>
        <HeaderNavButtons lan={lan} {...this.props}  />
      </HeaderNav>

    </div>);
  }
}
export default withRouter(Header)
