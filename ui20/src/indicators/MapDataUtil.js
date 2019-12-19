import messages from '../translations/messages'



export const getPovertyMapData = (json, data = [], intl) => {
  if (data.length > 0){

    let fields = ['region', 'povertyLevel']
    if (intl.locale == 'fr') {
      fields = ['regionFr', 'povertyLevelFr']
    }

    const tr_region = intl.formatMessage(messages.region)



    const keys = Array.from(new Set(data.map(d => d[fields[1]])))
    const years = Array.from(new Set(data.map(r => r.year)))
    const maxYear = years.pop()
    const regions = new Set(data.map(r => r[fields[0]]))
    const mostRecent = data.filter(d => d.year == maxYear)
    let mapData = []


    regions.forEach(r => {
      const record = {}
      record[tr_region] = r

      mostRecent.filter(m => m[fields[0]] == r).forEach(p => {
        record[p[fields[1]]] = p.percentage * 100
      })
      mapData.push(record)
    })

    debugger;

  }

  return json;
}
