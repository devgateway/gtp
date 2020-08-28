import 'url-search-params-polyfill';

export const FETCH_TIMEOUT_MILLISECONDS = 30000

const fetchWithTimeout = (url, options) => {
  let timeoutTrigger = undefined

  return Promise.race([
    fetch(url, options).then(result => {
      clearTimeout(timeoutTrigger)
      return result
    }).catch(result => {
      clearTimeout(timeoutTrigger)
      return Promise.reject(result)
    }),
    new Promise((resolve, reject) => {
      timeoutTrigger = setTimeout(() => {
        console.error(`${url} Request timed out`)
        return reject(new Error('Request timed out'))
      }, FETCH_TIMEOUT_MILLISECONDS)
    })
  ])
}

export const urlWithSearchParams = (url, params) => {
  if (!params) return url
  const u = new URL(url)
  u.search = new URLSearchParams(params).toString()
  return u.toString()
}

export const get = (url, params) => fetchWithTimeout(urlWithSearchParams(url, params)).then(response => {
  if (response.status !== 200) {
    return Promise.reject(response)
  }
  return response.json().then(data => Promise.resolve(data))
}).catch(Promise.reject)


export const post = (url, params, isBlob) => fetchWithTimeout(url, {
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
    },
    method: 'POST',
    body: JSON.stringify(params)
  }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(response)
      }
      if (isBlob) {
        return Promise.resolve(response.blob())
      }
      return response.json()
        .then((data) => Promise.resolve(data))
        .catch((err) => {
          console.error(err)
          return Promise.resolve(response.status)
        })
    }
  ).catch(Promise.reject)

export const getFile = (url, params) => fetchWithTimeout(urlWithSearchParams(url, params)).then(response => {
  if (response.status !== 200) {
    return Promise.reject(response)
  }
  showFileDownload(response.blob())
  return Promise.resolve()
}).catch(Promise.reject)

const showFileDownload = (blob) => {
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = "gtp-bulletin222.pdf"
  document.body.appendChild(a) // we need to append the element to the dom -> otherwise it will not work in firefox
  a.click()
  a.remove() // afterwards we remove the element again
}
