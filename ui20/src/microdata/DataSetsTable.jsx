import _ from 'lodash'
import React, { Component } from 'react'
import { Table,Pagination } from 'semantic-ui-react'

import {CustomFilterDropDown,TextInput,DateInput} from '../indicators/Components'
import {items2options} from '../indicators/DataUtil'
import {injectIntl,FormattedDate, FormattedMessage, FormattedHTMLMessage} from 'react-intl';
import {changeFilter,loadDatasets } from '../modules/Microdata'
import {connect} from 'react-redux';



class TableComponent extends Component {

  state = {

      data: [],

      activePage: null,
      boundaryRange: 1,
      siblingRange: 2,
      showEllipsis: true,
      showFirstAndLastNav: false,
      showPreviousAndNextNav: true,
      totalPages: null,
    }



    handleSort = (clickedColumn) => () => {
      const { column, data, direction } = this.state

      if (column !== clickedColumn) {
        this.setState({
          column: clickedColumn,
          data: _.sortBy(data, [clickedColumn]),
          direction: 'ascending',
        })

        return
      }

      this.setState({
        data: data.reverse(),
        direction: direction === 'ascending' ? 'descending' : 'ascending',
      })
    }

    componentDidMount(){
      this.props.onLoadDatasets()
    }

    componentDidUpdate(prevProps) {
    // Uso tipico (no olvides de comparar los props):

      debugger;
    if (this.props.datasets !== prevProps.datasets && this.props.datasets) {
      debugger;

        const newState=Object.assign({},this.state,
          {
          data:this.props.datasets?this.props.datasets.getIn(['content']).toJS():[],
          totalPages:this.props.datasets.getIn(['totalPages']),
          activePage:this.props.datasets.getIn(['pageable','page'])+1,
        })

          this.setState(newState)
    }
  }

  render() {

    const {onChangeFilter, keyword, startDate, endDate, organizations=[], selectedOrganizations=[], intl, datasets={}, onPageChange  } = this.props
    let column = null
    let direction = null
    debugger;
    return  (
      <div>
            <div className="table filters">
                  <div className="title">Filters</div>
                  <div className="item"><TextInput text={intl.formatMessage({id:'filters.keyword.label', defaultMessage:"Keyword Search"}) }
                  onChange={val=>onChangeFilter(['filters','datasets','text'],val)} name="keyword" value={keyword}/></div>
                  <div className="item"><DateInput locale={intl.locale}  text={intl.formatMessage({ id:'filters.start_date.label', defaultMessage:"Start Date"})}
                  onChange={val=>onChangeFilter(['filters','datasets','realMinDate'],val)} name="startDate" value={startDate}/></div>
                  <div className="item">
                  <DateInput locale={intl.locale}
                  text={intl.formatMessage({ id:'filters.end_date.label', defaultMessage:"End Date"})}
                  onChange={val=>onChangeFilter(['filters','datasets','realMaxDate'],val)} name="endDate" value={endDate}/></div>
                  <div className="item"><CustomFilterDropDown  text={intl.formatMessage({id:'filters.organization.label', defaultMessage:"Organizations"})}  options={items2options(organizations,intl)}
                  selected={selectedOrganizations} onChange={value => {onChangeFilter(['filters','datasets','organization'],value)}}/></div>

            </div>
          <Table sortable celled fixed>

                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell sorted={column === 'type' ? direction : null} onClick={this.handleSort('type')}>
                      <FormattedMessage id="microdata.table.heder.type" defaultMessage="Type"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'title' ? direction : null} onClick={this.handleSort('title')}>
                        <FormattedMessage id="microdata.table.heder.title" defaultMessage="Title"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'organization' ? direction : null} onClick={this.handleSort('organization')}>
                      <FormattedMessage id="microdata.table.heder.organization" defaultMessage="Organization"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'creator' ? direction : null} onClick={this.handleSort('creator')}>
                      <FormattedMessage id="microdata.table.heder.creator" defaultMessage="Creator"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'createdDate' ? direction : null} onClick={this.handleSort('createdDate')}>
                      <FormattedMessage id="microdata.table.heder.createdDate" defaultMessage="createdDate"/>
                    </Table.HeaderCell>

                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {this.state.data &&_.map(this.state.data, ({id, organization, title, creator, createdDate, fileId, type}) => (
                    <Table.Row key={id}>
                     <Table.Cell>{type}</Table.Cell>
                     <Table.Cell>{title}</Table.Cell>
                     <Table.Cell>{organization}</Table.Cell>
                      <Table.Cell>{creator}</Table.Cell>
                      <Table.Cell>  <FormattedDate value={createdDate} /></Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>
              <div className="pagination wrapper">
              <Pagination
                  activePage={this.state.activePage}
                  boundaryRange={this.state.boundaryRange}
                  onPageChange={(e, { activePage })=>{
                    onChangeFilter(['filters','datasets','pageNumber'],activePage -1)
                  }}
                  size='mini'
                  siblingRange={this.state.siblingRange}
                  totalPages={this.state.totalPages}
                  // Heads up! All items are powered by shorthands, if you want to hide one of them, just pass `null` as value
                  ellipsisItem={this.state.showEllipsis ? undefined : null}
                  firstItem={this.state.showFirstAndLastNav ? undefined : null}
                  lastItem={this.state.showFirstAndLastNav ? undefined : null}
                  prevItem={this.state.showPreviousAndNextNav ? undefined : null}
                  nextItem={this.state.showPreviousAndNextNav ? undefined : null}
          />
              </div>
      </div>
    )
  }
}




const mapStateToProps = state => {

  const startDate=state.getIn(['microdata','filters','datasets','realMinDate'])
  const endDate=state.getIn(['microdata','filters','datasets','realMaxDate'])
  const keyword=state.getIn(['microdata','filters','datasets','text'])
  const selectedOrganizations=state.getIn(['microdata','filters','datasets','organization'])
  const organizations=state.getIn(['data','items','organization'])
  const datasets=state.getIn(['microdata','data','datasets'])


  return {
    startDate,
    endDate,
    keyword,
    organizations,
    selectedOrganizations,
    datasets
  }
}

const mapActionCreators = {
  onLoadDatasets:loadDatasets,
  onChangeFilter:changeFilter
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(TableComponent));
