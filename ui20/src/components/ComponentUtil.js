import * as cssJS from "./css"

export const cssClasses = (...classes) => classes.filter(c => !!c).join(' ')

export const scrollToRef = (ref, extraOffset) => window.scrollTo(0, ref.current.offsetTop + extraOffset)

export const getBrowserClass = () => {
  if (!!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime)) {
    return 'chrome'
  }
  // Internet Explorer 6-11
  if (/* @cc_on!@*/false || !!document.documentMode) {
    return 'IE'
  }
  const navUserAgent = navigator.userAgent
  if (/Safari/.test(navUserAgent) && !/Chrome/.test(navUserAgent)) {
    const browserVer = navUserAgent.substring(navUserAgent.indexOf("Version/") + "Version/".length)
    if (browserVer.startsWith("13.")) {
      return 'safari safari-13'
    } else if (browserVer.startsWith("12.")) {
      return 'safari safari-12'
    }
    return 'safari'
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

export const getHeaderHeight = () => getElementHeightById("ad3-header")
export const getFooterHeight = () => getElementHeightById("ad3-footer")

export const getElementHeightById = (id, noElementHeight = 0) =>
  getElementHeight(document.getElementById(id), noElementHeight)

export const getElementHeightByQuerySelector = (querySelector, noElementHeight = 0) =>
  getElementHeight(document.querySelector(querySelector), noElementHeight)

const getElementHeight = (element, noElementHeight) => element ? element.clientHeight : noElementHeight
