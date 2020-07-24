import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {Icon, Pagination, Popup, Table} from "semantic-ui-react"
import RainSeasonData from "../../../modules/entities/rainSeason/RainSeasonData"
import RainSeasonConfigDTO from "../../../modules/graphic/water/rainSeason/RainSeasonConfigDTO"
import * as C from "../../../modules/graphic/water/rainSeason/RainSeasonConstants"
import {RainSeasonPredictionDTO} from "../../../modules/graphic/water/rainSeason/RainSeasonPredictionDTO"
import RainSeasonTableDTO from "../../../modules/graphic/water/rainSeason/RainSeasonTableDTO"
import {toSignedNumberLocaleString} from "../../../modules/utils/DataUtilis"
import * as waterActions from "../../../redux/actions/waterActions"
import "../../common/graphic/indicator-table.scss"
import GraphicSource from "../../common/graphic/GraphicSource"
import {cssClasses} from "../../ComponentUtil"
import RainSeasonTableFilter from "./RainSeasonTableFilter"


class RainSeasonTable extends Component {
  static propTypes = {
    rawData: PropTypes.instanceOf(RainSeasonData).isRequired,
    rainSeasonTableDTO: PropTypes.instanceOf(RainSeasonTableDTO).isRequired,
    filter: PropTypes.object.isRequired,
    handleSort: PropTypes.func.isRequired,
    sortedBy: PropTypes.string,
    sortedAsc: PropTypes.bool,
  }

  constructor(props) {
    super(props)
    this.state = {
      isLocalStateChange: false,
    }
    this.onPageChange = this.onPageChange.bind(this)
  }

  static getDerivedStateFromProps(props, state) {
    const {data} = props.rainSeasonTableDTO
    const {isLocalStateChange} = state
    if (isLocalStateChange) {
      return {
        isLocalStateChange: false
      }
    }
    return {
      activePage: 1,
      totalPages: Math.floor(data.length / C.PAGE_SIZE) + Math.min(1, data.length % C.PAGE_SIZE)
    }
  }

  onPageChange(activePage: number) {
    this.setState({
      activePage,
      isLocalStateChange: true
    })
  }

  _getData() {
    const {data} = this.props.rainSeasonTableDTO
    const {activePage} = this.state
    const begin = (activePage - 1) * C.PAGE_SIZE
    const end = Math.min(begin + C.PAGE_SIZE, data.length)
    return data.slice(begin, end)
  }

  render() {
    const {intl, sortedBy, sortedAsc, handleSort, filter, rainSeasonTableDTO, rawData} = this.props
    const data = this._getData()
    const directionLong = sortedAsc ? 'ascending' : 'descending'
    const headerCell = headerCellBuilder(sortedBy, sortedAsc, directionLong, handleSort, filter,
      rainSeasonTableDTO.config, rawData)
    const cell = (tooltip, content) => <Popup trigger={content} content={tooltip} size="mini" />

    return (
      <div className="indicator-table container">
        <div className="png exportable table">
          <div className="table-wrapper">
            <Table sortable celled>
              <Table.Header>
                <Table.Row>
                  {C.COLUMNS.map(headerCell)}
                </Table.Row>
              </Table.Header>
              <Table.Body>
                {data.map((dto: RainSeasonPredictionDTO) => {
                  const difference = toSignedNumberLocaleString(intl, dto.difference)
                  return (
                    <Table.Row key={dto.id}>
                      {cell(dto.zone, <Table.Cell className="column15">{dto.zone}</Table.Cell>)}
                      {cell(dto.region, <Table.Cell className="column15">{dto.region}</Table.Cell>)}
                      {cell(dto.department, <Table.Cell className="column15">{dto.department}</Table.Cell>)}
                      {cell(dto.post, <Table.Cell className="column15">{dto.post}</Table.Cell>)}
                      {cell(dto.planned, <Table.Cell className="column15">{dto.planned}</Table.Cell>)}
                      {cell(dto.actual, <Table.Cell className="column15">{dto.actual}</Table.Cell>)}
                      {cell(difference,
                        <Table.Cell className="column10">
                        <span className="difference">
                          {difference}
                        </span>
                        </Table.Cell>)}
                    </Table.Row>
                  )
                })}
              </Table.Body>
            </Table>
          </div>
          <div className="pagination wrapper">
            {dgPagination(this.props, this.state, this.onPageChange)}
            {GraphicSource("indicators.table.rainseason.source")}
          </div>
        </div>
      </div>
    )
  }
}

const dgPagination = (props, state, onPageChange) => {
  const {activePage, totalPages} = state
  return (
    <div className="dg-pagination">
      <Icon
        name="caret left"
        disabled={activePage === 1 }
        onClick={() => onPageChange(activePage - 1)} />
      <span><FormattedMessage id="indicators.table.page.text" values={{activePage, totalPages}} /></span>
      <Icon
        name="caret right"
        disabled={activePage === totalPages}
        onClick={() => onPageChange(activePage + 1)} />
    </div>)

}

// eslint-disable-next-line no-unused-vars
const semanticUIPagination = (props, state, onPageChange) => {
  const {activePage, totalPages} = state
  return (<Pagination
    activePage={activePage}
    totalPages={totalPages}
    size='mini'
    onPageChange={(e, { activePage })=> onPageChange(activePage)}
  />)
}

const headerCellBuilder = (sortedBy, sortedAsc, directionLong, handleSort, filter,
  config: RainSeasonConfigDTO, rawData: RainSeasonData) => (name) => {
  const canSort = C.SORTABLE_COLUMNS.has(name)
  const isSorted = sortedBy === name
  const applySort = () => handleSort(name, isSorted ? !sortedAsc : true)
  // const canFilter = !!C.FILTER_MESSAGE_KEY[name]
  const canFilter = false
  const headerClasses = [name === C.DIFFERENCE ? "column10" : "column15"]
  const trnValues = {}
  if (canFilter) headerClasses.push("th-filter")
  if (isSorted) headerClasses.push(directionLong)
  if (name === C.PLANNED) {
    trnValues.referenceYearStart = rawData.referenceYearStart
    trnValues.referenceYearEnd = rawData.referenceYearEnd
  } else if (name === C.ACTUAL) {
    trnValues.year = filter.yearIds[0]
  }

  return (
    <Table.HeaderCell
      key={name}
      className={cssClasses(...headerClasses)}
      sorted={canSort && isSorted ? directionLong : null}
      onClick={canSort ? applySort : null}>
      <div className="header-cell-container">
        {canFilter &&
        (<div className="indicator chart filter">
          <RainSeasonTableFilter columnName={name} filter={filter} config={config} min={0}/>
        </div>)}
        {!canFilter &&
        (<div className="header-title">
          <FormattedMessage id={C.COLUMN_MESSAGE_KEY[name]} defaultMessage={name} values={trnValues}/>
        </div>)}
        {canSort && !isSorted ? <Icon name="sort"/> : null}
        {canSort && isSorted ?
          (<div className="up-down-combo">
            <Icon name="sort up" className={sortedAsc ? "sorted" : null}/>
            <Icon name="sort down" className={!sortedAsc ? "sorted" : null}/>
          </div>) : null}
      </div>
    </Table.HeaderCell>)
}

const mapStateToProps = state => {
  return {
    rawData: state.getIn(['water', 'data', 'rainSeasonChart', 'data']),
  }
}

const mapActionCreators = {
  handleSort: waterActions.sortRainSeason
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainSeasonTable))
