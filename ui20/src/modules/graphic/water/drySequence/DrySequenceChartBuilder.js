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
  hasData: boolean

  constructor(drySequenceChart: DrySequenceChart, intl) {
    this.drySequenceChart = drySequenceChart
    this.intl = intl
    this.barData = []
    this.isDaysWithRain = drySequenceChart ? drySequenceChart.settings.isDaysWithRain : false
    this.indexBy = 'month'
    // always by decadal, may be in future we'll want to change
    this.byDecadal = true
    this.hasData = false
    this._init()
  }

  _init() {
    this.keys = [1, 2, 3]
    this.monthDecadal = this.drySequenceChart && new MonthDecadal(this.drySequenceChart.filter.year, C.SEASON_MONTHS)
  }

  build() {
    if (!this.drySequenceChart) {
      return null;
    }
    this.monthDecadal.getMonths().forEach(month => {
      const monthLabel = `${this.intl.formatMessage(messages[`month_${month}`])}`
      const record = {}
      this.monthDecadal.getDecadals(month).forEach(decadal => {
        const value = this._getValue(month, decadal)
        record[`${decadal}`] = asBarChartValue(value)
        this.hasData = this.hasData || value !== undefined
      })
      record[this.indexBy] = monthLabel
      this.barData.push(record)
    })
    return {
      drySequenceChartDTO: new DrySequenceChartDTO(
        this.indexBy,
        this.keys.map(k => `${k}`),
        this.barData,
        this.isDaysWithRain,
        this.hasData)
    }
  }

  _getValue(month: number, decadal: number) {
    if (this.byDecadal) {
      return this.drySequenceChart.data.getDecadalLevel(month, decadal, this.isDaysWithRain)
    }
    return this.drySequenceChart.data.getMonthLevel(month, this.isDaysWithRain)
  }
}
