import React, {useRef, useState} from "react"
import {Segment, Sticky} from "semantic-ui-react"
import "../ipar/indicators/indicators.scss"
import "../common/indicator-base.scss"
import "./graphicPage.scss"
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
  const [active, setActive] = useState(0)

  return (
    <div ref={contextRef} className="graphic-page">
      <Sticky context={contextRef} className="graphic-menu" pushing>
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
