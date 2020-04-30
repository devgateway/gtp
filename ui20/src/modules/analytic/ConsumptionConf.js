/* eslint-disable */
import React from 'react'
import { defineMessages } from 'react-intl'
import messages from '../../translations/messages'

export const configurator = (intl) => {
  const labelField = (intl.locale == 'fr') ? 'labelFr' : 'label'

  return {

    pivottable: {
      rows: [intl.formatMessage(messages.department)],
      cols: [intl.formatMessage(messages.crop_type)],
      vals: [intl.formatMessage(messages.weekly_consumption)],
      hiddenAttributes: [
        '_cropType', '_cropSubType', '_department', '_region'
      ],

      hiddenFromAggregators: [
        intl.formatMessage(messages.department),
        intl.formatMessage(messages.year),
        intl.formatMessage(messages.crop_type),
        intl.formatMessage(messages.region),
        intl.formatMessage(messages.region_code)
      ],
      hiddenFromDragDrop: [
        intl.formatMessage(messages.daily_consumption),
        intl.formatMessage(messages.house_hold_size),
        intl.formatMessage(messages.weekly_consumption)
      ],
      aggregatorName: intl.formatMessage(messages.sum),
      rendererName: 'Table'
    },

    fields: {
      department: '_department',
      region: '_region',
      year: intl.formatMessage(messages.year),
      cropType: '_cropType',
      cropSubType: '_cropSubType',
      householdSize: intl.formatMessage(messages.house_hold_size),
      dailyConsumption: intl.formatMessage(messages.daily_consumption),
      weeklyConsumption: intl.formatMessage(messages.weekly_consumption)
    },

    extraFields: {
      'Crop Type': {
        label: intl.formatMessage(messages.crop_type),
        extractor: row => items => items.cropType.filter(it => it.id == row._cropType)[0][labelField]
      },
      Department: {
        label: intl.formatMessage(messages.department),
        extractor: row => items => items.department.filter(it => it.id == row._department)[0].name
      },
      'Region Code': {
        label: intl.formatMessage(messages.region_code),
        extractor: row => items => items.region.filter(it => it.id == row._region)[0].code
      },
      Region: {
        label: intl.formatMessage(messages.region),
        extractor: row => items => items.region.filter(it => it.id == row._region)[0].name
      }
    }

  }
}
export default configurator