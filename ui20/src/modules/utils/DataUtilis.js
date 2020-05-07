import * as C from "../entities/Constants"

export const defaultMapFunc = () => new Map()

export const getOrDefaultMap = (map, key) => getOrDefault(map, key, null, defaultMapFunc)

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

