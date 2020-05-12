import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {Table} from "semantic-ui-react"
import {RainSeasonPredictionDTO} from "../../../modules/graphic/water/rainSeason/RainSeasonPredictionDTO"
import * as C from "../../../modules/graphic/water/rainSeason/RainSeasonConstants"
import {toSignedNumberLocaleString} from "../../../modules/utils/DataUtilis"
import * as waterActions from "../../../redux/actions/waterActions"
import "../../ipar/microdata/microdata.scss"


class RainSeasonTable extends Component {
  static propTypes = {
    data: PropTypes.arrayOf(PropTypes.instanceOf(RainSeasonPredictionDTO)).isRequired,
    handleSort: PropTypes.func.isRequired,
    sortedBy: PropTypes.string,
    sortedAsc: PropTypes.bool,
  }

  render() {
    const {data, intl, sortedBy, sortedAsc, handleSort} = this.props
    const directionLong = sortedAsc ? 'ascending' : 'descending'
    const headerCell = headerCellBuilder(sortedBy, sortedAsc, directionLong, handleSort)
    return (
      <div className="microdata container">
        <div className="png exportable table">
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
      </div>
    )
  }
}

const headerCellBuilder = (sortedBy, sortedAsc, directionLong, handleSort) => (name) => {
  const isSorted = sortedBy === name
  const applySort = () => handleSort(name, isSorted ? !sortedAsc : true)
  return (
    <Table.HeaderCell key={name} sorted={isSorted ? directionLong : null} onClick={applySort}>
      <FormattedMessage id={C.COLUMN_MESSAGE_KEY[name]} defaultMessage={name}/>
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
