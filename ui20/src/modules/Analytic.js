import {
  getDataSet
} from '../api'

import Immutable from 'immutable'


const initialState = Immutable.fromJS({
  production: {
    config: {
      "pivottable": {
        "rows": ["Region"],
        "cols": ["Crop Type"],
        "vals": ["Production"],
        "hiddenFromAggregators": [ "Year", "Crop Type", "Region", "Region Code"],
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
        "regionCode": {name:"Region Code", extractor:(state)=>{
          debugger;
          return this.code
        }},
        "regionName": {name:"Region", extractor:(state)=>{
          debugger;
        }},
        "cropTypeName": {name:"Crop Type", extractor:(state)=>{}}
      },


      "renderers": {
        "Table": "Table",
        "Heatmap": "Heatmap",
        "Row Heatmap": "Row Heatmap",
        "Col Heatmap": "Col Heatmap",
        "Bar Chart": "Bar Chart",
        "Stacked Bar Chart": "Stacked Bar Chart",
        "Horizontal Bar Chart": "Horizontal Bar Chart",
        "Horizontal Stacked Bar Chart": "Horizontal Stacked Bar Chart",
        "Area Chart": "Area Chart",
        "Line Chart": "Line Chart",
        "Multiple Pie Chart": "Multiple Pie Chart",
        "Scatter Chart": "Scatter Chart"
      },
      "mthNames": ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
      "dayNames": ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]
    }
  }
})



export const loadDataSet = (name) => dispatch => {
  getDataSet(name).then(data => {

    dispatch({
      type: 'LOAD_DATASET_DATA_OK',
      name,
      data
    })
  }).catch(error => {
    dispatch({
      type: 'LOAD_DATASET_DATA_ERROR',
      error
    })
  })
}


export const mapFields = (data, fields, extraFields, dataItems) => {
  return data.map(r => {
    let nr = {}
    console.log(extraFields)
    Object.keys(r).forEach(k => {
      const name = fields[k];
      nr[name] = r[k]
    })
    return nr
  });
}


export default (state = initialState, action) => {
  switch (action.type) {
    case 'LOAD_DATASET_DATA_OK':
      debugger;

      return state.setIn([action.name, 'data'], Immutable.List(action.data))
    case 'LOAD_DATASET_DATA_ERROR':
      return state
    default:
      return state
  }
}
