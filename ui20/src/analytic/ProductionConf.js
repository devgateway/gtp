/* eslint-disable */
import React from 'react'
import { defineMessages } from 'react-intl'
import messages from '../translations/messages'

export const configurator = (intl) => {
  const labelField = (intl.locale == 'fr') ? 'labelFr' : 'label'

  return {

    pivottable: {
      rows: [intl.formatMessage(messages.region)],
      cols: [intl.formatMessage(messages.crop_type)],
      vals: [intl.formatMessage(messages.production)],
      hiddenAttributes: [
        '_region', '_cropType', '_department'
      ],
      hiddenFromAggregators: [
        intl.formatMessage(messages.year),
        intl.formatMessage(messages.crop_type),
        intl.formatMessage(messages.region),
        intl.formatMessage(messages.region_code),
        intl.formatMessage(messages.department)
      ],
      hiddenFromDragDrop: [
        intl.formatMessage(messages.area),
        intl.formatMessage(messages.production),
        intl.formatMessage(messages.yield)
      ],
      aggregatorName: intl.formatMessage(messages.sum),
      rendererName: 'Table'
    },

    fields: {
      surface: intl.formatMessage(messages.area),
      production: intl.formatMessage(messages.production),
      year: intl.formatMessage(messages.year),
      yield: intl.formatMessage(messages.yield),
      region: '_region',
      department: '_department',
      cropType: '_cropType'
    },

    extraFields: {
      'Region Code': {
        label: intl.formatMessage(messages.region_code),
        extractor: row => items => items.region.filter(it => it.id == row._region)[0].code
      },
      Region: {
        label: intl.formatMessage(messages.region),
        extractor: row => items => items.region.filter(it => it.id == row._region)[0].name
      },
      Department: {
        label: intl.formatMessage(messages.department),
        extractor: row => items => items.department.filter(it => it.id == row._department)[0].name
      },
      'Crop Type': {
        label: intl.formatMessage(messages.crop_type),
        extractor: row => items => items.cropType.filter(it => it.id == row._cropType)[0][labelField]
      }
    }

  }
}

export default configurator
