import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/graphic/water/rainSeason/RainSeasonConstants"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/graphic/Graphic"
import RainSeasonTable from "./RainSeasonTable"
import RainSeasonTableFilter from "./RainSeasonTableFilter"


class RainSeasonGraphic extends Component {
  static propTypes = {
    filter: PropTypes.object.isRequired,
    getRainSeason: PropTypes.func.isRequired,
  }

  render() {
    const {filter, getRainSeason} = this.props;
    const {rainSeasonTableDTO} = getRainSeason()

    return (
      <Graphic id="anchor.indicator.water.rainseason" titleId="indicators.table.rainseason.title">
        <div className="indicator chart properties">
          <div className="indicator chart filter five-filters">
            {C.COLUMNS.filter(name => !!C.FILTER_MESSAGE_KEY[name]).map(name =>
              <RainSeasonTableFilter columnName={name} filter={filter} config={rainSeasonTableDTO.config} min={0}/>)}
            <RainSeasonTableFilter columnName={C.YEAR} filter={filter} config={rainSeasonTableDTO.config} max={1} min={1} />
          </div>
        </div>
        <RainSeasonTable rainSeasonTableDTO={rainSeasonTableDTO} {...this.props}/>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    isFilteredRainSeason: state.getIn(['water', 'isFilteredRainSeason']),
    filter: state.getIn(['water', 'data', 'rainSeasonChart', 'filter']),
    sortedBy: state.getIn(['water', 'data', 'rainSeasonChart', 'sortedBy']),
    sortedAsc: state.getIn(['water', 'data', 'rainSeasonChart', 'sortedAsc']),
  }
}

const mapActionCreators = {
  getRainSeason: waterActions.getRainSeason,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainSeasonGraphic))
