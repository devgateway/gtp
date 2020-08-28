import React, {Component} from "react"
import {GeoJSON, Map, TileLayer} from "react-leaflet"
import BorderLayer from "../../market/marketLocation/BorderLayer"
import "../../market/marketLocation/marketMap.scss"
import {cleanupFeatures, getFeatureStyle, mapLevelToColor} from "./RainfallMapHelper"
import RainfallMapLegend from "./RainfallMapLegend"

const rainfallJson1 = require('../../../json/anomalie_23_aoutPoly.json')
const rainfallJson2 = require('../../../json/anomalie_23_aout.json')
const regionJson = require('../../../json/regions.json')
const countriesJson = require('../../../json/countries.json')


export class RainfallMap extends Component {
  render() {
    cleanupFeatures(rainfallJson1)
    cleanupFeatures(rainfallJson2)
    const colorsMap: Map = mapLevelToColor(rainfallJson1)

    return (
      <div className="png exportable">
        <div className="map-container">
          <Map className="map rainfall-map" zoom={7} center={[14.4974, -14.4545887]} zoomControl={true}>
             {/*<TileLayer
              url="https://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}"
              />*/}
              {/*<GeoJSON data={countriesJson} onEachFeature={onCountryFeature} style={{
                fillColor: "#FFF",
                color: "#000",
                weight: 1,
                strokeOpacity: 0.1,
                fillOpacity: 1,
              }} />*/}
            <GeoJSON data={rainfallJson1} style={getFeatureStyle(colorsMap)}/>
            <GeoJSON data={rainfallJson2} style={getFeatureStyle(colorsMap)}/>
            <GeoJSON data={regionJson} onEachFeature={onRegionFeature} style={{
              color: "#000",
              weight: 1
            }} />
            {/* <BorderLayer style={{color: "#000"}}/> */}
            <RainfallMapLegend colorsMap={colorsMap} className="rainfall-map-legend"/>
          </Map>
        </div>
      </div>
    )
  }

}

const onRegionFeature = (feature, layer) => {
  layer.bindTooltip(feature.properties.NAME_1,
    {
      permanent: true,
      direction:"center",
      className: "region-label"
    }).openTooltip()
}

const onCountryFeature = (feature, layer) => {
  layer.bindTooltip(feature.properties.name,
    {
      permanent: true,
      direction:"center",
      className: "region-label"
    }).openTooltip()
}
