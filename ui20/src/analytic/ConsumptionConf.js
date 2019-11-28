import React from 'react'
import {defineMessages} from 'react-intl'
import messages from '../translations/messages'

export const configurator = (int) => {

  return {

    "pivottable": {
      "rows": [int.formatMessage(messages.department)],
      "cols": [int.formatMessage(messages.crop_type)],
      "vals": [int.formatMessage(messages.weekly_consumption)],
      "hiddenAttributes": [
        "_cropType", "_cropSubType", "_department"
      ],

      "hiddenFromAggregators": [
        int.formatMessage(messages.department),
        int.formatMessage(messages.year),
        int.formatMessage(messages.crop_type),
        int.formatMessage(messages.region)
      ],
      "hiddenFromDragDrop": [
        int.formatMessage(messages.daily_consumption),
        int.formatMessage(messages.house_hold_size),
        int.formatMessage(messages.weekly_consumption)
      ],
      "aggregatorName":  int.formatMessage(messages.sum),
      "rendererName": "Table"
    },

    "fields": {
      "department": "_department",
      "year": int.formatMessage(messages.year),
      "cropType": "_cropType",
      "cropSubType": "_cropSubType",
      "householdSize": int.formatMessage(messages.house_hold_size),
      "dailyConsumption": int.formatMessage(messages.daily_consumption),
      "weeklyConsumption": int.formatMessage(messages.weekly_consumption)
    },

    "extraFields": {
      "Crop Type": {
        label: int.formatMessage(messages.crop_type),
        extractor: row => items => items['cropType'].filter(it => it.id == row._cropType)[0].label
      },
      "Department": {
        label: int.formatMessage(messages.department),
        extractor: row => items => items['department'].filter(it => it.id == row._department)[0].name
      },
      "Region": {
        label: int.formatMessage(messages.region),
        extractor: row => items => items['department'].filter(it => it.id == row._department)[0].region.name
      }
    }

  }

}
export default configurator
