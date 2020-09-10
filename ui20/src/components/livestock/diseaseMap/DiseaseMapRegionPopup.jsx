import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {Grid, GridColumn, GridRow} from "semantic-ui-react"
import {MONTHS} from "../../../modules/entities/Constants"
import Region from "../../../modules/entities/Region"
import DiseaseQuantityMapDTO from "../../../modules/graphic/livestock/diseaseMap/DiseaseQuantityMapDTO"
import {cssClasses} from "../../ComponentUtil"

export default class DiseaseMapRegionPopup extends Component {
  static propTypes = {
    name: PropTypes.string.isRequired,
    diseaseMapDTO: PropTypes.instanceOf(DiseaseQuantityMapDTO).isRequired,
  }

  render() {
    const {name, intl} = this.props
    const diseaseMapDTO: DiseaseQuantityMapDTO = this.props.diseaseMapDTO
    const region: Region = diseaseMapDTO.findRegion(name)
    if (!region) {
      console.error(`Region '${name}' not found. Cannot render its tooltip.`)
      return <div className="disease-region-popup">{intl.formatMessage({ id: "all.no-data"})}</div>
    }
    const now = new Date()
    const futureMonth = diseaseMapDTO.year === now.getFullYear() ?  now.getMonth() + 1 : null
    const casesByMonth: Map = diseaseMapDTO.diseaseQuantityData.quantityByRegionIdByMonth.get(region.id)
    const monthCellRenderer = (m) => renderMonthCell(intl, m, futureMonth, diseaseMapDTO.month, casesByMonth)
    const currentMonthCases = casesByMonth.get(diseaseMapDTO.month) || 0

    return (
      <div className="disease-region-popup">
        <div className="disease-popup-title">
          <span className="region-cases">{region.name} / {currentMonthCases} </span>
          <span className="cases-text">{intl.formatMessage({ id: "indicators.map.disease.cases"})}</span>
        </div>
        <Grid>
          <GridRow>
            {MONTHS.slice(0, 6).map(monthCellRenderer)}
          </GridRow>
          <GridRow>
            {MONTHS.slice(6, 12).map(monthCellRenderer)}
          </GridRow>
        </Grid>
      </div>
    )
  }

}

const renderMonthCell = (intl, month: number, futureMonth: number, activeMonth: number, casesByMonth: Map<number, number>) => {
  const isFuture = futureMonth && month >= futureMonth
  const isActive = month === activeMonth

  return (
    <GridColumn className={cssClasses("disease-popup-month-cell", isFuture ? "future" : null, isActive ? "active" : null)}>
      <div className="month-title">{intl.formatMessage({ id: `all.month.short.${month}`})}</div>
      <div>{renderMonthCases(intl, month, futureMonth, casesByMonth)}</div>
    </GridColumn>
  )
}


const renderMonthCases = (intl, month: number, futureMonth: number, casesByMonth: Map<number, number>) => {
  const cases = casesByMonth.get(month)
  if (!cases) {
    const isFuture = futureMonth && month >= futureMonth
    return (
      <span className={cssClasses("cases no-cases", isFuture ? "future" : null)}>
        {isFuture ? "#" : intl.formatMessage({ id: "all.no-data.short"})}
      </span>)
  }
  return <span className="cases">{cases}</span>
}

