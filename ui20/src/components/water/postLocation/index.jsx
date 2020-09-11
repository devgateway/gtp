import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import * as waterActions from "../../../redux/actions/waterActions"
import Graphic from "../../common/graphic/Graphic"
import GraphicWithFallback from "../../common/graphic/GraphicWithFallback"
import PluviometricPostMap from "./PluviometricPostMap"

const hasDataFunc = ({postMapDTO} = {}) => !!postMapDTO
const childPropsBuilder = (props) => props.getPostLocation()
const PluviometricPostLocationGraphicWithFallback = GraphicWithFallback('water', 'isLoading', 'isLoaded',
  childPropsBuilder, hasDataFunc)

class PluviometricPostLocationGraphic extends Component {
  static propTypes = {
    getPostLocation: PropTypes.func.isRequired,
  }

  render() {
    const {waterConfig, getPostLocation} = this.props
    return (
      <Graphic
        id="anchor.indicator.water.post.map" titleId="indicators.map.post.title"
        helpId="indicators.map.post.help"
        className="map-graphic">
        <PluviometricPostLocationGraphicWithFallback waterConfig={waterConfig} getPostLocation={getPostLocation} >
          {childProps => <PluviometricPostMap {...childProps} />}
        </PluviometricPostLocationGraphicWithFallback>
      </Graphic>)
  }
}


const mapStateToProps = state => {
  return {
    waterConfig: state.getIn(['water', 'data', 'waterConfig']),
  }
}

const mapActionCreators = {
  getPostLocation: waterActions.getPostLocation
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(PluviometricPostLocationGraphic))
