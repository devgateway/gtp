import React, {useRef, useState, useEffect} from "react"
import {Segment, Sticky} from "semantic-ui-react"
import "./indicators.scss"
import "../common/indicator-base.scss"
import "./graphicPage.scss"
import * as cssJS from "../css"
import MenuScrollableTo, {MenuItemDef} from "./MenuScrollableTo"
import ScrollableTo, {ScrollRef} from "./ScrollableTo"
import TrackVisibility from "./TrackVisibility"


export class GraphicDef {
  menuItemDef: MenuItemDef
  GraphicComponent
  GraphicScrollableTo

  constructor(messageId: string, icon: string, GraphicComponent) {
    this.menuItemDef = new MenuItemDef(messageId, icon, new ScrollRef())
    this.GraphicComponent = GraphicComponent
    this.GraphicScrollableTo = ScrollableTo(this.menuItemDef.scrollRef)
  }
}

const GraphicPage = (props) => {
  const contextRef = useRef()
  const graphicsDefs: Array<GraphicDef> = props.graphicsDefs
  const menuDefs = graphicsDefs.map((graphicDef) => graphicDef.menuItemDef)
  menuDefs[0].scrollRef.offset = -cssJS.MENU_HEIGHT
  const [active, setActive] = useState(0)
  const [width, setWidth] = useState('auto')

  const resizeObserver = new ResizeObserver((entries) => {
    const newWidth = entries[0].target.clientWidth - 1
    if (newWidth !== width) {
      setWidth(newWidth)
    }
  })

  useEffect(() => {
    if (contextRef.current) {
      resizeObserver.observe(contextRef.current)
    }
  }, [contextRef.current])

  return (
    <div ref={contextRef} className="graphic-page">
      <Sticky
        context={contextRef}
        className="graphic-menu"
        styleElement={{ width }}
        pushing>
        <MenuScrollableTo defs={menuDefs} active={active} />
      </Sticky>
      <Segment className="graphics">
        <div key="graphics" className="indicators content">
          {graphicsDefs.map(({menuItemDef, GraphicComponent, GraphicScrollableTo}, index) => (
            <GraphicScrollableTo key={`scrollable-${menuItemDef.messageId}`}>
              <TrackVisibility thresholds={[0.5]} onVisible={() => {
                setActive(index)
              }}>
                <GraphicComponent/>
              </TrackVisibility>
            </GraphicScrollableTo>
          ))}
        </div>
      </Segment>
    </div>)
}

export default GraphicPage
