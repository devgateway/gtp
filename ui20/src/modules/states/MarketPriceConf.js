import React from 'react'
import {
  defineMessages
} from 'react-intl'
import messages from '../../translations/messages'

export const configurator = (int) => {

  const market = int.formatMessage(messages.market);

  const whole_sale_buy_price = int.formatMessage(messages.whole_sale_buy_price);

  const quantity = int.formatMessage(messages.quantity);
  const selling_price = int.formatMessage(messages.selling_price);
  const retail_buying_price = int.formatMessage(messages.retail_buying_price);
  const date = int.formatMessage(messages.date);
  const year = int.formatMessage(messages.year);
  const crop_type = int.formatMessage(messages.crop_type);


  return {
    "pivottable": {
      "rows": [],
      "cols": [crop_type],
      "vals": [whole_sale_buy_price],
      "hiddenAttributes": ["_market", "_cropType"],
      "hiddenFromAggregators": [],
      "hiddenFromDragDrop": [quantity, selling_price,retail_buying_price, whole_sale_buy_price],
      "aggregatorName": int.formatMessage(messages.average),
      "rendererName": "Table Heatmap"
    },

    "fields": {
      "market": "_market",
      "cropType": "_cropType",
      "date": date,
      "year": year,
      "quantity": quantity,
      "sellPrice": selling_price,
      "detailBuyPrice": retail_buying_price,
      "wholesaleBuyPrice": whole_sale_buy_price

    },

    "extraFields": {
      "market": {
        label:market,
        extractor: row => items => items['market'].filter(it => it.id == row._market)[0].name
      },
      "crop_type": {
        label:crop_type,
        extractor: row => items => items['cropType'].filter(it => it.id == row._cropType)[0].label
      },
    },
  }
}

export default configurator
