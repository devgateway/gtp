import * as C from "../entities/Constants"

export const defaultMapFunc = () => new Map()

export const getOrDefaultMap = (map, key) => getOrDefault(map, key, null, defaultMapFunc)
export const getOrDefaultArray = (map, key) => getOrDefault(map, key, [])

export const getOrDefault = (map, key, defaultValue, defaultFunc) => {
  if (!map.has(key)) {
    map.set(key, !defaultFunc ? defaultValue : defaultFunc())
  }
  return map.get(key)
}

export const asBarChartValue = (value) => {
  if (value > 0) return value
  if (value === undefined) return C.NA_VALUE
  return C.ZERO_VALUE
}

export const toSignedNumberLocaleString = (intl, value: number, defaultNullOrUndefined = '') => {
  if (value === null || value === undefined) return defaultNullOrUndefined
  const sign = value > 0 ? '+' : ''
  return `${sign}${intl.formatNumber(value)}`
}

export const compareDateExcludingTime = (d1:Date, d2:Date) => {
  if (d1.getFullYear() !== d2.getFullYear()) return d1.getFullYear() - d2.getFullYear()
  if (d1.getMonth() !== d2.getMonth()) return d1.getMonth() - d2.getMonth()
  return d1.getDay() - d2.getDay()
}

export const apiMonthDayAwareCompare = (o1, o2) => o1.monthDay.localeCompare(o2.monthDay)
