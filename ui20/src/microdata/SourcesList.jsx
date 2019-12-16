import _ from 'lodash'
import React, { Component } from 'react'
import { Table,Pagination } from 'semantic-ui-react'
import {items2options} from '../indicators/DataUtil'
import {injectIntl,FormattedDate, FormattedMessage, FormattedHTMLMessage} from 'react-intl';
import {changeFilter,loadSources } from '../modules/Microdata'
import {connect} from 'react-redux';



class Sources extends Component {



    componentDidMount(){
      this.props.onLoadSources(this.props.intl.locale)
    }

    componentDidUpdate(prevProps) {

    }


  render() {

    const {onChangeFilter, intl:{locale},totalPages, pageable={}} = this.props
    const { page,pageNumber,pageSize}=pageable
    const paginationProps={
      data:[],
      totalPages:totalPages,
      activePage:pageNumber+1,
      boundaryRange: 1,
      siblingRange: 2,
      onPageChange:(e, { activePage })=>{
        onChangeFilter(['filters','datasets','pageNumber'],activePage -1,locale)
      },
      ellipsisItem: true,
      firstItem: false,
      lastItem: true,
      totalPages: null,
      size:'mini',
      prevItem:false,
      nextItem:true
    }

    return  (
      <div>
      <div className="microdata sources title">
        <p>
          <FormattedMessage id="microdata.sources.title" defaultMessage="Other Sources"></FormattedMessage>
        </p>
      </div>


      <Pagination {...paginationProps}/>


      </div>
    )
  }
}



const mapStateToProps = state => {
    const sources=state.getIn(['microdata','data','sources'])?state.getIn(['microdata','data','sources']).toJS():{}
    debugger;
    return {...sources}
}

const mapActionCreators = {
  onLoadSources:loadSources,
  onChangeFilter:changeFilter
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Sources));
