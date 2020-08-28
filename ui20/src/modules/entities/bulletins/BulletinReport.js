import BulletinConfig from "./BulletinConfig"
import BulletinData from "./BulletinData"
import BulletinFilter from "./BulletinFilter"
import GTPLocation from "./GTPLocation"

const BulletinReport : {
  config: BulletinConfig,
  data: BulletinData,
  filter: BulletinFilter,
} = {
  config: null,
  data: null,
  filter: BulletinFilter,
}

export const fromApi = ({data, config, filter} = {data: {}, config: {}, filter: {}}) => {
  const {locations} = config
  BulletinReport.data = dataFromApi(data)
  BulletinReport.config = new BulletinConfig(Array.from(BulletinReport.data.gtpMaterials.keys()),
    (locations || []).map(l => new GTPLocation(l)).sort(GTPLocation.localeCompare))
  BulletinReport.filter.years = BulletinReport.config.years || []
  BulletinReport.filter.locationId = filter.locationId
  return BulletinReport
}

export const dataFromApi = (data) => new BulletinData(data)

export default BulletinReport
