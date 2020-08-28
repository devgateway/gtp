import ResizeObserver from 'resize-observer-polyfill';

const InitResizeObserver = () => {
  if (!window.ResizeObserver) {
    console.log('Loading ResizeObserver polyfill')
    window.ResizeObserver = ResizeObserver
  }
}

export default InitResizeObserver
