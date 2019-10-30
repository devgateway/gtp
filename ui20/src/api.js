const API_ROOT = document.location.href.indexOf('localhost') > -1 ? 'http://localhost:8080' : document.location.origin

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
  debugger;

  const val=Math.floor(Math.random() * 100)
  const val1=Math.floor(Math.random() * 100)
  const val2=Math.floor(Math.random() * 100)
  const val3=Math.floor(Math.random() * 100)

  const data = [{
    value: val+' %',
    image: '/sdg/1.svg',
    text: 'Proportion of population below the international poverty line'
  }, {
    value: val1+' %',
    image: '/sdg/5.svg',
    text: 'Women in the Agricultural sector'
  }, {
    value:val2+'K',
    image: '/sdg/12.svg',
    text: 'Agriculture orientation index for government expenditures'
  },
  {
    value: val3+'M',
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
