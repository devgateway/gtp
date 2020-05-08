import messages from "../../../translations/messages"
import {asBarChartValue} from "../../utils/DataUtilis"
import MonthDecadal from "../../utils/MonthDecadal"
import * as C from "../../entities/Constants"
import RainLevelReport from "../../entities/rainfall/RainLevelReport"
import RainReferenceLevelData from "../../entities/rainfall/RainReferenceLevelData"

export default class RainfallChartBuilder {
  rainLevelChart
  report: RainLevelReport
  intl
  barData: Array
  keys: Array<number>
  keyReferenceLevels: Map<number, RainReferenceLevelData>
  byDecadal: boolean
  indexBy: string
  monthDecadal: MonthDecadal

  constructor(rainLevelChart, intl) {
    this.rainLevelChart = rainLevelChart
    this.report = rainLevelChart.report
    this.intl = intl
    this.barData = []
    this.byDecadal = rainLevelChart.setting.byDecadal
    this.indexBy = this.byDecadal ? 'monthDecadal' : 'month'
    this._init()
  }

  _init() {
    this.keys = this.rainLevelChart.filter.years.sort().reverse()
    this.keyReferenceLevels = new Map()
    let idx = 0
    this.report.data.referenceLevels.sort((r1, r2) => r1.referenceYearEnd > r2.referenceYearEnd)
      .forEach((refLevels: RainReferenceLevelData) => {
        if (idx > this.keys.length) {
          console.error('More reference level periods than selected years')
        } else {
          this.keyReferenceLevels.set(this.keys[idx], refLevels)
        }
      })
    this.monthDecadal = new MonthDecadal(this.keys.length === 1 ? this.keys[0] : null, C.SEASON_MONTHS)
  }

  build() {
    this.monthDecadal.getMonths().forEach(month => {
      const monthLabel = `${this.intl.formatMessage(messages[`month_${month}`])}`
      if (this.byDecadal) {
        this.monthDecadal.getDecadals(month).forEach(decadal => {
          this._buildRecord(month, monthLabel, decadal)
        })
      } else {
        this._buildRecord(month, monthLabel)
      }
    })
  }

  _buildRecord(month: number, monthLabel: string, decadal: number) {
    const record = {}
    record[this.indexBy] = this.byDecadal ? `${month},${decadal}` : `${month}`
    record.indexLabel = this.byDecadal ? `${decadal}` : monthLabel
    record.lineValues = new Map()
    this.keys.forEach(year => {
      const yearLabel = `${year}`
      record[yearLabel] = asBarChartValue(this._getValue(year, month, decadal))
      const refLevels = this.keyReferenceLevels.get(year)
      if (refLevels) {
        record.lineValues.set(yearLabel, this._getReferenceValue(refLevels, month, decadal))
      }
    })
    this.barData.push(record)
  }

  _getValue(year: number, month: number, decadal: number) {
    if (this.byDecadal) {
      return this.report.data.getDecadalLevel(year, month, decadal)
    }
    return this.report.data.getMonthLevel(year, month)
  }

  _getReferenceValue(referenceLevels: RainReferenceLevelData, month: number, decadal: number) {
    if (this.byDecadal) {
      return referenceLevels.getDecadalLevel(month, decadal)
    }
    return referenceLevels.getMonthLevel(month)
  }

}
