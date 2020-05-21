import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import Graphic from "../../common/Graphic"
import RiverLevel from "./RiverLevel"


class RiverLevelGraphic extends Component {
  static propTypes = {
  }

  render() {
    return (<Graphic
      id="anchor.indicator.water.riverlevel" titleId="indicators.chart.riverlevel.title"
      sourceId="indicators.chart.riverlevel.source" className="rainfall">
      <RiverLevel />
    </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RiverLevelGraphic))
