import React, {Component} from "react"
import PropTypes from "prop-types"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as diseaseQuantityActions from "../../../redux/actions/livestock/diseaseQuantityActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import DiseaseMap from "./DiseaseMap"

const hasDataFunc = ({diseaseMapDTO}) => !!diseaseMapDTO
const childPropsBuilder = (props) => props.getDiseaseQuantities()
const DiseaseMapGraphicWithFallback = GraphicWithFallback('livestock', 'isDiseaseQuantityLoading',
  'isDiseaseQuantityLoaded', childPropsBuilder, hasDataFunc)


class DiseaseMapGraphic extends Component {
  static propTypes = {
    isDiseaseQuantityLoading: PropTypes.bool,
    getDiseaseQuantities: PropTypes.func.isRequired,
  }

  render() {
    const {getDiseaseQuantities} = this.props

    return (
      <Graphic
        id="anchor.indicator.livestock.disease.map" titleId="indicators.map.disease.title"
        helpId="indicators.map.disease.help"
        sourceId="indicators.map.disease.source"
        className="map-graphic">
        <DiseaseMapGraphicWithFallback getDiseaseQuantities={getDiseaseQuantities} >
          {childProps => <DiseaseMap {...childProps} />}
        </DiseaseMapGraphicWithFallback>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    isDiseaseQuantityLoading: state.getIn(['livestock', 'isDiseaseQuantityLoading']),
  }
}

const mapActionCreators = {
  getDiseaseQuantities: diseaseQuantityActions.getDiseaseQuantities
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(DiseaseMapGraphic))
