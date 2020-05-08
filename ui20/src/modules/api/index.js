import * as EP from './EPConstants'
import { get } from './request'

export const getAllWaterResources = () => get(EP.WATER_ALL)
