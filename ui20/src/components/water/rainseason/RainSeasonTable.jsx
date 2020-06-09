import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {Icon, Pagination, Table} from "semantic-ui-react"
import RainSeasonConfigDTO from "../../../modules/graphic/water/rainSeason/RainSeasonConfigDTO"
import * as C from "../../../modules/graphic/water/rainSeason/RainSeasonConstants"
import {RainSeasonPredictionDTO} from "../../../modules/graphic/water/rainSeason/RainSeasonPredictionDTO"
import {toSignedNumberLocaleString} from "../../../modules/utils/DataUtilis"
import * as waterActions from "../../../redux/actions/waterActions"
import "../../common/indicator-table.scss"
import {cssClasses} from "../../ComponentUtil"
import RainSeasonTableFilter from "./RainSeasonTableFilter"


class RainSeasonTable extends Component {
  static propTypes = {
    data: PropTypes.arrayOf(PropTypes.instanceOf(RainSeasonPredictionDTO)).isRequired,
    config: PropTypes.instanceOf(RainSeasonConfigDTO).isRequired,
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
  }

  static getDerivedStateFromProps(props, state) {
    const {data} = props
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
    const {data} = this.props
    const {activePage} = this.state
    const begin = (activePage - 1) * C.PAGE_SIZE
    const end = Math.min(begin + C.PAGE_SIZE, data.length)
    return data.slice(begin, end)
  }

  render() {
    const {intl, sortedBy, sortedAsc, handleSort, filter, config} = this.props
    const data = this._getData()
    const directionLong = sortedAsc ? 'ascending' : 'descending'
    const headerCell = headerCellBuilder(sortedBy, sortedAsc, directionLong, handleSort, filter, config)

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
                  {data.map((dto: RainSeasonPredictionDTO) => (
                    <Table.Row key={dto.id}>
                      <Table.Cell>{dto.zone}</Table.Cell>
                      <Table.Cell>{dto.region}</Table.Cell>
                      <Table.Cell>{dto.department}</Table.Cell>
                      <Table.Cell>{dto.post}</Table.Cell>
                      <Table.Cell>{dto.planned}</Table.Cell>
                      <Table.Cell>{dto.actual}</Table.Cell>
                      <Table.Cell>{toSignedNumberLocaleString(intl, dto.difference)}</Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>
          </div>
          <div className="pagination wrapper">
            <Pagination
              activePage={this.state.activePage}
              totalPages={this.state.totalPages}
              size='mini'
              onPageChange={(e, { activePage })=> this.onPageChange(activePage)}
            />
          </div>
        </div>
      </div>
    )
  }
}

const headerCellBuilder = (sortedBy, sortedAsc, directionLong, handleSort, filter, config) => (name) => {
  const canSort = C.SORTABLE_COLUMNS.has(name)
  const isSorted = sortedBy === name
  const applySort = () => handleSort(name, isSorted ? !sortedAsc : true)
  const canFilter = !!C.FILTER_MESSAGE_KEY[name]
  const headerClasses = []
  if (canFilter) headerClasses.push("th-filter")
  if (isSorted) headerClasses.push(directionLong)

  return (
    <Table.HeaderCell
      key={name}
      className={cssClasses(headerClasses)}
      sorted={canSort && isSorted ? directionLong : null}
      onClick={canSort ? applySort : null}>
      {canFilter &&
      (<div className="indicator chart filter">
        <RainSeasonTableFilter columnName={name} filter={filter} config={config} min={0}/>
      </div>)}
      {!canFilter &&
      <span><FormattedMessage id={C.COLUMN_MESSAGE_KEY[name]} defaultMessage={name}/></span>}
      {canSort && !isSorted ? <Icon name="sort" /> : null}
      {canSort && isSorted ?
        (<div className="up-down-combo">
          <Icon name="sort up" className={sortedAsc ? "sorted" : null}/>
          <Icon name="sort down" className={!sortedAsc ? "sorted" : null} />
        </div>) : null}
    </Table.HeaderCell>)
}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  handleSort: waterActions.sortRainSeason
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainSeasonTable))
