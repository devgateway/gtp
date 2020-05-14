import messages from "../../../../translations/messages"
import * as C from "../../../entities/Constants"
import DrySequenceChart from "../../../entities/drySequence/DrySequenceChart"
import {asBarChartValue} from "../../../utils/DataUtilis"
import MonthDecadal from "../../../utils/MonthDecadal"
import DrySequenceChartDTO from "./DrySequenceChartDTO"

export default class DrySequenceChartBuilder {
  drySequenceChart: DrySequenceChart
  barData: Array
  indexBy: string
  monthDecadal: MonthDecadal
  isDaysWithRain: boolean

  constructor(drySequenceChart: DrySequenceChart, intl) {
    this.drySequenceChart = drySequenceChart
    this.intl = intl
    this.barData = []
    this.isDaysWithRain = true
    this.indexBy = 'month'
    // always by decadal, may be in future we'll want to change
    this.byDecadal = true
    this._init()
  }

  _init() {
    this.keys = [1, 2, 3]
    this.monthDecadal = new MonthDecadal(this.drySequenceChart.filter.year, C.SEASON_MONTHS)
  }

  build() {
    this.monthDecadal.getMonths().forEach(month => {
      const monthLabel = `${this.intl.formatMessage(messages[`month_${month}`])}`
      this.monthDecadal.getDecadals(month).forEach(decadal => {
        this._buildRecord(month, monthLabel, decadal)
      })
    })
    return new DrySequenceChartDTO(this.indexBy, this.keys, this.barData)
  }

  _buildRecord(month: number, monthLabel: string, decadal: number) {
    const record = {}
    record[this.indexBy] = month
    record.indexLabel = monthLabel
    this.keys.forEach(decadal => {
      record[`${decadal}`] = asBarChartValue(this._getValue(month, decadal))
    })
    this.barData.push(record)
  }

  _getValue(month: number, decadal: number) {
    if (this.byDecadal) {
      return this.drySequenceChart.data.getDecadalLevel(month, decadal, this.isDaysWithRain)
    }
    return this.drySequenceChart.data.getMonthLevel(month, this.isDaysWithRain)
  }
}
