import {
  defineMessages
} from 'react-intl'

const messages = defineMessages({
  //LinksBlock
  home_tabs_microdata_title: {
    id: "home.tabs.microdata.title",
    defaultMessage: "Microdata"
  },


  home_tabs_indicator_title: {
    id: "home.tabs.indicator.title",
    defaultMessage: "Indicators"
  },


  home_tabs_market_title: {
    id: "home.tabs.market.title",
    defaultMessage: "Market"
  },


  home_tabs_source: {
    id: "home.tabs.source",
    defaultMessage: "Source:"
  },



  weather_title_1: {
    id: "home.weather.title_1",
    defaultMessage: "Main crops by zone"
  },
  weather_subtitle_1: {
    id: "home.weather.subtitle_1",
    defaultMessage: "This is where the short descriptive line will go here to describe the category"
  },

  weather_title_2: {
    id: "home.weather.title_2",
    defaultMessage: "Meteorological data"
  },

  weather_subtitle_2: {
    id: "home.weather.subtitle_2",
    defaultMessage: "Weather forecast for next 10 days"
  },

  weather_title_3: {
    id: "home.weather.title_3",
    defaultMessage: "Main crops by zone"
  },

  weather_subtitle_3: {
    id: "home.weather.subtitle_3",
    defaultMessage: "This is where the short descriptive line will go here to describe the category"
  },

  weather_title_4: {
    id: "home.weather.title_4",
    defaultMessage: "Main crops by zone"
  },

  weather_subtitle_4: {
    id: "home.weather.subtitle_4",
    defaultMessage: "This is where the short descriptive line will go here to describe the category"
  },

  indicator_global_population_short: {
    defaultMessage: 'Proportion of population below the international poverty line',
    id: 'indicators.global.population.short',
  },

  indicator_global_women_short: {
    defaultMessage: 'Women in the Agricultural sector',
    id: 'indicators.global.women.short',
  },

  indicator_global_food_short: {
    defaultMessage: 'Post-Harvest Loss',
    id: 'indicators.global.food.short',
  },

  indicator_global_aoi_short: {
    defaultMessage: 'Agriculture orientation index for government expenditures',
    id: 'indicators.global.aoi.short',
  },

  //analytic

  indicator_poverty_chart_by_region_and_year: {
    id: 'indicators.chart.poverty.by.region.and.year',
    defaultMessage: '% of population under poverty level.',
    description: '% of population under poverty level by region and year chart title'
  },

  indicator_poverty_chart_by_poor_no_poor_rencet_year: {
    id: 'indicators.chart.poverty.by.poor.no.poor',
    defaultMessage: '% of poor vs no poor by region ({year}).',
    description: '% of poor vs no poor by region chart title'
  },


  indicator_poverty_chart_historical_by_region: {
    id: 'indicators.chart.poverty.historical.by.region',
    defaultMessage: 'Historical % of population  under poverty level',
    description: 'Historical % of population  under poverty level chart title'
  },


  indicator_women_chart_distribution_by_age_tab_title: {
    id: 'indicators.chart.women.distribution_by_age_tab_title',
    defaultMessage: 'Distribution by age.',
    description: 'Distribution by age tab title'
  },

  indicator_women_chart_distribution_by_method_tab_title: {
    id: 'indicators.chart.women.distribution_by_method_tab_title',
    defaultMessage: 'Distribution by method of enforcement.',
    description: 'Distribution by method of enforcement tab title'
  },


  indicator_women_chart_distribution_by_gender: {
    id: 'indicators.chart.women.distribution_by_gender',
    defaultMessage: 'Distribution of the agricultural population by age group and gender ({year}).',
    description: 'Distribution of the agricultural population gender chart title'
  },

  indicator_women_chart_distribution_historical: {
    id: 'indicators.chart.women.distribution_historical',
    defaultMessage: 'Historical Female Distribution.',
    description: 'Historical Female Distribution chart title'
  },

  indicator_women_chart_distribution_by_enforcement_method: {
    id: 'indicators.chart.women.distribution_by_method',
    defaultMessage: 'Distribution of parcels by method of enforcement and gender ({year}).',
    description: 'Distribution of parcels by method of enforcement and gender ({year}).',
  },


  indicator_women_chart_distribution_by_enforcement_historical: {
    id: 'indicators.chart.women.distribution_by_enforcement_historical',
    defaultMessage: 'Historical female distribution of parcels by method of enforcement.',
    description: 'Historical female distribution of parcels by method of enforcement chart title'
  },


  indicator_food_chart_average_production_loss: {
    id: 'indicators.chart.food_average_production_loss',
    defaultMessage: 'Destinations of agricultural production ({year})',
    description: 'Destinations of agricultural production chart title'

  },

  indicator_food_chart_average_quantity: {
    id: 'indicators.chart.food.average.quantity',
    defaultMessage: 'Average quantity (in kg) per household ({year})',
    description: 'Average quantity (in kg) per household chart title'

  },


  indicator_aoi_total_public_budget: {
    id: "indicators.chart.aoi.total.public.budget",
    defaultMessage: "Total public budget, expenditure on the agricultural sector.",
    description: 'Total public budget, expenditure on the agricultural sector. chart title'
  },
  indicator_aoi_composition_of_subsidies: {
    id: "indicators.chart.aoi.total.public.subsidies",
    defaultMessage: "Composition of subsidies to agricultural investments.",
    description: 'Composition of subsidies to agricultural investments chart title'
  },

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

  age: {
    id: 'data.fields.age',
    defaultMessage: 'Age',
    description: 'Age data field'
  },


  totals: {
    id: 'data.fields.totals',
    defaultMessage: 'Totals',
    description: 'Totals table label'
  },

  methodOfEnforcement: {
    id: 'data.fields.enforcement_method',
    defaultMessage: 'Method of Enforcement',
    description: 'Age data field'
  },

  weekly_consumption: {
    id: 'data.fields.weekly_consumption',
    defaultMessage: 'Weekly Consumption',
    description: 'Weekly Consumption Data Field'
  },

  house_hold_size: {
    id: 'data.fields.house_hold_size',
    defaultMessage: 'House Hold Size ',
    description: 'House Hold Size Field'
  },


  daily_consumption: {
    id: 'data.fields.daily_consumption',
    defaultMessage: 'Daily Consumption (Kg)',
    description: 'Weekly Consumption Data Field'
  },

  crop_type: {
    id: 'data.fields.crop_type',
    defaultMessage: 'Crop Type',
    description: 'Crop Type Data Field'
  },

  crop: {
    id: 'data.fields.crop',
    defaultMessage: 'Crop',
    description: 'Crop Data Field'
  },

  area: {
    id: 'data.fields.area',
    defaultMessage: 'Area',
    description: 'Area Data Field'
  },
  production: {
    id: 'data.fields.production',
    defaultMessage: 'Production',
    description: 'Production Data Field'
  },

  yield: {
    id: 'data.fields.yield',
    defaultMessage: 'Yield',
    description: 'Yield Data Field'
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
  market: {
    id: 'data.fields.market',
    defaultMessage: 'Market',
    description: 'Market Data Field'
  },

  whole_sale_buy_price: {
    id: 'data.fields.whole_sale_buy_price',
    defaultMessage: 'Whole Sale Buy Price',
    description: 'Whole Sale Buy Price Data Field'
  },
  selling_price: {
    id: 'data.fields.selling_price',
    defaultMessage: 'Selling Price',
    description: 'Selling Price Data Field'
  },
  retail_buying_price: {
    id: 'data.fields.retail_buying_price',
    defaultMessage: 'Retail Buying Price',
    description: 'Selling Price Data Field'
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



  age: {
    id: 'data.fields.age',
    defaultMessage: 'Age',
    description: 'Age data field'
  },


  blues: {
    id: 'data.fields.blues',
    defaultMessage: 'Blues',
  },

  greens: {
    id: 'data.fields.greens',
    defaultMessage: 'Greens',
  },

  greys: {
    id: 'data.fields.greys',
    defaultMessage: 'Greys',
  },

  oranges: {
    id: 'data.fields.oranges',
    defaultMessage: 'Oranges',
  },

  purples: {
    id: 'data.fields.purples',
    defaultMessage: 'Purples',
  },

  reds: {
    id: 'data.fields.reds',
    defaultMessage: 'Reds',
  },
  blue_to_green: {
    id: 'data.fields.blue_to_green',
    defaultMessage: 'Blue to Green',
  },
  blue_to_purple: {
    id: 'data.fields.blue_to_purple',
    defaultMessage: 'Blue To Purple',
  },
  orange_to_red: {
    id: 'data.fields.orange_to_red',
    defaultMessage: 'Orange To Red',
  },


  table_help_unused_cols: {
    id: 'table.unsed.cols',
    defaultMessage: 'Drag and drop elements as rows or columns',

  },
  table_help_used_cols: {
    id: 'table.used.cols',
    defaultMessage: 'Element used as columns',

  },
  table_help_used_rows: {
    id: 'table.unsed.rows',
    defaultMessage: 'Element used as Rows',

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


  pivot_renders_table: {
    id: 'pivot.renders.table',
    defaultMessage: 'Table'
  },

  pivot_renders_table_heatmap: {
    id: 'pivot.renders.table_heatmap',
    defaultMessage: 'Table Heatmap'
  },

  pivot_renders_table_col_heatmap: {
    id: 'pivot.renders.table_col_heatmap',
    defaultMessage: 'Table Col Heatmap'
  },

  pivot_renders_table_row_heatmap: {
    id: 'pivot.renders.table_row_heatmap',
    defaultMessage: 'Table Row Heatmap'
  },

  pivot_renders_exportable_tsv: {
    id: 'pivot.renders.exportable_tsv',
    defaultMessage: 'Exportable TSV'
  },

  pivot_renders_grouped_column_chart: {
    id: 'pivot.renders.grouped_column_chart',
    defaultMessage: 'Grouped Column Chart'
  },

  pivot_renders_stacked_column_chart: {
    id: 'pivot.renders.stacked_column_chart',
    defaultMessage: 'Stacked Column Chart'
  },

  pivot_renders_grouped_bar_chart: {
    id: 'pivot.renders.grouped_bar_chart',
    defaultMessage: 'Grouped Bar Chart'
  },

  pivot_renders_stacked_bar_chart: {
    id: 'pivot.renders.stacked_bar_chart',
    defaultMessage: 'Stacked Bar Chart'
  },

  pivot_renders_line_chart: {
    id: 'pivot.renders.line_chart',
    defaultMessage: 'Line Chart'
  },

  pivot_renders_dot_chart: {
    id: 'pivot.renders.dot_chart',
    defaultMessage: 'Dot Chart'
  },

  pivot_renders_area_chart: {
    id: 'pivot.renders.area_chart',
    defaultMessage: 'Area Chart'
  },

  pivot_renders_scatter_chart: {
    id: 'pivot.renders.scatter_chart',
    defaultMessage: 'Scatter Chart'
  },
  pivot_renders_multiple_pie_chart: {
    id: 'pivot.renders.multiple_pie_chart',
    defaultMessage: 'Multiple Pie Chart'
  },

  microdata_filters_keyword: {
    id: 'microdata.filters.keyword.label',
    defaultMessage: "Keyword Search"
  },
  microdata_filters_start_date: {
    id: 'microdata.filters.start_date.label',
    defaultMessage: "Start Date"
  },
  microdata_filters_end_date: {
    id: 'microdata.filters.end_date.label',
    defaultMessage: "End Date"
  },
  microdata_filters_organization: {
    id: 'microdata.filters.organization.label',
    defaultMessage: "Organizations"
  },
  data_no_data_available: {
    id: 'data.no_available',
    defaultMessage: "No data available"
  },


  national_indicators_actual: {
    id: 'national.indicator.actual',
    defaultMessage: "Actual"
  },

  national_indicators_reference: {
    id: 'national.indicator.reference',
    defaultMessage: "Reference"
  },

  national_indicators_target: {
    id: 'national.indicator.target',
    defaultMessage: "Target"
  },

});



export default messages
