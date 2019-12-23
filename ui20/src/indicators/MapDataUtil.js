import messages from '../translations/messages'



export const getPovertyMapData = (json, data = [], intl) => {
  if (data.length > 0) {

    const tr_region = intl.formatMessage(messages.region)
    const keys = Array.from(new Set(data.map(d => d.region)))
    const years = Array.from(new Set(data.map(r => r.year)))
    const maxYear = years.pop()

    const regions = new Set(data.map(r => r.region))
    const mostRecent = data.filter(d => d.year == maxYear)

    regions.forEach(r => {
      let feature = null
      const rFiltered = json.features.filter(f => f.properties.NAME_1 == r)

      if (rFiltered.length > 0) {
        feature = rFiltered[0]
      }

      if (!feature) {
        console.log('No feature with region name ->' + r)
      } else {
        mostRecent.filter(m => m.region == r).forEach(p => {
          feature.properties[p.povertyLevel] = p.percentage * 100
        })
      }
    })
  }


  return json;
}
