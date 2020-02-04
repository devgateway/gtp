import React, {Component} from 'react';
import {FormattedMessage, FormattedHTMLMessage, injectIntl} from 'react-intl';
import { Tab } from 'semantic-ui-react'
import messages from '../translations/messages'

import './links.scss'

const PresnetedBy = (props) => {
  return (<div className="home presented by">
    <div className="col_1">
      <div className="presented title">
        <span><FormattedMessage id="home.presented.by" defaultMessage={"presented by"} values={""}/></span>
        <div className="presented logo1"/>
          <div className="presented logo"/>
      </div>
      <div className="presented text"><FormattedMessage id="home.presented.text" defaultMessage={`The AgriData platform aims to bring science to the Senegalese agricultural sector by providing key support decision information to policymakers while empowering farmers and other relevant value chain actors.`} values={""}/></div>
    </div>
    <div className="col_2">
      <div className="presented image"></div>
    </div>

  </div>)
}

const Source=({type})=>{
  return   (<div className="links source">
  <div className="source-icon"></div>

    {type=='microdata'&&<FormattedMessage id="home.tabs.microdata.source" defaultMessage={"Data comes from DAPSA, ANSD and FAO"}/>}
    {type=='indicator'&&<FormattedMessage id="home.tabs.indicator.source" defaultMessage={"Data comes from DAPSA, ANSD and FAO"}/>}
    {type=='market'&&<FormattedMessage id="home.tabs.market.source" defaultMessage={"Data comes from DAPSA, ANSD and FAO"}/>}

  </div>)

}

const LinksBlock = injectIntl((props) => {
  const {intl}=props
  const panes=  [{
      menuItem:  {className:'link microdata', key: 'microdata', icon: '', content: intl.formatMessage(messages.home_tabs_microdata_title)},
      render: () =>(
        <div className="links explanation">
            {intl.formatMessage(messages.home_tabs_microdata_text)}
            <Source type="microdata"/>
        </div>
      )
    },
    {
        menuItem:  {className:'link indicator', key: 'indicator', icon: '', content: intl.formatMessage(messages.home_tabs_indicator_title)},
        render: () =>(
          <div className="links explanation">
          {intl.formatMessage(messages.home_tabs_indicator_text)}
          <Source type="indicator"/>
            </div>)
      },
      {
          menuItem:  {className:'link market', key: 'market', icon: '', content: intl.formatMessage(messages.home_tabs_market_title)},
          render: () =>(<div className="links explanation">
          {intl.formatMessage(messages.home_tabs_market_text)}
            <Source type="market"/>
          </div>)
        }
  ]

  return (<div className="home-links-container">

    <PresnetedBy></PresnetedBy>
    <Tab className="home-links-tabs" key="links" menu={{ pointing: true }} panes={panes} />
    <div className="source-separator "></div>
  </div>);
})

export default LinksBlock
