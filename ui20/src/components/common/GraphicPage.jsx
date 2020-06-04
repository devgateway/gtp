import React from "react"
import MenuScrollableTo, {MenuItemDef} from "./MenuScrollableTo"
import ScrollableTo, {ScrollRef} from "./ScrollableTo"
import "../ipar/indicators/indicators.scss"
import "../common/indicator-base.scss"


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
  const graphicsDefs: Array<GraphicDef> = props.graphicsDefs
  const menuDefs = graphicsDefs.map((graphicDef) => graphicDef.menuItemDef)
  return (
    <>
      <div key="graphics-menu">
        <MenuScrollableTo defs={menuDefs} />
      </div>
      <div key="graphics" className="indicators content fixed">
        {graphicsDefs.map(({menuItemDef, GraphicComponent, GraphicScrollableTo}) => (
          <GraphicScrollableTo key={`scrollable-${menuItemDef.messageId}`}>
            <GraphicComponent/>
          </GraphicScrollableTo>
        ))}
      </div>
    </>)
}

export default GraphicPage
