import React from 'react'
import {
  defineMessages
} from 'react-intl'
import messages from '../translations/messages'

export const configurator = (intl) => {
  const labelField = (intl.locale == 'fr') ? 'labelFr' : 'label'

  const market = intl.formatMessage(messages.market)

  const whole_sale_buy_price = intl.formatMessage(messages.whole_sale_buy_price)

  const quantity = intl.formatMessage(messages.quantity)
  const selling_price = intl.formatMessage(messages.selling_price)
  const retail_buying_price = intl.formatMessage(messages.retail_buying_price)
  const date = intl.formatMessage(messages.date)
  const year = intl.formatMessage(messages.year)
  const crop_type = intl.formatMessage(messages.crop_type)

  return {
    pivottable: {
      rows: [market],
      cols: [year],
      vals: [whole_sale_buy_price],
      hiddenAttributes: ['_market', '_cropType'],
      hiddenFromAggregators: [date, year, market, crop_type],
      hiddenFromDragDrop: [quantity, selling_price, retail_buying_price, whole_sale_buy_price],
      aggregatorName: intl.formatMessage(messages.average),
      rendererName: 'Table'
    },

    fields: {
      market: '_market',
      cropType: '_cropType',
      date: date,
      year: year,
      quantity: quantity,
      sellPrice: selling_price,
      detailBuyPrice: retail_buying_price,
      wholesaleBuyPrice: whole_sale_buy_price

    },

    extraFields: {
      market: {
        label: market,
        extractor: row => items => items.market.filter(it => it.id == row._market)[0].name
      },
      crop_type: {
        label: crop_type,
        extractor: row => items => items.cropType.filter(it => it.id == row._cropType)[0][labelField]
      }
    }
  }
}

export default configurator
