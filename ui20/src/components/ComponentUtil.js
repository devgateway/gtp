export const cssClasses = (...classes) => classes.filter(c => !!c).join(' ')

export const scrollToRef = (ref, extraOffset) => window.scrollTo(0, ref.current.offsetTop + extraOffset)

export const getBrowserClass = () => {
  if (!!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime)) {
    return 'chrome'
  }
  return ''
}
