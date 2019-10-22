import {
  injectIntl
} from 'react-intl';

import {
  sortAs,
  aggregatorTemplates,
  numberFormat,
  localeStrings
} from 'react-pivottable/Utilities';

import messages from '../translations/messages'



var usFmt = numberFormat();
var usFmtInt = numberFormat({
  digitsAfterDecimal: 0
});
var usFmtPct = numberFormat({
  digitsAfterDecimal: 1,
  scaler: 100,
  suffix: '%'
});


var frFmt = numberFormat({
  thousandsSep: " ",
  decimalSep: ","
});
var frFmtInt = numberFormat({
  digitsAfterDecimal: 0,
  thousandsSep: " ",
  decimalSep: ","
});
var frFmtPct = numberFormat({
  digitsAfterDecimal: 1,
  scaler: 100,
  suffix: "%",
  thousandsSep: " ",
  decimalSep: ","
});


// default aggregators & renderers use US naming and number formatting
export const aggregators = (int) => {


  let aggregators = {}

  aggregators[int.formatMessage(messages.count)] = aggregatorTemplates.count(usFmtInt)
  aggregators[int.formatMessage(messages.count_unique_values)] = aggregatorTemplates.countUnique(usFmtInt)
  aggregators[int.formatMessage(messages.list_unique_values)] = aggregatorTemplates.listUnique(', ')
  aggregators[int.formatMessage(messages.sum)] = aggregatorTemplates.sum(usFmt)
  aggregators[int.formatMessage(messages.integer_sum)] = aggregatorTemplates.sum(usFmtInt)
  aggregators[int.formatMessage(messages.average)] = aggregatorTemplates.average(usFmt)
  aggregators[int.formatMessage(messages.median)] = aggregatorTemplates.median(usFmt)
  aggregators[int.formatMessage(messages.sample_variance)] = aggregatorTemplates.var(1, usFmt)
  aggregators[int.formatMessage(messages.sample_standard_deviation)] = aggregatorTemplates.stdev(1, usFmt)
  aggregators[int.formatMessage(messages.minimum)] = aggregatorTemplates.min(usFmt)
  aggregators[int.formatMessage(messages.maximum)] = aggregatorTemplates.max(usFmt)
  aggregators[int.formatMessage(messages.first)] = aggregatorTemplates.first(usFmt)
  aggregators[int.formatMessage(messages.last)] = aggregatorTemplates.last(usFmt)
  aggregators[int.formatMessage(messages.sum_over_sum)] = aggregatorTemplates.sumOverSum(usFmt)
  aggregators[int.formatMessage(messages.sum_as_fraction_of_total)] = aggregatorTemplates.fractionOf(aggregatorTemplates.sum(), 'total', usFmtPct)
  aggregators[int.formatMessage(messages.sum_as_fraction_of_rows)] = aggregatorTemplates.fractionOf(aggregatorTemplates.sum(), 'row', usFmtPct)
  aggregators[int.formatMessage(messages.sum_as_fraction_of_columns)] = aggregatorTemplates.fractionOf(aggregatorTemplates.sum(), 'col', usFmtPct)
  aggregators[int.formatMessage(messages.count_as_fraction_of_total)] = aggregatorTemplates.fractionOf(aggregatorTemplates.count(), 'total', usFmtPct)
  aggregators[int.formatMessage(messages.count_as_fraction_of_rows)] = aggregatorTemplates.fractionOf(aggregatorTemplates.count(), 'row', usFmtPct)
  aggregators[int.formatMessage(messages.count_as_fraction_of_columns)] = aggregatorTemplates.fractionOf(aggregatorTemplates.count(), 'col', usFmtPct)

  return aggregators
}


//renderers, aggregators and localeStrings

export const locales = {
  en: {
    aggregators: aggregators,
    localeStrings: {
      renderError: 'An error occurred rendering the PivotTable results.',
      computeError: 'An error occurred computing the PivotTable results.',
      uiRenderError: 'An error occurred rendering the PivotTable UI.',
      selectAll: 'Select All',
      selectNone: 'Select None',
      tooMany: '(too many to list)',
      filterResults: 'Filter values',
      apply: 'Apply',
      cancel: 'Cancelar',
      totals: 'totales',
      vs: 'vs',
      by: 'by'
    }
  }
};
