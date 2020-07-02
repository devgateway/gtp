import { install } from 'resize-observer'

const InitResizeObserver = () => {
  if (!window.ResizeObserver) {
    console.log('Loading ResizeObserver polyfill')
    install()
  }
}

export default InitResizeObserver
