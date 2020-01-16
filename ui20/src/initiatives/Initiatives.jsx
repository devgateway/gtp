import React, { useEffect ,Component,useState, createRef} from 'react'
import {loadInitiativesTypes,loadInitiativesItems} from '../modules/Data'
import {connect} from 'react-redux';
import {FormattedMessage,FormattedDate, injectIntl} from 'react-intl';
import Sticky from './Sticky'
import {
  Dropdown,
  Grid,
  Image,
  Ref,
  Rail,
  Segment,
  Container,
  Label,
  Menu,
  Input,
  Pagination
} from 'semantic-ui-react'

import './initiatives.scss'

const contextRef = createRef()

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
      <p>  <FormattedMessage id='initiatives.page.description' defaultMessage="orem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ac nibh aliquet, placerat erat vel, iaculis urna. Maecenas sagittis eu ante et consectetur. Proin ac vulputate odio. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus."/></p>
    </div>


    <Container fluid>
    <Grid>
        <Grid.Row>
          <Grid.Column >
            <img src="/back_initiatives.png" className="ui image  fluid"/>
          </Grid.Column>
        </Grid.Row>
        <Grid.Row>
          <Grid.Column width={8} className="initiatives description">
          <p>
            <FormattedMessage id="initiatives.left.description" defaultMessage="The organization datasets will display data groupes by a respective organization. The table will be updated as new information is downloaded and will only show the 10 most recent datasets. Users can use the Search function to retrieve hidden dataset that have been previouly made available."></FormattedMessage>
          </p>
          </Grid.Column>
          <Grid.Column width={8} className="initiatives description">
          <p>
            <FormattedMessage id="initiatives.rigth.description" defaultMessage="The organization datasets will display data groupes by a respective organization. The table will be updated as new information is downloaded and will only show the 10 most recent datasets. Users can use the Search function to retrieve hidden dataset that have been previouly made available."></FormattedMessage>
          </p>
          </Grid.Column>
        </Grid.Row>
    </Grid>
    </Container>

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

  if(items&&items.get(type)){
    debugger;
  }

return (<div className="initiatives container">
        <div className="initiatives list title">
          <p>
            {label}
          </p>
        </div>
          <Container fluid className="initiatives list container">
              <Grid fluid width={2} stackable>
              {content.map(e=>{
                  return(

                      <Grid.Column width={8}>

                      <div className="icon file"><img src="/icon_file.png"/></div>

                      <div className="date"><FormattedDate value={e.publicationDate} year="numeric" month="long" day="2-digit" /></div>

                      <div className="title">  <a href={e.link}>{e.title}</a></div>

                      <div className="description"> <a href={e.link}>{e.description}</a> </div>


                      </Grid.Column>
              )

                })}

                {content== 0 &&<Grid.Column width={16}><Label   ribbon={true} className="centered" basic color="olive" inverted>This section has no data</Label></Grid.Column>}
              </Grid>

                {content.length > 0 &&<Pagination   {...paginationProps}/>}


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
