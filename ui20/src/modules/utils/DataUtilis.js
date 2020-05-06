
export const getOrDefault = (map, key, defaultValue, defaultFunc) => {
  if (!map.has(key)) {
    map.set(key, !defaultFunc ? defaultValue : defaultFunc())
  }
  return map.get(key)
}
