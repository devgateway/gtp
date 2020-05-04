const API_ROOT = document.location.href.indexOf('localhost') > -1 ? 'http://localhost:8080' : document.location.origin
const dumpUrlBuilder = name => `/data/${name}/dump`
const itemsURLBuilder = (category, path) => `/data/${path}/${category}`

const URL_INDICATORS = API_ROOT + '/data/indicator'
const URL_POVERTY = API_ROOT + '/data/poverty/summary'
const URL_AGRICULTURAL_POPULATION = API_ROOT + '/data/agriculturalWomen/summary/byAgeGroup'
const URL_AGRICULTURAL_DISTRIBUTION = API_ROOT + '/data/agriculturalWomen/summary/byMethodOfEnforcement'
const URL_FOOD_LOSS = API_ROOT + '/data/foodLoss/summary'
const URL_AOI_SUBSIDIES = API_ROOT + '/data/agOrientation/summary/subsidies'
const URL_AOI_TOTAL_BUDGET = API_ROOT + '/data/agOrientation/summary/totalBudget'
const URL_RAPID_LINKS = API_ROOT + '/data/rapidLink/top5'
const URL_DATA_SETS = API_ROOT + '/data/dataset/all'
const URL_SOURCES = API_ROOT + '/data/datasources/all'

const API_PARTNERS_URL = API_ROOT + '/data/partner/all'
const URL_API_INITIATIVE_TYPES = API_ROOT + '/data/filter/contentType'
const URL_API_INITIATIVE_ITEMS = API_ROOT + '/data/agriculturalContent/type'
const URL_DATA_SETS_YEARS = API_ROOT + '/data/filter/dataset/years'
const API_NATIONAL_URL = API_ROOT + '/data/nationalIndicator/all'
const URL_API_METADATA = API_ROOT + '/data/indicatorMetadata/all'

const API_GIS_URL_REGION = API_ROOT + '/data/gisIndicator/region/all'

const API_GIS_URL_DEPARTMENT = API_ROOT + '/data/gisIndicator/department/all'

const xlsExportURLBuilder = (what) => {
  let subfix = ''
  // eslint-disable-next-line default-case
  switch (what) {
    case 'POVERTY':
      subfix = 'poverty'
      break
    case 'WOMEN':
      subfix = 'women'
      break
    case 'FOOD':
      subfix = 'foodLoss'
      break
    case 'AOI':
      subfix = 'aoi'
      break
  }

  return `${API_ROOT}/data/indicator/excelExport/${subfix}`
}

const csvExportURLBuilder = (what) => {
  let subfix = ''
  // eslint-disable-next-line default-case
  switch (what) {
    case 'POVERTY':
      subfix = 'poverty'
      break
    case 'WOMEN':
      subfix = 'agriculturalWomen'
      break
    case 'FOOD':
      subfix = 'foodLoss'
      break
    case 'AOI':
      subfix = 'agOrientation'
      break
  }

  return `${API_ROOT}/data/${subfix}/summary/csv`
}

// eslint-disable-next-line no-unused-vars
function queryParams (params) {
  return Object.keys(params)
    .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&')
}

const post = (url, params, isBlob) => {
  return new Promise((resolve, reject) => {
    fetch(url, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(params)
    })
      .then(
        function (response) {
          if (response.status !== 200) {
            reject(response)
          }
          if (isBlob) {
            resolve(response.blob())
          }
          response.json().then(function (data) {
            resolve(data)
          }).catch(() => resolve(response.status))
        }
      )
      .catch(function (err) {
        reject(err)
      })
  })
}
const get = (url) => {
  return new Promise((resolve, reject) => {
    fetch(url)
      .then(
        function (response) {
          if (response.status !== 200) {
            reject(response)
          }
          response.json().then(function (data) {
            resolve(data)
          })
        }
      )
      .catch(function (err) {
        reject(err)
      })
  })
}

export const getRapidLinks = () => {
  return get(URL_RAPID_LINKS)
}

export const getDataSet = (name) => {
  return post(API_ROOT + dumpUrlBuilder(name))
}

export const getItems = (category, path, params) => {
  return post(API_ROOT + itemsURLBuilder(category, path), params.global)
}

export const getGISRegionData = (params) => {
  return post(API_GIS_URL_REGION, params)
}

export const getGISDepartmentData = (params) => {
  return post(API_GIS_URL_DEPARTMENT, params)
}

export const getNationalIndicators = (params) => {
  return post(API_NATIONAL_URL, params)
}

export const getPartners = (locale) => {
  return new Promise((resolve, reject) => {
    return post(API_PARTNERS_URL, {
      lang: locale
    }).then((partners) => {
      const groups = Array.from(new Set(partners.map(p => p.groupType))).map(g => {
        // eslint-disable-next-line eqeqeq
        const pps = partners.filter(p => p.groupType == g)
        return {
          name: g,
          id: pps[0].groupId,
          partners: pps
        }
      })
      resolve({
        partners,
        groups
      })
    }).catch(error => {
      reject(error)
    })
  })
}

export const getIndicatorsMetadata = (lang) => {
  return new Promise((resolve, reject) => {
    post(URL_API_METADATA, {}).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const loadPovertyChartData = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_POVERTY, {
      ...params.global,
      ...params.poverty
    }).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const exportIndicators = (what, format, lang, params, options) => {
  debugger

  return new Promise((resolve, reject) => {
    let url = ''
    // eslint-disable-next-line eqeqeq
    const fileName = (what == 'ALL' ? 'indicator' : what) + (format == 'XLS' ? '.xlsx' : '.csv')

    // eslint-disable-next-line eqeqeq
    if (format == 'XLS') {
      url = xlsExportURLBuilder(what)
      // eslint-disable-next-line eqeqeq
    } else if (format == 'CSV') {
      url = csvExportURLBuilder(what)
    }

    const filters = {
      ...params.global
    }

    switch (what) {
      case 'POVERTY':
        Object.assign(filters, params.poverty)
        break

      case 'WOMEN':

        const womenFilters = {}
        Object.keys(options).filter(o => options[o]).forEach(k => {
          womenFilters[k] = params.women[k]
          debugger
        })
        Object.assign(filters, womenFilters)
        break

      case 'FOOD':
        Object.assign(filters, params.food)
        break

      case 'AOI':
        Object.assign(filters, params.aoi)
        break
      default:
    }

    post(url, {
      ...filters,
      lang
    }, true).then(blob => {
      var url = window.URL.createObjectURL(blob)
      var a = document.createElement('a')
      a.href = url
      a.download = fileName.toLowerCase()
      document.body.appendChild(a) // we need to append the element to the dom -> otherwise it will not work in firefox
      a.click()
      a.remove() // afterwards we remove the element again
    }).catch(error => {
      reject(error)
    })
  })
}

export const getAgricuturalDistribution = (params) => {
  return new Promise((resolve, reject) => {
    const specificFilters = params.women
    specificFilters.ageGroup = []

    post(URL_AGRICULTURAL_DISTRIBUTION, {
      ...params.global,
      ...specificFilters
    }).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getAgricuturalPopulation = (params) => {
  return new Promise((resolve, reject) => {
    const specificFilters = params.women
    specificFilters.methodOfEnforcement = []
    post(URL_AGRICULTURAL_POPULATION, {
      ...params.global,
      ...specificFilters
    }).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getFoodLoss = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_FOOD_LOSS, {
      ...params.global,
      ...params.food
    }).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getAOIsubsidies = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_AOI_SUBSIDIES, {
      ...params.global,
      ...(params.aoi ? params.aoi.subsidies : {})
    }).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getAOItotalBudget = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_AOI_TOTAL_BUDGET, {
      ...params.global,
      ...(params.aoi ? params.aoi.budget : {})
    }).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getGlobalIndicators = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_INDICATORS, params.global).then((data) => {
      const mockData = [{
        value: data.poverty.data ? data.poverty.data.value : null,
        image: '/sdg/1.svg',
        text: 'Proportion of population below the international poverty line',
        key: 'indicator.global.population.short',
        year: data.poverty.data ? data.poverty.data.year : null,
        style: 'percent'
      }, {
        value: data.agriculturalWomen.data ? data.agriculturalWomen.data.value : null,
        image: '/sdg/5.svg',
        text: 'Women in the Agricultural sector',
        key: 'indicator.global.women.short',
        year: data.agriculturalWomen.data ? data.agriculturalWomen.data.year : null,
        style: 'percent'
      },

      {
        value: data.foodLoss.data ? data.foodLoss.data.value : null,
        image: '/sdg/food_loss.svg',
        text: 'Post-Harvest Loss',
        key: 'indicator.global.food.short',
        year: data.agriculturalWomen.data ? data.agriculturalWomen.data.year : null,
        style: 'percent'
      },

      {
        value: data.agOrientation.data ? data.agOrientation.data.value : null,
        image: '/sdg/12.svg',
        text: 'Agriculture orientation index for government expenditures',
        key: 'indicator.global.aoi.short',
        year: data.agOrientation.data ? data.agOrientation.data.year : null,
        style: 'percent'
      }

      ]

      resolve(mockData)
    }).catch(error => {
      console.log('ERROR')
      reject(error)
    })
  })
}

export const getDefaultIndicatorFilters = () => {
  return new Promise((resolve, reject) => {
    resolve({
      year: [],
      region: [],
      crop: []
    })
  })
}

export const getDatasets = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_DATA_SETS, params).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getDatasetsYears = () => {
  return new Promise((resolve, reject) => {
    get(URL_DATA_SETS_YEARS).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getSources = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_SOURCES, params).then((data) => {
      resolve(data)
    }).catch(error => {
      reject(error)
    })
  })
}

export const getInitiativeTypes = () => {
  return get(URL_API_INITIATIVE_TYPES)
}

export const getInitiativeItems = (id, locale, page) => {
  return new Promise((resolve, reject) => {
    return post(`${URL_API_INITIATIVE_ITEMS}/${id}`, {
      lang: locale,
      pageNumber: page
    }).then((data) => {
      resolve({
        data,
        id
      })
    }).catch(error => {
      reject(error)
    })
  })
}

// CORS NOT SUPPORTED ON MAILCHIMP this mehtod can't beused
export const subscribeToNewsLetter = (email) => {
  return new Promise((resolve, reject) => {
    const AUDIENCE_ID = '35d5eec81f' // https://mailchimp.com/en/help/find-audience-id/
    const API_KEY = '20a617cf01c77ab0b149fe438e74b8f0-us19'

    const URL_PREFIX = API_KEY.split('-')[1]
    const KEY = API_KEY.split('-')[0]

    var subscriber = { email_address: email, status: 'subscribed' }

    fetch(`https://${URL_PREFIX}.api.mailchimp.com/3.0/lists/${AUDIENCE_ID}/members`, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: `Basic ${KEY}`
      },
      method: 'POST',
      mode: 'cors',
      cache: 'default',
      body: JSON.stringify(subscriber)
    }).then(response => {

    }).catch(error => {

    })
  })
}



export const getAnaliticUserCode = ()=>{
   var PROD = !(document.location.href.indexOf('dgstg') > -1 || document.location.href.indexOf('localhost') > -1 || document.location.href.indexOf("127.0.0.1") > -1)
   return  (PROD === true) ? 'UA-162751032-1' : 'UA-162929851-1';

}