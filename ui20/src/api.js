const API_ROOT = document.location.href.indexOf('localhost') > -1 ? 'http://localhost:8080' : document.location.origin

const dumpUrlBuilder = name => `/data/${name}/dump`

const itemsURLBuilder = (category, path) => `/data/${path}/${category}`

const URL_INDICATORS = API_ROOT + '/data/indicator'

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
  return get(API_ROOT + itemsURLBuilder(category, path))
}


export const loadPovertyChartData = (params) => {

  return new Promise((resolve, reject) => {


    var trace1 = {
      x: [20, 14, 23],
      y: ['giraffes', 'orangutans', 'monkeys'],
      name: 'SF Zoo',
      orientation: 'v',
      marker: {
        color: 'rgba(55,128,191,0.6)',
        width: 1
      },
      type: 'bar'
    };

    var trace2 = {
      x: [12, 18, 29],
      y: ['giraffes', 'orangutans', 'monkeys'],
      name: 'LA Zoo',
      orientation: 'v',
      type: 'bar',
      marker: {
        color: 'rgba(255,153,51,0.6)',
        width: 1
      }
    };

    const data={data:[trace1,trace2], layout: {barmode: "stack"}, filename: "stacked-bar", fileopt: "overwrite"}

    resolve(data)
  })
}

export const getGlobalIndicators = (params) => {
  return new Promise((resolve, reject) => {
    post(URL_INDICATORS, params.global).then((data) => {
      debugger;
      const mockData = [{
          value: data.poverty.data.value,
          image: '/sdg/1.svg',
          text: 'Proportion of population below the international poverty line',
          key: 'indicator.global.population.short',
          year: data.poverty.data.year,
          style: "percent"
        }, {
          value: data.agriculturalWomen.data.value,
          image: '/sdg/5.svg',
          text: 'Women in the Agricultural sector',
          key: 'indicator.global.agricultrural.short',
          year: data.agriculturalWomen.data.year,
          style: "percent"
        }, {
          value: data.agOrientation.data.value,
          image: '/sdg/12.svg',
          text: 'Agriculture orientation index for government expenditures',
          key: 'indicator.global.aoi.short',
          year: data.agOrientation.data.year,
          style: "percent"
        },
        {
          value: data.foodLoss.data.value,
          image: '/sdg/2.svg',
          text: 'Global Food Loss Index',
          key: 'indicator.global.global.food.short',
          year: data.agriculturalWomen.data.year,
          style: "percent"
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
      year: [2019],
      region: [],
      crop: []
    })
  })
}
