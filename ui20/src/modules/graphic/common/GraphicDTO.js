import {MONTHS} from "../../entities/Constants"

export const yearsToOptions = (years) => years.sort().reverse().map(y => ({
  key: y,
  text: y,
  value: y
}))

export const monthsToOptions = (intl, months: Array<number> = MONTHS) => months.map(m => ({
  key: m,
  text: intl.formatMessage({ id: `all.month.${m}`}),
  value: m
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
