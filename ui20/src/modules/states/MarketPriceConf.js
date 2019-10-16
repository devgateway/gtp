const marketPrice = {
  config: {
    "pivottable": {
      "rows": ["Market"],
      "cols": ["Crop Type"],
      "vals": ["Whole Sale Buy Price"],
      "hiddenAttributes": ["_market", "_cropType"],
      "hiddenFromAggregators": [],
      "hiddenFromDragDrop": ["Quantity","Selling Price","Retail Buying Price","Whole Sale Buy Price"],
      "aggregatorName": "Average",
      "rendererName": "Table Heatmap"
    },

    "fields": {
      "market": "_market",
      "cropType": "_cropType",
      "date": "Date",
      "year": "Year",
      "quantity": "Quantity",
      "sellPrice": "Selling Price",
      "detailBuyPrice": "Retail Buying Price",
      "wholesaleBuyPrice": "Whole Sale Buy Price"

    },

    "extraFields": {
      "Market": {
        extractor: row => items => items['market'].filter(it => it.id == row._market)[0].name
      },
      "Crop Type": {
        extractor: row => items => items['cropType'].filter(it => it.id == row._cropType)[0].label
      },
    },


  }
}

export default marketPrice
