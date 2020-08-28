import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as C from "../../../modules/graphic/water/rainSeason/RainSeasonConstants"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import RainSeasonTable from "./RainSeasonTable"
import RainSeasonTableFilter from "./RainSeasonTableFilter"

const hasDataLength = ({data} = {}) => data && data.length
const hasDataFunc = ({rainSeasonTableDTO} = {}) => hasDataLength(rainSeasonTableDTO)
const childPropsBuilder = (props) => props
const RainSeasonGraphicWithFallback = GraphicWithFallback('water', 'isFilteringRainSeason', 'isFilteredRainSeason',
  childPropsBuilder, hasDataFunc)

class RainSeasonGraphic extends Component {
  static propTypes = {
    filter: PropTypes.object,
    getRainSeason: PropTypes.func.isRequired,
  }

  render() {
    const {filter, getRainSeason} = this.props
    const {rainSeasonTableDTO} = getRainSeason()

    return (
      <Graphic
        id="anchor.indicator.water.rainseason"
        titleId="indicators.table.rainseason.title"
        helpId="indicators.table.rainseason.help"
        className="rain-season">

        <div className="indicator chart properties">
          <div className="indicator chart filter five-filters">
            {filter && C.COLUMNS.filter(name => !!C.FILTER_MESSAGE_KEY[name]).map(name =>
              <RainSeasonTableFilter key={name} columnName={name} filter={filter} config={rainSeasonTableDTO.config} min={0}/>)}
            {filter &&
            <RainSeasonTableFilter key={C.YEAR} columnName={C.YEAR} filter={filter} config={rainSeasonTableDTO.config} max={1} min={1}/>}
          </div>
        </div>

        <RainSeasonGraphicWithFallback {...this.props} rainSeasonTableDTO={rainSeasonTableDTO}>
          {childProps => <RainSeasonTable {...childProps}/>}
        </RainSeasonGraphicWithFallback>


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
