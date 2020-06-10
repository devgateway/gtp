import React, {useRef} from "react"

export class ScrollRef {
  ref
  offset = 0
}

const ScrollableTo = (scrollRef: ScrollRef) => (props) => {
  scrollRef.ref = useRef(null)
  return (
    <div ref={scrollRef.ref}>
      {props.children}
    </div>
  )
}

export default ScrollableTo
