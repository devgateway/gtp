import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Segment, Table} from "semantic-ui-react"
import {Bulletin} from "../../modules/entities/bulletins/Bulletin"
import Bulletins from "../../modules/entities/bulletins/Bulletins"
import {BULLETIN_MONTHS, DECADALS} from "../../modules/entities/Constants"

export default class BulletinYear extends Component {
  static propTypes = {
    bulletins: PropTypes.instanceOf(Bulletins).isRequired,
  }

  render() {
    const bulletins:Bulletins = this.props.bulletins

    return (
      <div>
        <Segment>
          <span><FormattedMessage id="bulletins.bulletin.title" values={{year: bulletins.year}} /></span>
          <span>
            <span><FormattedMessage id="bulletins.bulletin.annualReport" /></span>
            <span>{getBulletinURL(bulletins.annualReport)}</span>
          </span>
        </Segment>
        <Table stackable={true} columns={1 + BULLETIN_MONTHS.length}>

          <Table.Header>
            <Table.Row>
              <Table.HeaderCell id="decadals" className="decadal-header" />
              {BULLETIN_MONTHS.map(m =>
                <Table.HeaderCell>
                  <FormattedMessage id={`all.month.${m}`} />
                </Table.HeaderCell>)}
            </Table.Row>
          </Table.Header>

          <Table.Body>
            {DECADALS.map(d => {
              const bsByMonth = bulletins.bulletinsByDecadalByMonth.get(d)
              const bs = BULLETIN_MONTHS.map(m => bsByMonth.get(m))

              return (
                <Table.Row>
                  <Table.HeaderCell>
                    <FormattedMessage id={`bulletins.bulletin.table.decadal.${d}`} />
                  </Table.HeaderCell>
                  {bs.map(b => <Table.Cell>{getBulletinURL(b)}</Table.Cell>)}
                </Table.Row>)
            })}

          </Table.Body>

        </Table>

      </div>
    )
  }
}

const getBulletinURL = (b: Bulletin) => b ? "url" : "-"
