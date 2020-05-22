import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/Graphic"
import RiverLevel from "./RiverLevel"


class RiverLevelGraphic extends Component {
  static propTypes = {
    getRiverLevel: PropTypes.func.isRequired,
  }

  render() {
    const {getRiverLevel} = this.props

    return (<Graphic
      id="anchor.indicator.water.riverlevel" titleId="indicators.chart.riverlevel.title"
      sourceId="indicators.chart.riverlevel.source" className="rainfall">
      <RiverLevel {...getRiverLevel()} />
    </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  getRiverLevel: waterActions.getRiverLevel
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevelGraphic))
