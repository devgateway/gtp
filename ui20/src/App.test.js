import React from 'react'
import ReactDOM from 'react-dom'
import App from './App'
import * as api from './api'

it('renders without crashing', () => {
  const div = document.createElement('div')
  ReactDOM.render(<App />, div)
})

it('call api', () => {
  api.getProductionDump().then((response) => {
    console.log(response)
  })
}
)
