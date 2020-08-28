import React from "react"
import {MapControl, withLeaflet} from "react-leaflet"
import L from "leaflet"
import "./rainfalMap.scss"

class RainfallMapLegend extends MapControl {
  createLeafletElement(props) {}

  componentDidMount() {
    const colorsMap: Map = this.props.colorsMap
    const grades = [0, ...Array.from(colorsMap.keys()).sort((a, b) => a - b)]
    const legend = L.control({ position: "topright" });

    legend.onAdd = () => {
      const div = L.DomUtil.create("div", "rainfall-map-legend info legend")
      let labels = []

      for (let i = 0; i < grades.length; i++) {
        const from = grades[i];
        const to = grades[i + 1];

        labels.push(
          '<i style="background:' +
          colorsMap.get(from) +
          '"></i> ' +
          from +
          (to ? " - " + to : "+")
        );
      }

      div.innerHTML = labels.join("<br>")
      return div;
    }

    const { map } = this.props.leaflet
    legend.addTo(map)

  }
}

export default withLeaflet(RainfallMapLegend);
