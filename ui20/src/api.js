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

const xlsExportURLBuilder = (what) => {
  let subfix = ''
  switch (what) {
    case 'POVERTY':
      subfix = 'poverty'
      break;
    case 'WOMEN':
      subfix = 'women'
      break;
    case 'FOOD':
      subfix = 'foodLoss'
      break;
    case 'AOI':
      subfix = 'aoi'
      break;
  }

  return `${API_ROOT}/data/indicator/excelExport/${subfix}`
}


const csvExportURLBuilder = (what) => {
  let subfix = ''
  switch (what) {
    case 'POVERTY':
      subfix = 'poverty'
      break;
    case 'WOMEN':
      subfix = 'agriculturalWomen'
      break;
    case 'FOOD':
      subfix = 'foodLoss'
      break;
    case 'AOI':
      subfix = 'agOrientation'
      break;
  }

  return `${API_ROOT}/data/${subfix}/summary/csv`
}

function queryParams(params) {
  return Object.keys(params)
    .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&');
}

const post = (url, params, isBlob) => {
  return new Promise((resolve, reject) => {
    fetch(url, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(params)
      })
      .then(
        function(response) {
          if (response.status !== 200) {
            reject(response)
          }
          if (isBlob) {

            resolve(response.blob())
          }
          response.json().then(function(data) {
            resolve(data);
          }).catch(() => resolve(response.status));
        }
      )
      .catch(function(err) {
        reject(err);
      });
  })
}
const get = (url) => {
  return new Promise((resolve, reject) => {
    fetch(url)
      .then(
        function(response) {
          if (response.status !== 200) {
            reject(response)
          }
          response.json().then(function(data) {
            resolve(data);
          });
        }
      )
      .catch(function(err) {
        reject(err);
      });
  })
}

export const getRapidLinks = () => {
  return get(URL_RAPID_LINKS)
}

export const getDataSet = (name) => {
  return post(API_ROOT + dumpUrlBuilder(name))
}

export const getItems = (category, path, params) => {
  return post(API_ROOT + itemsURLBuilder(category, path, ), params.global)
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


export const exportIndicators = (what, format, lang, params) => {

  return new Promise((resolve, reject) => {

    let url=''
    let fileName=(what=='ALL'?'indicator':what)+(format=='XLS'?'.xlsx':'.csv')

    if (format == 'XLS') {
      url=xlsExportURLBuilder(what)
    } else if (format == 'CSV') {
      url=csvExportURLBuilder(what)
    }

      post(url, {
        ...params.global,
        ...params.poverty,
        ...params.women,
        ...params.food,
        ...params.aoi,
        lang
      }, true).then(blob => {

        
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = fileName.toLowerCase();
        document.body.appendChild(a); // we need to append the element to the dom -> otherwise it will not work in firefox
        a.click();
        a.remove(); //afterwards we remove the element again
      }).catch(error => {
        reject(error)
      })


  })
}


export const getAgricuturalDistribution = (params) => {
  return new Promise((resolve, reject) => {
    const specificFilters = params.women;
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
    const specificFilters = params.women;
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
          style: "percent"
        }, {
          value: data.agriculturalWomen.data ? data.agriculturalWomen.data.value : null,
          image: '/sdg/5.svg',
          text: 'Women in the Agricultural sector',
          key: 'indicator.global.women.short',
          year: data.agriculturalWomen.data ? data.agriculturalWomen.data.year : null,
          style: "percent"
        },

        {
          value: data.foodLoss.data ? data.foodLoss.data.value : null,
          image: '/sdg/food_loss.svg',
          text: 'Global Food Loss Ratio',
          key: 'indicator.global.food.short',
          year: data.agriculturalWomen.data ? data.agriculturalWomen.data.year : null,
          style: "percent"
        }

        , {
          value: data.agOrientation.data ? data.agOrientation.data.value : null,
          image: '/sdg/12.svg',
          text: 'Agriculture orientation index for government expenditures',
          key: 'indicator.global.aoi.short',
          year: data.agOrientation.data ? data.agOrientation.data.year : null,
          style: "percent"
        },

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
