import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {withRouter} from "react-router";
import './header.scss';
import {Popup} from "semantic-ui-react"

// eslint-disable-next-line no-unused-vars
const HeaderImage = (props) => {
  return <div className="header-main">{props.children}</div>
}

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

const HeaderTitle = (props) => {
  const legend = <FormattedMessage id="home.header.sub.title"/>
  return (<div className="header-title">
    <div className="title">
      <FormattedMessage id="home.header.title" defaultMessage={"Senegal Agridata Platform"} values={""}/>
    </div>
    <Popup
      position="bottom center"
      trigger={
        <div className="legend">
          {legend}
        </div>
      }>
      <Popup.Content>{legend}</Popup.Content>
    </Popup>
  </div>)
}


export default class Header extends Component {

  render() {
    return (
      <div id="ad3-header" className='header sticky' ref={this.props.divRef}>
        <div className="header-nav">
          <HeaderTitle {...this.props}/>
        </div>
      </div>);
  }
}
