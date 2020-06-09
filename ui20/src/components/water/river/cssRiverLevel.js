import {categoricalColorSchemes} from '@nivo/colors'

export const LEGEND_TRANSLATE_X = 35
export const LEGEND_ITEM_WIDTH = 100

const { category10, set3, dark2 } = categoricalColorSchemes
// category10=10, set3=12, dark2=8 => total 30 colors
export const LEVEL_COLORS = [...category10, ...set3, ...dark2]
export const REFERENCE_COLORS = ['#bdbdbd', '#828282']
