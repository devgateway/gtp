import _ from 'lodash'
import React, { Component } from 'react'
import { Table,Pagination } from 'semantic-ui-react'
import {items2options} from '../indicators/DataUtil'
import {injectIntl,FormattedDate, FormattedMessage, FormattedHTMLMessage} from 'react-intl';
import {changeFilter,changePage,loadSources } from '../modules/Microdata'
import {connect} from 'react-redux';



class Sources extends Component {



    componentDidMount(){
      this.props.onLoadSources(this.props.intl.locale)
    }

    componentDidUpdate(prevProps) {

    }


  render() {

    const {onChangeFilter, intl:{locale},totalPages, pageable={},content,onChangePage} = this.props
    const { page,pageNumber,pageSize}=pageable
    const paginationProps={

       activePage: page+1,
       boundaryRange: 1,
       siblingRange: 2,
       showEllipsis: true,
       showFirstAndLastNav: false,
       showPreviousAndNextNav: true,
       totalPages: totalPages,
       size:'mini',
       firstItem:false,
       lastItem:false,
       onPageChange:(e, { activePage })=>{
        onChangePage(['filters','sources','pageNumber'],activePage -1,locale,['SOURCES'])
      },

    }

    return  (
      <div className="microdata sources">
      <div className="microdata sources title">
        <p>
          <FormattedMessage id="microdata.sources.title" defaultMessage="Other data sources"></FormattedMessage>
        </p>
      </div>
        <ul>
          {content&&content.map(c=>
            <li>
              <a href={c.link}> {c.title}</a> <span className="microdata sources description">{c.description}</span></li>)}
      </ul>
      <div className="pagination wrapper">

        <Pagination {...paginationProps}/>
        </div>
      </div>
    )
  }
}



const mapStateToProps = state => {
    const sources=state.getIn(['microdata','data','sources'])?state.getIn(['microdata','data','sources']).toJS():{}

    return {...sources}
}

const mapActionCreators = {
  onLoadSources:loadSources,
  onChangeFilter:changeFilter,
  onChangePage:changePage
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Sources));
