import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {GeoJSON} from "react-leaflet"

const countries = require('../../../json/countries-ne.json')

class CountriesLayer extends Component {
  static propTypes = {
    countryFeatureStyle: PropTypes.oneOfType([PropTypes.object, PropTypes.func]),
    onEachFeature: PropTypes.func,
  }

  render() {
    const {countryFeatureStyle, onEachFeature, intl} = this.props
    const attribution = intl.formatMessage({ id: "indicators.map.attribution.countries"})
    return (
      <GeoJSON
        data={countries}
        attribution={attribution}
        style={countryFeatureStyle}
        onEachFeature={onEachFeature} />)
  }

}

export default injectIntl(CountriesLayer)
