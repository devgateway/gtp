export const yearsToOptions = (years) => years.sort().reverse().map(y => ({
  key: y,
  text: y,
  value: y
}))

export const anyWithIdAndNameToOptions = (list) => list.map(el => ({
  key: el.id,
  text: el.name,
  value: el.id,
}))

export const anyWithIdAndLabelToOptions = (list) => list.map(el => ({
  key: el.id,
  text: el.label,
  value: el.id,
}))
