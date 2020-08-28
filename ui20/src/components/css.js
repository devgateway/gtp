import {categoricalColorSchemes} from '@nivo/colors'

export const BASE_TEXT_COLOR = '#747474'

export const GRAPHIC_COLOR_BLUE = '#3c7ebb'
export const GRAPHIC_COLOR_RED = '#c94545'
export const GRAPHIC_COLOR_YELLOW = '#f2b704'
export const GRAPHIC_COLOR_GREEN = '#479519'
export const GRAPHIC_COLOR_GRAY1 = '#898989'
export const GRAPHIC_COLOR_GRAY2 = '#c1c1c1'
export const GRAPHIC_COLOR_GRAY3 = '#e1e1e1'
export const GRAPHIC_COLOR_ORANGE = '#e86d00'
export const GRAPHIC_LIGHT_PLUM = '#D2A6E4'

export const GRAPHIC_MAIN_COLORS = [
  GRAPHIC_COLOR_BLUE,
  GRAPHIC_COLOR_RED,
  GRAPHIC_COLOR_YELLOW,
  GRAPHIC_COLOR_GREEN,
]

const { category10, set3, dark2 } = categoricalColorSchemes
// category10=10, set3=12, dark2=8 => total 30 colors
export const PALLET_COLORS_NIVO = [...category10, ...set3, ...dark2]
export const REFERENCE_COLORS_OLD = ['#bdbdbd', '#828282', '#535353', '#0e0e0e']
export const REFERENCE_COLORS = [GRAPHIC_LIGHT_PLUM, GRAPHIC_COLOR_BLUE]

export const PALLET_COLORS = [
  GRAPHIC_COLOR_BLUE,
  GRAPHIC_COLOR_RED,
  GRAPHIC_COLOR_YELLOW,
  GRAPHIC_COLOR_ORANGE,
  GRAPHIC_LIGHT_PLUM,
  "#FF8080",
  "#2E5B92",
  "#A05EBC",
  "#93E155",
  "#79BEFE",
  "#29BEA4",
  "#479519"
]

export const RIVER_PALLET_COLORS = PALLET_COLORS.filter(c => !REFERENCE_COLORS.includes(c) && c !== GRAPHIC_COLOR_RED)

export const NIVO_THEME = {
  axis: {
    ticks: {
      line: {
        stroke: '#e3e3e3',
      },
      text: {
        fontSize: 10,
      }
    },
    legend: {
      text: {
        fontSize: 10,
      },
    },
  },
  textColor: BASE_TEXT_COLOR,
}

export const LEGEND_SYMBOL_LINE_LENGTH = 15

export const MENU_HEIGHT_DEFAULT = 58

export const GRAPHIC_TITLE_LEFT_PADDING = 21

export const NIVO_CHART_BOTTOM = 65
export const NIVO_CHART_BOTTOM_LEGEND_OFFSET = 40

export const NIVO_CHART_WITH_CUSTOM_LEGEND_MARGIN = {
  top: 10,
  right: 50,
  bottom: 65,
  left: 60
}
