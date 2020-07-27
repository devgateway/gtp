import React, {useRef, useState, useEffect} from "react"
import {Segment, Sticky} from "semantic-ui-react"
import "./indicators.scss"
import "./indicator-base.scss"
import "./graphicPage.scss"
import * as CUtils from "../../ComponentUtil"
import * as cssJS from "../../css"
import MenuScrollableTo, {MenuItemDef} from "../MenuScrollableTo"
import ScrollableTo, {ScrollRef} from "../ScrollableTo"
import TrackVisibility from "../TrackVisibility"


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

const getGraphicMenuOffset = () => -CUtils.getElementHeightByQuerySelector(".graphic-menu > .ui.sticky",
  cssJS.MENU_HEIGHT_DEFAULT)

const GraphicPage = (props) => {
  const contextRef = useRef()
  const graphicsDefs: Array<GraphicDef> = props.graphicsDefs
  const menuDefs = graphicsDefs.map((graphicDef) => graphicDef.menuItemDef)

  const [active, setActive] = useState(0)
  const [width, setWidth] = useState('auto')
  const [firstGraphicOffset, setFirstGraphicOffset] = useState(() => getGraphicMenuOffset())

  menuDefs[0].scrollRef.offset = firstGraphicOffset

  const resizeObserver = new ResizeObserver((entries) => {
    const newWidth = entries[0].target.clientWidth - 1
    if (newWidth !== width) {
      setWidth(newWidth)
    }
    const graphicMenuOffset = getGraphicMenuOffset()
    if (graphicMenuOffset !== firstGraphicOffset) {
      setFirstGraphicOffset(graphicMenuOffset)
    }
  })

  useEffect(() => {
    if (contextRef.current) {
      resizeObserver.observe(contextRef.current)
    }
    return () => resizeObserver.unobserve(contextRef.current)
  }, [contextRef.current, width, firstGraphicOffset])

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
