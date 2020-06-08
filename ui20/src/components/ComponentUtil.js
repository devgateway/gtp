export const cssClasses = (...classes) => classes.filter(c => !!c).join(' ')

export const scrollToRef = (ref) => window.scrollTo(0, ref.current.offsetTop)
