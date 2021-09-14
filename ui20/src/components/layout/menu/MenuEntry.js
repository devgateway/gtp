import {PAGE_AGRICULTURE_AND_MARKET, PAGE_LIVESTOCK, PAGE_WATER_RESOURCES} from "../../../modules/entities/FMConstants"

export default class MenuEntry {
  url: string
  messageId: string
  descriptionId: string
  icon: string
  className: string
  fmEntry: string

  constructor(url: string, messageId: string, descriptionId: string, icon: string, className: string, fmEntry: string) {
    this.url = url
    this.messageId = messageId
    this.descriptionId = descriptionId
    this.icon = icon
    this.className = className
    this.fmEntry = fmEntry
  }
}

export const APP_MENU = [
  new MenuEntry("home", "menu.home", null, "page_icon_home.svg"),
  new MenuEntry("about", "menu.about", null, "page_icon_about.svg"),
  new MenuEntry("water-resources", "home.pane.waterResources.title", "home.pane.waterResources.description", "page_icon_water.svg", null,PAGE_WATER_RESOURCES),
  new MenuEntry("agriculture-and-market", "home.pane.agricultureAndMarkets.title", "home.pane.agricultureAndMarkets.description", "page_icon_agriculture.svg", null, PAGE_AGRICULTURE_AND_MARKET),
  new MenuEntry("livestock", "home.pane.livestock.title", "home.pane.livestock.description", "page_icon_livestock.svg", null, PAGE_LIVESTOCK),
  new MenuEntry("gtp-bulletins", "menu.bulletins.title", "menu.bulletins.description", "page_icon_bulletins.svg"),
]
