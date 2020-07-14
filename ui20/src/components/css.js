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

export const GRAPHIC_MAIN_COLORS = [
  GRAPHIC_COLOR_BLUE,
  GRAPHIC_COLOR_RED,
  GRAPHIC_COLOR_YELLOW,
  GRAPHIC_COLOR_GREEN,
]

const { category10, set3, dark2 } = categoricalColorSchemes
// category10=10, set3=12, dark2=8 => total 30 colors
export const PALLET_COLORS = [...category10, ...set3, ...dark2]
export const REFERENCE_COLORS = ['#bdbdbd', '#828282', '#535353', '#0e0e0e']

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

export const HEADER_HEIGHT = 70
export const MENU_HEIGHT = 354
export const FOOTER_HEIGHT = 297

export const GRAPHIC_TITLE_LEFT_PADDING = 21

export const NIVO_CHART_WITH_CUSTOM_LEGEND_MARGIN = {
  top: 10,
  right: 50,
  bottom: 75,
  left: 60
}
