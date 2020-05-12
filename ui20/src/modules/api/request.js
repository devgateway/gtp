
export const get = (url) => fetch(url).then(response => {
  if (response.status !== 200) {
    return Promise.reject(response)
  }
  return response.json().then(data => Promise.resolve(data))
}).catch(Promise.reject)


export const post = (url, params, isBlob) => fetch(url, {
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
