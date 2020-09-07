import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as livestockActions from "../../redux/actions/livestock"
import "../common/graphic/indicators.scss"
import "../common/graphic/indicator-base.scss"
import GraphicPage, {GraphicDef} from "../common/graphic/GraphicPage"
import DiseaseMapGraphic from "./diseaseMap"

const livestockGraphicsDef = [
  new GraphicDef('indicators.map.disease.title', 'masked-icon icon-barchart', DiseaseMapGraphic),
]


class Livestock extends Component {

  static propTypes = {
    onLoadAll: PropTypes.func.isRequired,
  }

  componentDidMount() {
    this.props.onLoadAll();
  }

  render() {
    return <GraphicPage graphicsDefs={livestockGraphicsDef} />
  }

}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
  onLoadAll: livestockActions.loadAllLivestockData
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Livestock))
