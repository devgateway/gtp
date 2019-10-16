import {
  injectIntl
} from 'react-intl';

import {
  sortAs,
  aggregatorTemplates,
  numberFormat,
  localeStrings
} from 'react-pivottable/Utilities';
// aggregator templates default to US number formatting but this is overrideable
var usFmt = numberFormat();
var usFmtInt = numberFormat({
  digitsAfterDecimal: 0
});
var usFmtPct = numberFormat({
  digitsAfterDecimal: 1,
  scaler: 100,
  suffix: '%'
});


// default aggregators & renderers use US naming and number formatting
export const aggregators = (tpl) => {
  return {
    'Count': tpl.count(usFmtInt),
    'Count Unique Values': tpl.countUnique(usFmtInt),
    'List Unique Values': tpl.listUnique(', '),
    'Sum': tpl.sum(usFmt),
    'Integer Sum': tpl.sum(usFmtInt),
    'Average': tpl.average(usFmt),
    'Median': tpl.median(usFmt),
    'Sample Variance': tpl.var(1, usFmt),
    'Sample Standard Deviation': tpl.stdev(1, usFmt),
    'Minimum': tpl.min(usFmt),
    'Maximum': tpl.max(usFmt),
    'First': tpl.first(usFmt),
    'Last': tpl.last(usFmt),
    'Sum over Sum': tpl.sumOverSum(usFmt),
    'Sum as Fraction of Total': tpl.fractionOf(tpl.sum(), 'total', usFmtPct),
    'Sum as Fraction of Rows': tpl.fractionOf(tpl.sum(), 'row', usFmtPct),
    'Sum as Fraction of Columns': tpl.fractionOf(tpl.sum(), 'col', usFmtPct),
    'Count as Fraction of Total': tpl.fractionOf(tpl.count(), 'total', usFmtPct),
    'Count as Fraction of Rows': tpl.fractionOf(tpl.count(), 'row', usFmtPct),
    'Count as Fraction of Columns': tpl.fractionOf(tpl.count(), 'col', usFmtPct)
  }
};



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
