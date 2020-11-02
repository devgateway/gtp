import React from "react"

export const RainfallMapLegend = ({colorsMap, unit, legendLabelFunc}) => {
  const grades = Array.from(colorsMap.keys()).sort((a, b) => a - b)

  return (
    <div className="rainfall-map-legend">
      <div className="legend">
        {grades.map((g, idx) => {
          return (
            <span key={g} className="legend-item">
              <i style={{backgroundColor: colorsMap.get(g)}} />
              {legendLabelFunc(g, unit, idx, grades.length)}
            </span>
          )
        })}
      </div>
    </div>
  )
}
