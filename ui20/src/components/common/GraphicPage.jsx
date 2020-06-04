import React, {useRef} from "react"
import {Sticky, Segment} from "semantic-ui-react"
import MenuScrollableTo, {MenuItemDef} from "./MenuScrollableTo"
import ScrollableTo, {ScrollRef} from "./ScrollableTo"
import "../ipar/indicators/indicators.scss"
import "../common/indicator-base.scss"
import "./graphicPage.scss"


export class GraphicDef {
  menuItemDef: MenuItemDef
  GraphicComponent
  GraphicScrollableTo

  constructor(messageId: string, GraphicComponent) {
    this.menuItemDef = new MenuItemDef(messageId, new ScrollRef())
    this.GraphicComponent = GraphicComponent
    this.GraphicScrollableTo = ScrollableTo(this.menuItemDef.scrollRef)
  }
}

const GraphicPage = (props) => {
  const contextRef = useRef()
  const graphicsDefs: Array<GraphicDef> = props.graphicsDefs
  const menuDefs = graphicsDefs.map((graphicDef) => graphicDef.menuItemDef)
  return (
    <div ref={contextRef} className="graphic-page">
      <Sticky context={contextRef} className="graphic-menu">
        <MenuScrollableTo defs={menuDefs} />
      </Sticky>
      <Segment attached='bottom'>
        <div key="graphics" className="indicators content fixed">
          {graphicsDefs.map(({menuItemDef, GraphicComponent, GraphicScrollableTo}) => (
            <GraphicScrollableTo key={`scrollable-${menuItemDef.messageId}`}>
              <GraphicComponent/>
            </GraphicScrollableTo>
          ))}
        </div>
      </Segment>
    </div>)
}

export default GraphicPage
