import messages from "../../../../translations/messages"
import * as C from "../../../entities/Constants"
import RainLevelData from "../../../entities/rainfall/RainLevelData"
import RainReferenceLevelData from "../../../entities/rainfall/RainReferenceLevelData"
import {addAndGet, asBarChartValue} from "../../../utils/DataUtilis"
import MonthDecadal from "../../../utils/MonthDecadal"
import RainfallDTO from "./RainfallDTO"

export default class RainfallChartBuilder {
  rainLevelChart
  data: RainLevelData
  intl
  keys: Array<number>
  keyReferenceLevels: Map<number, RainReferenceLevelData>
  byDecadal: boolean
  rainfallDTO: RainfallDTO
  cumulatedByYear: Map<number, number>

  constructor(rainLevelChart, intl) {
    this.rainLevelChart = rainLevelChart
    this.data = rainLevelChart.data
    this.intl = intl
    this.byDecadal = rainLevelChart.setting.byDecadal
    this.keyReferenceLevels = new Map()
    this.cumulatedByYear = new Map()
    this._init()
  }

  _init() {
    this.keys = this.rainLevelChart.filter.years.sort().reverse()
    this.rainfallDTO = new RainfallDTO(this.keys.map(k => `${k}`), this.byDecadal ? 'monthDecadal' : 'month')
    this.keys.forEach(year => this.cumulatedByYear.set(year, 0))

    this.data.referenceLevels.sort((r1, r2) => r1.referenceYearEnd > r2.referenceYearEnd)
      .forEach((refLevels: RainReferenceLevelData) => {
        const refKeys = this.keys.filter(k => (refLevels.yearStart <= k && refLevels.yearEnd >= k))
        if (!refKeys.length) {
          console.error('No matching year found for provided reference level')
        } else {
          this.keyReferenceLevels.set(refKeys[Math.min(2, refKeys.length) - 1], refLevels)
        }
      })
    this.rainfallDTO.monthDecadal = new MonthDecadal(this.keys.length === 1 ? this.keys[0] : null, C.SEASON_MONTHS)
    this.rainfallDTO.maxValue = this._getMaxValue()
  }

  _getMaxValue() {
    const maxLevels = this.keys.map(year => this.data.getYearTotal(year))
    const maxRainLevel = Math.max(...maxLevels)
    const maxRefLevel = Math.max(...Array.from(this.keyReferenceLevels.values()).map((refData: RainReferenceLevelData) =>
      this.byDecadal ? refData.maxDecadalLevel : refData.maxMonthLevel))
    return Math.max(maxRainLevel, maxRefLevel)
  }

  build(): RainfallDTO {
    this.rainfallDTO.monthDecadal.getMonths().forEach(month => {
      const monthLabel = `${this.intl.formatMessage(messages[`month_${month}`])}`
      if (this.byDecadal) {
        this.rainfallDTO.monthDecadal.getDecadals(month).forEach(decadal => {
          this._buildRecord(month, monthLabel, decadal)
        })
      } else {
        this._buildRecord(month, monthLabel)
      }
    })
    this.rainfallDTO.keysWithRefs = this._buildKeyWithReferences()
    return this.rainfallDTO
  }

  _buildRecord(month: number, monthLabel: string, decadal: number) {
    const record = {}
    record[this.rainfallDTO.indexBy] = this.byDecadal ? `${month},${decadal}` : `${month}`
    record.indexLabel = this.byDecadal ? `${decadal}` : monthLabel
    record.lineValues = new Map()
    record.actualValue = {}
    this.keys.forEach(year => {
      const yearLabel = `${year}`
      const actualValue = this._getValue(year, month, decadal)
      record.actualValue[yearLabel] = actualValue
      this.rainfallDTO.hasData = this.rainfallDTO.hasData || actualValue !== undefined

      const cumulatedValue = addAndGet(this.cumulatedByYear, year, actualValue || 0)
      record[yearLabel] = asBarChartValue(cumulatedValue, this.rainfallDTO.maxValue)

      const refLevels = this.keyReferenceLevels.get(year)
      if (refLevels) {
        record.lineValues.set(yearLabel, this._getReferenceValue(refLevels, month, decadal))
      }
    })
    this.rainfallDTO.barData.push(record)
  }

  _getValue(year: number, month: number, decadal: number) {
    if (this.byDecadal) {
      return this.data.getDecadalLevel(year, month, decadal)
    }
    return this.data.getMonthLevel(year, month)
  }

  _getReferenceValue(referenceLevels: RainReferenceLevelData, month: number, decadal: number) {
    if (this.byDecadal) {
      return referenceLevels.getDecadalLevel(month, decadal)
    }
    return referenceLevels.getMonthLevel(month)
  }

  _buildKeyWithReferences() {
    const keyWithRefs = []
    this.keyReferenceLevels.forEach((ref, key: RainReferenceLevelData) => {
      keyWithRefs.push([`${key}`, `${ref.referenceYearStart} - ${ref.referenceYearEnd}`])
    })
    return keyWithRefs.sort(([k1, ], [k2, ]) => k1.localeCompare(k2)).reverse()
  }
}
