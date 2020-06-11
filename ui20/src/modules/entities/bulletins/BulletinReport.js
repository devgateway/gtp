import BulletinConfig from "./BulletinConfig"
import BulletinData from "./BulletinData"
import BulletinFilter from "./BulletinFilter"

const BulletinReport : {
  config: BulletinConfig,
  data: BulletinData,
  filter: BulletinFilter,
} = {
  config: null,
  data: null,
  filter: BulletinFilter,
}

export const fromApi = (report) => {
  BulletinReport.data = new BulletinData(report)
  BulletinReport.config = new BulletinConfig(Array.from(BulletinReport.data.gtpMaterials.keys()))
  BulletinReport.filter.years = BulletinReport.config.years
  return BulletinReport
}

export default BulletinReport
