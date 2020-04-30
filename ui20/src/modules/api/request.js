
export const get = (url) => fetch(url).then(response => {
  if (response.status !== 200) {
    return Promise.reject(response)
  }
  return response.json().then(data => Promise.resolve(data))
})