export default class MenuEntry {
  url: string
  messageId: string
  descriptionId: string
  icon: string
  className: string

  constructor(url: string, messageId: string, descriptionId: string, icon: string, className: string) {
    this.url = url
    this.messageId = messageId
    this.descriptionId = descriptionId
    this.icon = icon
    this.className = className
  }
}

export const APP_MENU = [
  new MenuEntry("home", "menu.home", null, "logo-anacim-small-optimized.png", "home-icon"),
  new MenuEntry("water-resources", "home.pane.waterResources.title", "home.pane.waterResources.description", "page_icon_water.svg"),
  new MenuEntry("agriculture-and-market", "home.pane.agricultureAndMarkets.title", "home.pane.agricultureAndMarkets.description", "page_icon_agriculture.svg"),
  new MenuEntry("livestock", "home.pane.livestock.title", "home.pane.livestock.description", "page_icon_livestock.svg"),
  new MenuEntry("bulletins", "menu.bulletins.title", "menu.bulletins.description", "page_icon_bulletins.svg"),
]
