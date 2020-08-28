import React, {Component} from 'react'
import {GeoJSON} from 'react-leaflet';

// TODO: waiting confirmation
const country = require('../../../json/gadm36_SEN_0.json')
const attribution = 'GADM'

/*
const country = require('../../../json/senegal-osm.json')
// OSM already mentioned by the main map tile
const attribution = ''
*/

/*
const country = require('../../../json/senegal-natural-earth.json')
const attribution = 'Made with Natural Earth'
*/

export default class BorderLayer extends Component {

  static defaultProps = {
    style: defaultStyle,
  }

  render() {
    const style = {...defaultStyle, ...this.props.style}
    return <GeoJSON data={country} attribution={attribution} style={featureStyle(style)}/>
  }
}

const featureStyle = style => feature => style

const defaultStyle = {
  color: '#E03E32',
  weight: 1,
  fillOpacity: 0,
}
