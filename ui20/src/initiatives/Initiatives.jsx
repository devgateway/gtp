/* eslint-disable */
import React, { useEffect , createRef} from 'react'
import {loadInitiativesTypes,loadInitiativesItems} from '../modules/Data'
import {connect} from 'react-redux';
import {FormattedMessage,FormattedDate, injectIntl} from 'react-intl';
import messages from '../translations/messages'
import {
  Grid,
  Image,
  Container,
  Label,
  Pagination
} from 'semantic-ui-react'

import './initiatives.scss'


const Initiatives = ({intl, onLoad, onLoadItems , types, items}) => {

  useEffect(() => {
    if (onLoad){
        onLoad(intl.locale)
    }

  }, [])
return (<div className="initiatives container">
    <div className="initiatives title">
      <p>
        <FormattedMessage id="initiatives.page.title" defaultMessage="Agricultural Initiatives, Policies, Strategies, Programs"></FormattedMessage>
      </p>
    </div>


    <div className="initiatives description">
      <p><FormattedMessage id='initiatives.page.description' defaultMessage="The Agricultural Initiatives page presents the national policies and strategies that govern and promote the sustainable development of the agricultural sector including projects and programs, studies and research documents as well as information in the agricultural sector budget. The page will be updated with studies that are conducted and as more projects are implemented in the agricultural sector."/></p>
    </div>



    {types &&  types.map(d=>(<Items onLoadItems={onLoadItems} {...d} items={items}></Items>))}


    </div>)
}


const Items = injectIntl(({intl,id,type,label,labelFr, onLoadItems , items}) => {
  useEffect(() => {
    if (onLoadItems){
        onLoadItems(type,intl.locale)
    }
  }, [])

  const elements=(items&&items.get(type))?items.get(type):{data:{pageable:{}}}


  const {pageable:{page,pageNumber,pageSize}, totalPages, content=[]} = elements.data

  const paginationProps={
     activePage: page+1,
     boundaryRange: 1,
     siblingRange: 2,
     ellipsisItem: false,
     totalPages: totalPages,
     size:'mini',
     firstItem:false,
     lastItem:false,
     onPageChange:(e, { activePage })=>{
         onLoadItems(type,intl.locale, activePage -1)

    },
  }


  var i=1;
return (<div className="initiatives container">
        <div className="initiatives list title">
          <p>
            {intl.locale=='en'?label:''}
            {intl.locale=='fr'?labelFr:''}

          </p>
        </div>
          <Container fluid className="initiatives list container">
              <Grid fluid width={2} >

              {content.map(e=>{


                  return(

                      <Grid.Column width={content.length > 1?8:16}>

                        <div className="date"><FormattedDate value={e.publicationDate} year="numeric" month="long" day="2-digit" /></div>

                        <div className="title">  {e.link?<a href={e.link}>{e.title}</a>:<a href={`/files/download/${e.fileId}`}>{e.title}</a>}</div>

                        <div className="description"> <a href={e.link}>{e.description}</a> </div> {e.fileId &&   <div className="download">
                        <a href={`/files/download/${e.fileId}`}>
                          <FormattedMessage id='initiatives.download_file' defaultMessage="Download File"/>
                        </a></div>}
                      </Grid.Column>

              )

                })}

                {content== 0 &&<Grid.Column width={16}><Label   ribbon={true} className="centered" basic color="olive" inverted>  <FormattedMessage id="data.no_available" defaultMessage="No data available"> No data available</FormattedMessage> </Label></Grid.Column>}
              </Grid>

                {content.length > 0 &&  <div className="pagination wrapper"><Pagination   {...paginationProps}/></div>}


            </Container>


    </div>)
})





const mapActionCreators = {
  onLoad:loadInitiativesTypes,
  onLoadItems:loadInitiativesItems
};


const mapStateToProps = (state, ownProps) => {
  const types=state.getIn(['data','initiatives','types','data'])
  const items=state.getIn(['data','initiatives','items','data'])

  if(items){

  }
  return {types,items}
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Initiatives));
