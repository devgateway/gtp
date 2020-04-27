/* eslint-disable */
import "./microdata.scss"

import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import DataSetsTable from './DataSetsTable'
import Sources from './SourcesList'

class Microdata extends Component {

  constructor(props) {
    super(props);
  }


  render() {

    const {onChangeFilter}=this.props

    return (<div className="microdata container">
      <div className="microdata title">
        <p>
          <FormattedMessage id="microdata.page.title" defaultMessage="Microdata"></FormattedMessage>
        </p>
      </div>


      <div className="microdata description">
        <p>  <FormattedMessage id='microdata.page.description' defaultMessage="The Microdata page displays agricultural datasets that have been preloaded by each responsible partner organization. Where available, a given dataset will be displaying a link that will connect the ANSD data repository where users can consult reports, studies and other metadata related to a specific dataset. The site may also display non-official data sources that users can access by clicking on the links provided."/></p>
      </div>


      <div className="microdata focal point title ">
        <p>
          <FormattedMessage id="microdata.table.datasets_title" defaultMessage="Organization Dataset"></FormattedMessage>
        </p>
      </div>

      <div className="microdata table1 description">
          <p>
            <FormattedMessage id="microdata.table.datasets_description" defaultMessage="The section displays datasets starting with the most recent ones. The table will be updated as new information is uploaded and will only show the 10 most recent datasets. Users can use the Search function or the Pagination options to retrieve hidden dataset that have been previously made available."></FormattedMessage>
          </p>
      </div>

        <DataSetsTable {...this.props}/>
        <Sources {...this.props}/>
      </div>)
  }
}


export default injectIntl(Microdata);
