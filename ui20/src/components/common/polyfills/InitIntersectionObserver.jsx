let initialized = false

export default async function InitIntersectionObserver() {
  if (!initialized) {
    initialized = true
    if (!('IntersectionObserver' in window)) {
      console.log('Loading IntersectionObserver polifill')
      await import('intersection-observer')
    }
  }
}
