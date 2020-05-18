import CommonConfig from "../../entities/rainfall/CommonConfig"

export const yearsToOptions = (years) => years.sort().reverse().map(y => ({
  key: y,
  text: y,
  value: y
}))

export const postIdsToOptions = (postIds, commonConfig: CommonConfig) => postIds.map(id => {
  const post = commonConfig.posts.get(id)
  const dep = post.department
  return ({
    key: id,
    text: `${post.label} (${dep.name})`,
    value: id
  })}).sort((p1, p2) => p1.text.localeCompare(p2.text))

