/* eslint-disable eqeqeq,no-unused-vars,array-callback-return */
import messages from '../../../translations/messages'

const localeSort = (a, b, lan) => {
  if (typeof (a.label) === 'number') {
    return a.label - b.label
  }

  if (lan === 'en') {
    return a.label.localeCompare(b.label, 'en', {
      sensitivity: 'base'
    })
  }
  if (lan === 'fr') {
    return a.labelFr.localeCompare(b.labelFr, 'en', {
      sensitivity: 'base'
    })
  }
}

export const items2options = (items, intl) => {
  return items && items.length > 0 ? items.sort((c1, c2) => localeSort(c1, c2, intl.locale)).map(r => {
    let text = (intl.locale === 'fr') ? r.labelFr : r.label

    if (typeof (r.label) === 'number') {
      text = r.label
    }

    return {
      key: r.id,
      text: text,
      value: r.id
    }
  }) : []
}

export const getPovertyRegionalYearly = (data = [], intl) => {
  let fields = ['region', 'povertyLevel']
  if (intl.locale === 'fr') {
    fields = ['regionFr', 'povertyLevelFr']
  }

  const tr_region = intl.formatMessage(messages.region)

  const keys = Array.from(new Set(data.map(d => d.year)))
  const years = new Set(data.map(r => r.year))
  const regions = new Set(data.map(r => r[fields[0]]))
  const barData = []

  regions.forEach(r => {
    const record = {}

    record[tr_region] = r

    years.forEach(y => {
      const toReduce = data.filter(d => d.year === y && d[fields[0]] === r && d.povertyLevel != 'Not poor')
      const value = toReduce.reduce((a, b) => a + b.percentage, 0)
      record[y] = value * 100
    })

    barData.push(record)
  })

  return {
    data: barData,
    keys: keys.sort(),
    groupMode: 'grouped',
    indexBy: tr_region,
    colors: {
      scheme: 'red_yellow_blue'
    }
  }
}

export const getPovertyRegionalStackedByPovertyLevel = (data, intl) => {
  let fields = ['region', 'povertyLevel']
  if (intl.locale === 'fr') {
    fields = ['regionFr', 'povertyLevelFr']
  }

  const tr_region = intl.formatMessage(messages.region)

  const keys = Array.from(new Set(data.map(d => d[fields[1]])))
  const years = Array.from(new Set(data.map(r => r.year)))
  const maxYear = years.pop()
  const regions = new Set(data.map(r => r[fields[0]]))
  const mostRecent = data.filter(d => d.year === maxYear)
  const barData = []

  regions.forEach(r => {
    const record = {}
    record[tr_region] = r

    mostRecent.filter(m => m[fields[0]] === r).forEach(p => {
      record[p[fields[1]]] = p.percentage * 100
    })
    barData.push(record)
  })

  return {
    data: barData,
    keys: keys.sort(),
    indexBy: tr_region,
    groupMode: 'stacked',
    colors: ['#f1e15b', '#e8a838', ' #f47561']
  }
}

export const getPovertyTimeLine = (data) => {
  const regions = Array.from(new Set(data.map(d => d.region))).sort((a, b) => b.localeCompare(a))

  const years = Array.from(new Set(data.map(d => d.year)))
  const lineData = regions.map(r => {
    const record = {
      id: r,
      data: []
    }

    years.forEach((y) => {
      const value = data.filter(d => d.region === r && d.povertyLevel != 'Not poor' && d.year === y).reduce((a, b) => a + (b.percentage * 100), 0)

      record.data.push({
        x: y,
        y: value
      })
    })

    return record
  })

  return {
    data: lineData
  }
}

export const getAverageProductionLossData = (data = [], valueField, intl) => {
  let fields = ['cropType', 'lossType']

  if (intl.locale === 'fr') {
    fields = ['cropTypeFr', 'lossTypeFr']
  }

  const crops = Array.from(new Set(data.map(d => d[fields[0]])))
  const types = Array.from(new Set(data.map(r => r[fields[1]])))
  const barData = []
  const years = new Set(data.map(r => r.year))
  const maxYear = Array.from(years).sort()[years.size - 1]

  const mostRecent = data.filter(d => d.year === maxYear)

  crops.sort((a, b) => a.localeCompare(b))
  crops.forEach(g => {
    const r = {
      Crop: g
    }
    mostRecent.filter(d => d[fields[0]] === g)
      .forEach(gd => {
        const field = fields[1]
        r[gd[field]] = gd[valueField]
      })

    barData.push(r)
  })

  return {
    data: barData,
    keys: types.sort((a, b) => b.localeCompare(a)),
    indexBy: 'Crop',
    groupMode: 'stacked'
  }
}

export const getWomenDistributionByGroup = (data = [], intl, groupMode = 'grouped', sortFunc) => {
  let fields = ['groupType', 'gender']
  if (intl.locale === 'fr') {
    fields = ['groupTypeFr', 'genderFr']
  }

  const tr_age = intl.formatMessage(messages.age)

  const keys = Array.from(new Set(data.map(d => d[fields[0]])))
  const groups = Array.from(new Set(data.map(r => r[fields[0]])))
  const genders = Array.from(new Set(data.map(r => r[fields[1]])))

  const barData = []
  const years = new Set(data.map(r => r.year))
  const maxYear = Array.from(years).sort()[years.size - 1]

  const mostRecent = data.filter(d => d.year === maxYear)

  if (sortFunc) {
    groups.sort(sortFunc)
  } else {
    groups.sort()
  }

  groups.forEach(g => {
    const r = {}
    r[tr_age] = g

    const value = mostRecent.filter(d => d[fields[0]] === g).forEach(gd => {
      r[gd[fields[1]]] = gd.percentage
    })
    barData.push(r)
  })

  return {
    data: barData,
    keys: genders,
    indexBy: 'Age',
    groupMode: groupMode
  }
}

export const getWomenHistoricalDistribution = (data = [], intl, sortFunc) => {
  let fields = ['groupType', 'gender']
  if (intl.locale === 'fr') {
    fields = ['groupTypeFr', 'genderFr']
  }
  const keys = Array.from(new Set(data.map(d => d[fields[0]])))
  const groups = Array.from(new Set(data.map(r => r[fields[0]])))
  const genders = Array.from(new Set(data.map(r => r[fields[1]])))
  const years = Array.from(new Set(data.map(r => r.year)))
  const filteredData = data.filter(d => d.gender === 'Female')
  const lineData = []

  if (sortFunc) {
    groups.sort(sortFunc)
  } else {
    groups.sort()
  }

  groups.forEach((g) => {
    const r2 = {
      id: g,
      gender: 'Female',
      data: []
    }

    years.sort((y1, y2) => y1 - y2).forEach((year) => {
      const filtered = filteredData.filter(d => d.year === year && d[fields[0]] === g && d.gender === 'Female')
      if (filtered.length > 0) {
        const value = filtered.reduce((a, b) => {
          return a + b.percentage
        }, 0)
        r2.data.push({
          x: year,
          y: value
        })
      }
    })
    if (r2.data.length > 0) {
      lineData.push(r2)
    }
  })
  const sorted = lineData.sort((a, b) => a.gender.localeCompare(b.gender))
  return {
    data: sorted
  }
}

export const getAOItotalBudget = (data, intl) => {
  let fields = ['indexType']
  if (intl.locale === 'fr') {
    fields = ['indexTypeFr']
  }
  const barData = []
  let years = []
  let keys = []

  if (data) {
    data.sort((a, b) => a.year - b.year)
    years = [...new Set(data.map(d => d.year))]
    keys = [...new Set(data.map(d => d[fields[0]]))]

    years.map(y => {
      const row = {
        Year: y
      }
      const yearlyData = data.filter(d => d.year === y).forEach(r => {
        row[r[fields[0]]] = r.budgetedExpenditures
      })
      barData.push(row)
    })
  }

  if (barData.length > 0) {

  }

  return {
    data: barData,
    keys: keys.sort((a, b) => b.localeCompare(a)),
    groupMode: 'stacked',
    indexBy: 'Year'
  }
}

export const getAOIsubsidies = (data, intl) => {
  let fields = ['indexType']
  if (intl.locale === 'fr') {
    fields = ['indexTypeFr']
  }

  const barData = []
  let years = []
  let keys = []

  if (data) {
    data.sort((a, b) => a.year - b.year)
    years = [...new Set(data.map(d => d.year))]
    keys = [...new Set(data.map(d => d[fields[0]]))]

    years.map(y => {
      const row = {
        Year: y
      }
      const yearlyData = data.filter(d => d.year === y).forEach(r => {
        row[r[fields[0]]] = r.subsidies
      })
      barData.push(row)
    })
  }

  return {
    data: barData,
    keys: keys.sort((a, b) => b.localeCompare(a)),
    groupMode: 'stacked',
    indexBy: 'Year'
  }
}