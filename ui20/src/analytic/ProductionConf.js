import React from 'react'
import {defineMessages} from 'react-intl'
import messages from '../translations/messages'


export const configurator = (int) => {


  return {

    "pivottable": {
      "rows": [int.formatMessage(messages.region)],
      "cols": [int.formatMessage(messages.crop_type)],
      "vals": [int.formatMessage(messages.production)],
      "hiddenAttributes": [
        "_region", "_cropType"
      ],
      "hiddenFromAggregators": [
        int.formatMessage(messages.year),
        int.formatMessage(messages.crop_type),
        int.formatMessage(messages.region),
        int.formatMessage(messages.region_code)
      ],
      "hiddenFromDragDrop": [
        int.formatMessage(messages.area),
        int.formatMessage(messages.production),
        int.formatMessage(messages.yield)
      ],
      "aggregatorName":   int.formatMessage(messages.sum),
      "rendererName": "Table"
    },

    "fields": {
      "surface": int.formatMessage(messages.area),
      "production": int.formatMessage(messages.production),
      "year": int.formatMessage(messages.year),
      "yield": int.formatMessage(messages.yield),
      "region": "_region",
      "cropType": "_cropType"
    },

    "extraFields": {
      "Region Code": {
        label: int.formatMessage(messages.region_code),
        extractor: row => items => items['region'].filter(it => it.id == row._region)[0].code
      },
      "Region": {
        label: int.formatMessage(messages.region),
        extractor: row => items => items['region'].filter(it => it.id == row._region)[0].name
      },
      "Crop Type": {
        label: int.formatMessage(messages.crop_type),
        extractor: row => items => items['cropType'].filter(it => it.id == row._cropType)[0].label
      }
    }

  }
}

export default configurator
