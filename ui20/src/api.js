const API_ROOT = document.location.href.indexOf('localhost') > -1 ? 'http://localhost:8080' : 'https://ad3.dgstg.org/'

const dumpUrlBuilder = name => `/data/${name}/dump`

const itemsURLBuilder = category => `/data/filter/${category}`


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

export const getItems = (category) => {
  return get(API_ROOT + itemsURLBuilder(category))
}



export const getGlobalIndicators = (params) => {

  const data = [{
    value: '55%',
    image: '/sdg/1.svg',
    text: 'Proportion of population below the international poverty line'
  }, {
    value: '2.2M',
    image: '/sdg/5.svg',
    text: 'Women in the Agricultural sector'
  }, {
    value: '2.2M',
    image: '/sdg/12.svg',
    text: 'Agriculture orientation index for government expenditures'
  },
  {
    value: '2.2M',
    image: '/sdg/2.svg',
    text: 'Global Food Loss Index'
  }]


  return new Promise((resolve, reject) => {
      resolve(data)
  })
}

export const getDefaultIndicatorFilters = () => {
  return new Promise((resolve, reject) => {
    resolve({
      years: [2019],
      regions: ['DK'],
      crops: [95]
    })
  })
}
