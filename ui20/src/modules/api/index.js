import RainLevelFilter from "../entities/rainfall/RainLevelFilter"
import * as EP from './EPConstants'
import {get, post} from './request'

export const getAllWaterResources = () => get(EP.WATER_ALL)
export const getRainfall = (rainLevelFilter: RainLevelFilter) => post(EP.RAINFALL, rainLevelFilter)
export const getLengthOfDrySequence = (filter) => post(EP.DRY_SEQUENCE, filter)
export const getRainSeason = (year: number) => post(EP.RAINSEASON, {year})
