import * as cssJS from "./css"

export const cssClasses = (...classes) => classes.filter(c => !!c).join(' ')

export const scrollToRef = (ref, extraOffset) => window.scrollTo(0, ref.current.offsetTop + extraOffset)

export const getBrowserClass = () => {
  if (!!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime)) {
    return 'chrome'
  }
  return ''
}

export const getColors = (count) => {
  let colors = []
  while (count) {
    const size = Math.min(count, cssJS.PALLET_COLORS.length)
    count -= size
    colors = colors.concat(count ? cssJS.PALLET_COLORS : cssJS.PALLET_COLORS.slice(0, size))
  }
  return colors
}
