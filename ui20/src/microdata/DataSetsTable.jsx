import _ from 'lodash'
import React, { Component } from 'react'
import { Table,Pagination } from 'semantic-ui-react'

import {CustomFilterDropDown,TextInput,DateInput} from '../indicators/Components'
import {items2options} from '../indicators/DataUtil'
import {injectIntl,FormattedDate, FormattedMessage, FormattedHTMLMessage} from 'react-intl';
import {changeFilter,loadDatasets,changePage } from '../modules/Microdata'
import {connect} from 'react-redux';
import messages from '../translations/messages'


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
      return
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
      this.props.onLoadDatasets(this.props.intl.locale)
    }

    componentDidUpdate(prevProps) {
    // Uso tipico (no olvides de comparar los props):


    if (this.props.datasets !== prevProps.datasets && this.props.datasets) {


        const newState=Object.assign({},this.state,
          {
          data:this.props.datasets&&this.props.datasets.get('content')?this.props.datasets.get('content').toJS():[],
          totalPages:this.props.datasets.getIn(['totalPages']),
          activePage:this.props.datasets.getIn(['pageable','page'])+1,
        })

          this.setState(newState)
    }
  }

  render() {
    //TODO: remove state use props

    const {onChangeFilter, keyword, startDate, endDate, organizations=[], selectedOrganizations=[], intl, datasets={}, onChangePage ,selectedYear=[],  years} = this.props
    const locale=intl.locale
    let column = 'type'
    let direction = 'asc'

    return  (
      <div>
            <div className="table filters">
                  <div className="title">  <FormattedMessage id="microdata.filters.title" defaultMessage="Filters"/></div>
                  <div className="item"><TextInput text={intl.formatMessage(messages.microdata_filters_keyword)}
                  onChange={val=>onChangeFilter(['filters','datasets','text'],val,locale,'DATASETS')} name="keyword" value={keyword}/></div>
                  <div className="item">
                    <CustomFilterDropDown  text={intl.formatMessage(messages.year)}  options={items2options(years,intl)} selected={selectedYear} onChange={value => {onChangeFilter(['filters','datasets','year'],value,locale,'DATASETS')}}/> </div>
                  <div className="item">
                  <CustomFilterDropDown  text={intl.formatMessage(messages.microdata_filters_organization)}  options={items2options(organizations,intl)} selected={selectedOrganizations} onChange={value => {onChangeFilter(['filters','datasets','organization'],value,locale,'DATASETS')}}/></div>

            </div>
          <Table sortable celled fixed>

                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell sorted={column === 'type' ? direction : null}  onClick={this.handleSort('type')}>
                      <FormattedMessage id="microdata.table.type" defaultMessage="Type"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'title' ? direction : null} onClick={this.handleSort('title')}>
                        <FormattedMessage id="microdata.table.title" defaultMessage="Title"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'organization' ? direction : null} onClick={this.handleSort('organization')}>
                      <FormattedMessage id="microdata.table.organization" defaultMessage="Organization"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'source' ? direction : null} onClick={this.handleSort('source')}>
                      <FormattedMessage id="microdata.table.source" defaultMessage="Source"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'creator' ? direction : null} onClick={this.handleSort('creator')}>
                      <FormattedMessage id="microdata.table.creator" defaultMessage="Creator"/>
                    </Table.HeaderCell>
                    <Table.HeaderCell sorted={column === 'createdDate' ? direction : null} onClick={this.handleSort('createdDate')}>
                      <FormattedMessage id="microdata.table.created" defaultMessage="Created"/>
                    </Table.HeaderCell>

                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {this.state.data &&_.map(this.state.data, ({id, organization, title, source,creator, createdDate, fileId, type}) => (
                    <Table.Row key={id}>
                     <Table.Cell>{type}</Table.Cell>
                     <Table.Cell><a href={`/files/download/${fileId}`}>{title}</a></Table.Cell>
                     <Table.Cell>{organization}</Table.Cell>
                     <Table.Cell>{source}</Table.Cell>

                      <Table.Cell>{creator}</Table.Cell>
                      <Table.Cell>

                      <FormattedDate value={createdDate} />

                      </Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>
              <div className="pagination wrapper">
              <Pagination
                  activePage={this.state.activePage}
                  boundaryRange={this.state.boundaryRange}
                  onPageChange={(e, { activePage })=>{
                    onChangePage(['filters','datasets','pageNumber'],activePage -1,locale,'DATASETS')
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
  const selectedYear=state.getIn(['microdata','filters','datasets','year'])
  const organizations=state.getIn(['data','items','organization'])
  const datasets=state.getIn(['microdata','data','datasets'])
  const years=state.getIn(['microdata','data','years'])


  return {
    startDate,
    endDate,
    keyword,
    organizations,
    selectedOrganizations,
    datasets,
    years,
    selectedYear
  }
}

const mapActionCreators = {
  onLoadDatasets:loadDatasets,
  onChangeFilter:changeFilter,
  onChangePage:changePage
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(TableComponent));
