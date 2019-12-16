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
        <p>  Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
      </div>


      <div className="microdata focal point title ">
        <p>
          <FormattedMessage id="microdata.table.table1.title" defaultMessage="Focal Point Datasets"></FormattedMessage>
        </p>
      </div>

      <div className="microdata table1 description">
          <p>
            <FormattedMessage id="microdata.table.table1.description" defaultMessage="  Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."></FormattedMessage>
          </p>
      </div>

        <DataSetsTable {...this.props}/>
        <Sources {...this.props}/>
      </div>)
  }
}


export default injectIntl(Microdata);
