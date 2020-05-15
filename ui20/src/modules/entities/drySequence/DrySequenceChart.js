import DrySequenceData from "./DrySequenceData"
import DrySequenceFilter, {drySequenceFilterFromAPI} from "./DrySequenceFilter"
import DrySequenceSettings from "./DrySequenceSettings"

const DrySequenceChart: {
  filter: DrySequenceFilter,
  settings: DrySequenceSettings,
  data: DrySequenceData,
} = {
}

export const drySequenceChartFromApi: DrySequenceChart = ({filter, data}) => {
  DrySequenceChart.filter = drySequenceFilterFromAPI(filter)
  DrySequenceChart.settings = DrySequenceSettings
  DrySequenceChart.data = new DrySequenceData(data)
  return DrySequenceChart
}

export default DrySequenceChart
