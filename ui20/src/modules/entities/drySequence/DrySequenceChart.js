import DrySequenceData from "./DrySequenceData"
import DrySequenceFilter, {drySequenceFilterFromAPI} from "./DrySequenceFilter"

const DrySequenceChart: {
  filter: DrySequenceFilter,
  data: DrySequenceData,
} = {
}

export const drySequenceChartFromApi: DrySequenceChart = ({filter, data}) => {
  DrySequenceChart.filter = drySequenceFilterFromAPI(filter)
  DrySequenceChart.data = new DrySequenceData(data)
  return DrySequenceChart
}

export default DrySequenceChart
