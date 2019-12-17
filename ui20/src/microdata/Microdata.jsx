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
        <p>  <FormattedMessage id='microdata.page.description' defaultMessage="The Microdata page will display agricultural datasets that have been preloaded by each responsible partner organization. The site will also display, non-official data sources that users can access by clicking on the links provided. Where available, a given dataset will be displaying a link that will connect the ANSD data repository when users can consult reports, studies and other metadata related to a specific dataset."/></p>
      </div>


      <div className="microdata focal point title ">
        <p>
          <FormattedMessage id="microdata.table.datasets.title" defaultMessage="Organization Dataset"></FormattedMessage>
        </p>
      </div>

      <div className="microdata table1 description">
          <p>
            <FormattedMessage id="microdata.table.datasets.description" defaultMessage="The organization datasets will display data groupes by a respective organization. The table will be updated as new information is downloaded and will only show the 10 most recent datasets. Users can use the Search function to retrieve hidden dataset that have been previouly made available."></FormattedMessage>
          </p>
      </div>

        <DataSetsTable {...this.props}/>
        <Sources {...this.props}/>
      </div>)
  }
}


export default injectIntl(Microdata);
