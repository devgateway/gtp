import React from "react"
import {Popup} from "semantic-ui-react"

export const RainfallMapLegend = ({colorsMap, unit, legendLabelFunc}) => {
  const grades = Array.from(colorsMap.keys()).sort((a, b) => a - b)

  return (
    <div className="rainfall-map-legend">
      <div className="legend">
        {grades.map((g, idx) => {
          const style = {backgroundColor: colorsMap.get(g)}
          return (
            <span key={g} className="legend-item">
              <Popup
                position="top center"
                className="rain-legend-item"
                trigger={
                  <i style={style} />
                }>
                <Popup.Content>
                  <div>
                    <span style={style}>&nbsp;</span>
                    <span>&nbsp;{g}{unit}</span>
                  </div>
                </Popup.Content>
              </Popup>
              {legendLabelFunc(g, unit, idx, grades.length)}
            </span>
          )
        })}
      </div>
    </div>
  )
}
