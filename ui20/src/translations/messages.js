import {
  defineMessages
} from 'react-intl'

const messages = defineMessages({

  department: {
    id: 'data.fields.department',
    defaultMessage: 'Department',
    description: 'Department Data Field'
  },

  billions: {
    id: 'data.fields.billions',
    defaultMessage: 'Billions',
    description: 'Billions Data Field'
  },

  billions_short: {
    id: 'data.fields.billions_short',
    defaultMessage: 'B',
    description: 'Billions Data Field'
  },
  year: {
    id: 'data.fields.year',
    defaultMessage: 'Year',
    description: 'Year Data Field'
  },

  totals: {
    id: 'data.fields.totals',
    defaultMessage: 'Totals',
    description: 'Totals table label'
  },

  region_code: {
    id: 'data.fields.region_code',
    defaultMessage: 'Region Code',
    description: 'Region Code Data Field'
  },
  region: {
    id: 'data.fields.region',
    defaultMessage: 'Region',
    description: 'Region Data Field'
  },
  percent: {
    id: 'data.fields.percent',
    defaultMessage: 'Percent',
    description: 'Percent Label'
  },

  kg: {
    id: 'data.fields.kg',
    defaultMessage: 'Kg',
    description: 'Kg Label'
  },

  market: {
    id: 'data.fields.market',
    defaultMessage: 'Market',
    description: 'Market Data Field'
  },

  date: {
    id: 'data.fields.date',
    defaultMessage: 'Date',
    description: 'Date Data Field'
  },
  quantity: {
    id: 'data.fields.quantity',
    defaultMessage: 'Quantity',
    description: 'Quantity Data Field'
  },

  table_help_unused_cols: {
    id: 'table.unsed.cols',
    defaultMessage: 'Drag and drop elements as rows or columns'

  },
  table_help_used_cols: {
    id: 'table.used.cols',
    defaultMessage: 'Element used as columns'

  },
  table_help_used_rows: {
    id: 'table.unsed.rows',
    defaultMessage: 'Element used as Rows'

  },
  table_filter_values: {
    id: 'table.filter_values',
    defaultMessage: 'Filter Values'
  },

  table_select: {
    id: 'table.select',
    defaultMessage: 'Select'
  },

  table_all: {
    id: 'table.all',
    defaultMessage: 'All'
  },

  table_filter_select_all: {
    id: 'table.select_all',
    defaultMessage: 'Select All'
  },
  table_filter_deselect: {
    id: 'table.deselect',
    defaultMessage: 'Deselect All'
  },
  count: {
    id: 'table.aggregators.count',
    defaultMessage: 'Count',
    description: 'Count aggregator'
  },

  count_unique_values: {
    id: 'table.aggregators.count_unique_values',
    defaultMessage: 'Count',
    description: 'Count Unique Values'
  },
  list_unique_values: {
    id: 'table.aggregators.list_unique_values',
    defaultMessage: 'List Unique Values',
    description: 'List Unique Values'
  },

  sum: {
    id: 'table.aggregators.sum',
    defaultMessage: 'Sum',
    description: 'Sum'
  },
  integer_sum: {
    id: 'table.aggregators.integer_sum',
    defaultMessage: 'Integer Sum',
    description: 'Integer Sum'
  },

  average: {
    id: 'table.aggregators.average',
    defaultMessage: 'Average',
    description: 'Average'
  },

  median: {
    id: 'table.aggregators.median',
    defaultMessage: 'Median',
    description: 'Median'
  },

  sample_variance: {
    id: 'table.aggregators.sample_variance',
    defaultMessage: 'Sample Variance',
    description: 'Sample Variance'
  },
  sample_standard_deviation: {
    id: 'table.aggregators.sample_standard_deviation',
    defaultMessage: 'Sample Standard Deviation',
    description: 'Sample Standard Deviation'
  },
  minimum: {
    id: 'table.aggregators.minimum',
    defaultMessage: 'Minimum',
    description: 'Minimum'
  },

  maximum: {
    id: 'table.aggregators.maximum',
    defaultMessage: 'Maximum',
    description: 'Maximum'
  },

  first: {
    id: 'table.aggregators.first',
    defaultMessage: 'First',
    description: 'First'
  },
  last: {
    id: 'table.aggregators.last',
    defaultMessage: 'Last',
    description: 'Last'
  },

  sum_over_sum: {
    id: 'table.aggregators.sum_over_sum',
    defaultMessage: 'Sum over Sum',
    description: 'Sum over Sum'
  },

  sum_as_fraction_of_total: {
    id: 'table.aggregators.sum_as_fraction_of_total',
    defaultMessage: 'Sum as Fraction of Total',
    description: 'Sum as Fraction of Total'
  },
  sum_as_fraction_of_rows: {
    id: 'table.aggregators.sum_as_fraction_of_rows',
    defaultMessage: 'Sum as Fraction of Rows',
    description: 'Sum as Fraction of Rows'
  },
  sum_as_fraction_of_columns: {
    id: 'table.aggregators.sum_as_fraction_of_columns',
    defaultMessage: 'Sum as Fraction of Columns',
    description: 'Sum as Fraction of Columns'
  },
  count_as_fraction_of_total: {
    id: 'table.aggregators.count_as_fraction_of_total',
    defaultMessage: 'Count as Fraction of Total',
    description: 'Count as Fraction of Total'
  },

  count_as_fraction_of_rows: {
    id: 'table.aggregators.count_as_fraction_of_rows',
    defaultMessage: 'Count as Fraction of Rows',
    description: 'Count as Fraction of Rows'
  },

  count_as_fraction_of_columns: {
    id: 'table.aggregators.count_as_fraction_of_columns',
    defaultMessage: 'Count as Fraction of Columns',
    description: 'Count as Fraction of Columns'
  },

  data_no_data_available: {
    id: 'data.no_available',
    defaultMessage: 'No data available'
  },

  valueNA: {
    id: 'all.graphic.value.NA',
    defaultMessage: 'N/A'
  },

  period: {
    id: 'all.period',
    defaultMessage: 'Time'
  },

  months: {
    id: 'all.months',
    defaultMessage: 'Months'
  },

  month_1: {
    id: 'all.month.1',
    defaultMessage: 'January'
  },

  month_2: {
    id: 'all.month.2',
    defaultMessage: 'February'
  },

  month_3: {
    id: 'all.month.3',
    defaultMessage: 'March'
  },

  month_4: {
    id: 'all.month.4',
    defaultMessage: 'April'
  },

  month_5: {
    id: 'all.month.5',
    defaultMessage: 'May'
  },

  month_6: {
    id: 'all.month.6',
    defaultMessage: 'June'
  },

  month_7: {
    id: 'all.month.7',
    defaultMessage: 'July'
  },

  month_8: {
    id: 'all.month.8',
    defaultMessage: 'August'
  },

  month_9: {
    id: 'all.month.9',
    defaultMessage: 'September'
  },

  month_10: {
    id: 'all.month.10',
    defaultMessage: 'October'
  },

  month_11: {
    id: 'all.month.11',
    defaultMessage: 'November'
  },

  month_12: {
    id: 'all.month.12',
    defaultMessage: 'December'
  },

  decadals: {
    id: 'all.decadals',
    defaultMessage: 'Decadals'
  },

  decadalsPerMonth: {
    id: 'all.decadalsPerMonth',
    defaultMessage: 'Decadals per month'
  },

  rainfall: {
    id: 'water.rainfall.level',
    defaultMessage: 'Rain (mm)'
  },

  noOfDays: {
    id: 'water.drysequence.days',
    defaultMessage: 'Number of days'
  },
})

export default messages
