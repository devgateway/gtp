import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {Segment} from "semantic-ui-react"
import BulletinConfig from "../../modules/entities/bulletins/BulletinConfig"
import BulletinReport from "../../modules/entities/bulletins/BulletinReport"
import Bulletins from "../../modules/entities/bulletins/Bulletins"
import {anyWithIdAndNameToOptions, yearsToOptions} from "../../modules/graphic/common/GraphicDTO"
import {getOrDefault} from "../../modules/utils/DataUtilis"
import * as bulletinActions from "../../redux/actions/bulletinActions"
import "../common/common.scss"
import FilterDropDown from "../common/filter/FilterDropDown"
import PageLoadWrapper from "../common/page/PageLoadWrapper"
import "./bulletin.scss"
import BulletinYear from "./BulletinYear"

class BulletinPage extends Component {
  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
    isLoaded: PropTypes.bool,
    report: PropTypes.object.isRequired,
    setYears: PropTypes.func.isRequired,
    setLocation: PropTypes.func.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    const {isLoaded, setYears, setLocation, intl} = this.props;
    if (!isLoaded) {
      return <div/>
    }

    const report: BulletinReport = this.props.report
    const config: BulletinConfig = report.config
    const years = report.filter.years.sort().reverse()
    const bulletinsList = years.map(y => getOrDefault(report.data.gtpMaterials, y, null, () => new Bulletins(y)))

    return (
      <div className="page-container bulletins-container">
        <Segment className="page-header bottom-border">
          <Segment className="title">
            <FormattedMessage id="menu.bulletins.title" />
          </Segment>
          <div className="indicator chart filter two-filters">
            <div className="filter item fixed">
              <FilterDropDown
                options={yearsToOptions(config.years)}
                onChange={setYears}
                single={false}
                selected={years}
                text={intl.formatMessage({ id: "indicators.filters.year"})} />
            </div>
            <div className="filter item fixed">
              <FilterDropDown
                options={anyWithIdAndNameToOptions(config.locations)}
                onChange={(ids) => setLocation(ids[0])}
                single={true}
                withSearch={true}
                selected={[report.filter.locationId]}
                text={intl.formatMessage({ id: "indicators.filters.localite"})} />
            </div>
          </div>
        </Segment>
        <Segment className="bulletins-content">
          {bulletinsList.map((bs: Bulletins) => <BulletinYear key={bs.year} bulletins={bs} />)}
        </Segment>
      </div>)
  }
}


const mapStateToProps = state => {
  return {
    isLoaded: state.getIn(['bulletin', 'isLoaded']),
    report: state.getIn(['bulletin', 'data']),
  }
}

const mapActionCreators = {
  onLoadAll: bulletinActions.loadAllBulletins,
  setYears: bulletinActions.setYears,
  setLocation: bulletinActions.setLocation,
}

const BulletinPageLoadWrapper = PageLoadWrapper({ statePath: 'bulletin'})
const BP = (props) => <BulletinPageLoadWrapper {...props} >{(childProps) => <BulletinPage {...childProps}/>}</BulletinPageLoadWrapper>

export default injectIntl(connect(mapStateToProps, mapActionCreators)(BP))
