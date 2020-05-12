import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import {RainSeasonPredictionDTO} from "../../../modules/graphic/water/rainSeason/RainSeasonPredictionDTO"


class RainSeasonTable extends Component {
  static propTypes = {
    data: PropTypes.arrayOf(PropTypes.instanceOf(RainSeasonPredictionDTO)).isRequired,
  }

  render() {
    const {data} = this.props
    return (
      <div className="png exportable table filters">
        {`Rows count = ${data.length}`}
      </div>
    )
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(RainSeasonTable))
