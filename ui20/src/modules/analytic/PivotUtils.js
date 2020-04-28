import { aggregatorTemplates } from 'react-pivottable/Utilities'

import messages from '../../translations/messages'

var addSeparators = function addSeparators (nStr, thousandsSep, decimalSep) {
  var x = String(nStr).split('.')
  var x1 = x[0]
  var x2 = x.length > 1 ? decimalSep + x[1] : ''
  var rgx = /(\d+)(\d{3})/
  while (rgx.test(x1)) {
    x1 = x1.replace(rgx, '$1' + thousandsSep + '$2')
  }
  return x1 + x2
}

var numberFormat = function numberFormat (opts_in) {
  var defaults = {
    digitsAfterDecimal: 2,
    scaler: 1,
    thousandsSep: ',',
    decimalSep: '.',
    prefix: '',
    suffix: ''
  }
  var opts = Object.assign({}, defaults, opts_in)
  return function (x) {
    if (isNaN(x) || !isFinite(x)) {
      return ''
    }
    var result = addSeparators((opts.scaler * x).toFixed(opts.digitsAfterDecimal), opts.thousandsSep, opts.decimalSep)
    return '' + opts.prefix + result + opts.suffix
  }
}

var usFmt = numberFormat()

var usFmtInt = numberFormat({ digitsAfterDecimal: 0 })

var usFmtPct = numberFormat({ digitsAfterDecimal: 1, scaler: 100, suffix: '%' })

var frFmt = numberFormat({ decimalSep: ',', thousandsSep: ' ' })

var frFmtInt = numberFormat({ digitsAfterDecimal: 0 })

var frFmtPct = numberFormat({ digitsAfterDecimal: 1, scaler: 100, suffix: '%' })

const getFmt = (locale) => (locale === 'fr')
  ? frFmt
  : usFmt

const getFmtInt = (locale) => (locale === 'fr')
  ? frFmtInt
  : usFmtInt

const getFmtPct = (locale) => (locale === 'fr')
  ? frFmtPct
  : usFmtPct

// default aggregators & renderers use US naming and number formatting
export const aggregators = (intl) => {
  const aggregators = {}
  const locale = intl.locale

  aggregators[intl.formatMessage(messages.count)] = aggregatorTemplates.count(getFmtInt(locale))
  aggregators[intl.formatMessage(messages.count_unique_values)] = aggregatorTemplates.countUnique(getFmtInt(locale))
  aggregators[intl.formatMessage(messages.list_unique_values)] = aggregatorTemplates.listUnique(', ')
  aggregators[intl.formatMessage(messages.sum)] = aggregatorTemplates.sum(getFmt(locale))
  aggregators[intl.formatMessage(messages.integer_sum)] = aggregatorTemplates.sum(getFmtInt(locale))
  aggregators[intl.formatMessage(messages.average)] = aggregatorTemplates.average(getFmt(locale))
  aggregators[intl.formatMessage(messages.median)] = aggregatorTemplates.median(getFmt(locale))
  aggregators[intl.formatMessage(messages.sample_variance)] = aggregatorTemplates.var(1, getFmt(locale))
  aggregators[intl.formatMessage(messages.sample_standard_deviation)] = aggregatorTemplates.stdev(1, getFmt(locale))
  aggregators[intl.formatMessage(messages.minimum)] = aggregatorTemplates.min(getFmt(locale))
  aggregators[intl.formatMessage(messages.maximum)] = aggregatorTemplates.max(getFmt(locale))
  aggregators[intl.formatMessage(messages.first)] = aggregatorTemplates.first(getFmt(locale))
  aggregators[intl.formatMessage(messages.last)] = aggregatorTemplates.last(getFmt(locale))
  aggregators[intl.formatMessage(messages.sum_over_sum)] = aggregatorTemplates.sumOverSum(getFmt())
  aggregators[intl.formatMessage(messages.sum_as_fraction_of_total)] = aggregatorTemplates.fractionOf(aggregatorTemplates.sum(), 'total', getFmtPct(locale))
  aggregators[intl.formatMessage(messages.sum_as_fraction_of_rows)] = aggregatorTemplates.fractionOf(aggregatorTemplates.sum(), 'row', getFmtPct(locale))
  aggregators[intl.formatMessage(messages.sum_as_fraction_of_columns)] = aggregatorTemplates.fractionOf(aggregatorTemplates.sum(), 'col', getFmtPct(locale))
  aggregators[intl.formatMessage(messages.count_as_fraction_of_total)] = aggregatorTemplates.fractionOf(aggregatorTemplates.count(), 'total', getFmtPct(locale))
  aggregators[intl.formatMessage(messages.count_as_fraction_of_rows)] = aggregatorTemplates.fractionOf(aggregatorTemplates.count(), 'row', getFmtPct(locale))
  aggregators[intl.formatMessage(messages.count_as_fraction_of_columns)] = aggregatorTemplates.fractionOf(aggregatorTemplates.count(), 'col', getFmtPct(locale))

  return aggregators
}
