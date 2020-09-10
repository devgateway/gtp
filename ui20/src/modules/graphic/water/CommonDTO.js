import CommonConfig from "../../entities/config/CommonConfig"
import WaterConfig from "../../entities/config/WaterConfig"
import RiverStation from "../../entities/river/RiverStation"


export const postIdsToOptions = (postIds, waterConfig: WaterConfig, commonConfig: CommonConfig) => postIds.map(id => {
  const post = waterConfig.posts.get(id)
  const dep = commonConfig.departments.get(post.departmentId)
  return ({
    key: id,
    text: `${post.label} (${dep.name})`,
    value: id
  })}).sort(alphabeticalOptionsSort)

export const hydrologicalYearToString = (year: number) => {
  const nextYearPart = (year + 1) % 100
  const nextYearStr = nextYearPart < 10 ? `0${nextYearPart}` : nextYearPart
  return `${year}-${nextYearStr}`
}

export const yearsToHydrologicalYearsOptions = (years) => years.sort().reverse().map(y => ({
  key: y,
  text: hydrologicalYearToString(y),
  value: y
}))

export const riverStationToOptions = (riverStations: Array<RiverStation>, intl) => {
  return riverStations.map(rs => ({
    key: rs.id,
    text: intl.formatMessage(
      { id: "indicators.filters.river_station.option" },
      { river: rs.riverName, station: rs.name }
    ),
    value: rs.id
  })).sort(alphabeticalOptionsSort)
}

const alphabeticalOptionsSort = (o1, o2) => o1.text.localeCompare(o2.text)
