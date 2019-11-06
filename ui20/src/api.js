const API_ROOT = document.location.href.indexOf('localhost') > -1 ? 'http://localhost:8080' : document.location.origin

const dumpUrlBuilder = name => `/data/${name}/dump`

const itemsURLBuilder = (category, path) => `/data/${path}/${category}`

const URL_INDICATORS = API_ROOT + '/data/indicator'
const URL_POVERTY = API_ROOT + '/data/poverty/summary'


function queryParams(params) {
  return Object.keys(params)
    .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&');
}

const post = (url, params) => {
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


export const getDataSet = (name) => {
  return get(API_ROOT + dumpUrlBuilder(name))
}

export const getItems = (category, path) => {
  return post(API_ROOT + itemsURLBuilder(category, path))
}


export const loadPovertyChartData = (params) => {

    return new Promise((resolve, reject) => {

        post(URL_POVERTY, params).then((data) => {


           resolve(data)

        }).catch(error=>{

          reject(error)
        })
      })
    }

    export const getGlobalIndicators = (params) => {
      return new Promise((resolve, reject) => {

        debugger;
        post(URL_INDICATORS, params.global).then((data) => {

          debugger;

          const mockData = [
            {
              value: data.poverty.data?data.poverty.data.value:null,
              image: '/sdg/1.svg',
              text: 'Proportion of population below the international poverty line',
              key: 'indicator.global.population.short',
              year: data.poverty.data?data.poverty.data.year:null,
              style: "percent"
            }, {
              value: data.agriculturalWomen.data?data.agriculturalWomen.data.value:null,
              image: '/sdg/5.svg',
              text: 'Women in the Agricultural sector',
              key: 'indicator.global.agricultrural.short',
              year: data.agriculturalWomen.data?data.agriculturalWomen.data.year:null,
              style: "percent"
            }, {
              value: data.agOrientation.data?data.agOrientation.data.value:null,
              image: '/sdg/12.svg',
              text: 'Agriculture orientation index for government expenditures',
              key: 'indicator.global.aoi.short',
              year: data.agOrientation.data?data.agOrientation.data.year:null,
              style: "percent"
            },
            {
              value: data.foodLoss.data?data.foodLoss.data.value:null,
              image: '/sdg/2.svg',
              text: 'Global Food Loss Index',
              key: 'indicator.global.global.food.short',
              year: data.agriculturalWomen.data?data.agriculturalWomen.data.year:null,
              style: "percent"
            }
          ]

            debugger;
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
