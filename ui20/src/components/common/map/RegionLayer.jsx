import React, {Component} from "react"
import * as PropTypes from "prop-types"
import {GeoJSON} from "react-leaflet"

const regions = require('../../../json/regions.json')

export default class RegionLayer extends Component {
  static propTypes = {
    attribution: PropTypes.string,
    regionFeatureStyle: PropTypes.oneOfType([PropTypes.object, PropTypes.func]),
    onEachFeature: PropTypes.func,
  }

  render() {
    const {attribution, regionFeatureStyle, onEachFeature} = this.props
    return <GeoJSON data={regions} attribution={attribution} style={regionFeatureStyle} onEachFeature={onEachFeature}/>
  }

}
