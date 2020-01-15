import React, { useEffect ,Component,useState, createRef} from 'react'
import {loadInitiativesTypes,loadInitiativesItems} from '../modules/Data'
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
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
  Input
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
        <FormattedMessage id="initiatives.page.title" defaultMessage="initiatives"></FormattedMessage>
      </p>
    </div>


    <div className="initiatives description">
      <p>  <FormattedMessage id='initiatives.page.description' defaultMessage="The Microdata page will display agricultural datasets that have been preloaded by each responsible partner organization. The site will also display, non-official data sources that users can access by clicking on the links provided. Where available, a given dataset will be displaying a link that will connect the ANSD data repository when users can consult reports, studies and other metadata related to a specific dataset."/></p>
    </div>


    <div className="initiatives type  title ">
      <p>
        <FormattedMessage id="initiatives.type.title" defaultMessage="Partner Type(s)"></FormattedMessage>
      </p>
    </div>

    <div className="initiatives type description">
        <p>
          <FormattedMessage id="initiatives.type.description" defaultMessage="The organization datasets will display data groupes by a respective organization. The table will be updated as new information is downloaded and will only show the 10 most recent datasets. Users can use the Search function to retrieve hidden dataset that have been previouly made available."></FormattedMessage>
        </p>
    </div>

    <div className="initiatives list title ">
      <p>
        <FormattedMessage id="initiatives.list.title" defaultMessage="initiatives"></FormattedMessage>
      </p>
    </div>
    {types &&  types.map(d=>(<Items onLoadItems={onLoadItems} {...d} items={items}></Items>))}
    </div>)
}




const Items = injectIntl(({intl,id,label,labelFr, onLoadItems , items}) => {

  const elements=items&&items.get(id)?items.get(id).data:[]

  debugger;

  useEffect(() => {
    if (onLoadItems){
        onLoadItems(id,intl.locale)
    }

  }, [])
return (<div className="initiatives container">
    <div className="initiatives title">
      <p>
        {label} {elements.total}
      </p>
    </div>
    {elements.total > 0 && elements.map(e=>{
        return <h1>{e.title}</h1>
      })}


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
    debugger;
  }
  return {types,items}
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Initiatives));
