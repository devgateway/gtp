import * as api from "../../modules/api/index";
import * as C from "../../modules/entities/Constants";
import CommonConfig from "../../modules/entities/rainfall/CommonConfig";
import RainLevelChart from "../../modules/entities/rainfall/RainLevelChart";
import messages from "../../translations/messages";
import {WATER_RESOURCES} from "../reducers/Water";

export const loadAllWaterData = () => (dispatch, getState) => {
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources().then(transformAll)
  });
}

const transformAll = (allData) => {
  const {commonConfig, rainLevelChart} = allData
  return {
    commonConfig: new CommonConfig(commonConfig),
    rainLevelChart: new RainLevelChart(rainLevelChart)
  }
}

export const getRain = (intl) => (dispatch, getState) => {
  const {rainLevelChart} = getState().getIn(['water', 'data'])
  const keys = rainLevelChart.filter.years.sort().reverse()
  const barData = []
  const { byDecadal } = rainLevelChart.setting
  const indexBy = byDecadal ? 'monthDecadal' : 'month'

  C.MONTHS.forEach(month => {
    const monthLabel = `${intl.formatMessage(messages[`month_${month}`])}`
    if (byDecadal) {
      C.DECADALS.forEach(decadal => {
        const record = {}
        record[indexBy] = `${monthLabel},${decadal}`
        keys.forEach(year => {
          record[year] = asChartValue(rainLevelChart.data.getDecadalLevel(year, month, decadal))
        })
        barData.push(record)
      })
    } else {
      const record = {}
      record[indexBy] = monthLabel
      keys.forEach(year => {
        record[year] = asChartValue(rainLevelChart.data.getMonthLevel(year, month))
      })
      barData.push(record)
    }
  })

  return {
    data: barData,
    keys: keys,
    groupMode: 'grouped',
    indexBy,
    colors: {
      // TODO update based on mockup
      scheme: 'category10'
    },
    byDecadal
  }
}

const asChartValue = (value) => {
  if (value > 0) return value
  if (value === undefined) return C.NA_VALUE
  return C.ZERO_VALUE
}

