import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/Graphic"
import RainSeasonTable from "./RainSeasonTable"


class RainSeasonGraphic extends Component {
  static propTypes = {
    getRainSeason: PropTypes.func.isRequired,
  }

  render() {
    const {getRainSeason} = this.props;

    return (<Graphic
      id="anchor.indicator.water.rainseason" titleId="indicators.table.rainseason.title"
      sourceId="indicators.table.rainseason.source" className="rainseason">
      <RainSeasonTable {...getRainSeason()} />
    </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  getRainSeason: waterActions.getRainSeason,
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainSeasonGraphic))
