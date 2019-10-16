const consumption = {
  config: {
    "pivottable": {
      "rows": ["Department"],
      "cols": ["Crop Type"],
      "vals": ["Weekly Consumption"],
      "hiddenAttributes":["_cropType","_cropSubType","_department"],
      "hiddenFromAggregators": ["Department","Year","Crop Type","Region"],
      "hiddenFromDragDrop": ["Daily Consumption","House Hold Size","Weekly Consumption"],
      "aggregatorName": "Sum",
      "rendererName": "Table"
    },

    "fields": {
      "department": "_department",
      "year": "Year",
      "cropType": "_cropType",
      "cropSubType": "_cropSubType",
      "householdSize": "House Hold Size",
      "dailyConsumption": "Daily Consumption",
      "weeklyConsumption": "Weekly Consumption"
    },

    "extraFields": {
      "Crop Type": {
        extractor: row => items => items['cropType'].filter(it => it.id == row._cropType)[0].label
      },
      "Department": {
        extractor: row => items => items['department'].filter(it => it.id == row._department)[0].name
      },
      "Region": {
        extractor: row => items => items['department'].filter(it => it.id == row._department)[0].region.name
      }
    },


  }
}

export default consumption
