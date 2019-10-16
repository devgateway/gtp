const production = {
  config: {
    "pivottable": {
      "rows": ["Region"],
      "cols": ["Crop Type"],
      "vals": ["Production"],
      "hiddenAttributes": ["_region", "_cropType"],
      "hiddenFromAggregators": ["Year", "Crop Type", "Region", "Region Code"],
      "hiddenFromDragDrop": ["_region", "_cropType", "Area", "Production", "Yield"],
      "aggregatorName": "Sum",
      "rendererName": "Table",
      "aggregatorNames": ["Average", "Count", "Sum"],
    },

    "fields": {
      "surface": "Area",
      "production": "Production",
      "year": "Year",
      "yield": "Yield",
      "region": "_region",
      "cropType": "_cropType"
    },

    "extraFields": {
      "Region Code": {
        extractor: row => items => items['region'].filter(it => it.id == row._region)[0].code
      },
      "Region": {
        extractor: row => items => items['region'].filter(it => it.id == row._region)[0].name
      },
      "Crop Type": {
        extractor: row => items => items['cropType'].filter(it => it.id == row._cropType)[0].label
      }
    },

  }
}

export default production
