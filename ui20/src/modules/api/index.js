import RainLevelFilter from "../entities/rainfall/RainLevelFilter"
import RainMapFilter from "../entities/rainfallMap/RainMapFilter"
import RiverLevelFilter from "../entities/river/RiverLevelFilter"
import * as EP from './EPConstants'
import {get, post, urlWithSearchParams} from './request'

export const getAllWaterResources = () => get(EP.WATER_ALL)
export const getRainfall = (rainLevelFilter: RainLevelFilter) => post(EP.RAINFALL, rainLevelFilter)
export const getRainfallMap = (rainLevelFilter: RainMapFilter, layerType: string) => post(EP.RAINFALL_MAP, {
  ...rainLevelFilter,
  layerType
}, false, [200, 404])
export const getLengthOfDrySequence = (filter) => post(EP.DRY_SEQUENCE, filter)
export const getRainSeason = (year: number) => post(EP.RAINSEASON, {year})
export const getRiverLevel = (riverLevelFilter: RiverLevelFilter) => post(EP.RIVER_LEVEL, riverLevelFilter)

export const getAllMarketAndAgriculture = () => get(EP.MARKET_AND_AGRICULTURE_ALL)
export const getProductPrices = (year, productId, marketId) => post(EP.PRODUCT_PRICES, {year, productId, marketId})
export const getProductQuantities = (year, productTypeId, marketId) => post(EP.PRODUCT_QUANTITIES, {year, productTypeId, marketId})

export const getAllLivestock = () => get(EP.LIVESTOCK_ALL)
export const getDiseaseQuantity = (year, diseaseId) => post(EP.DISEASE_QUANTITY, {year, diseaseId})

export const getAllBulletins = () => get(EP.BULLETIN_ALL)
export const getDownloadBulletinUrl = (id) => urlWithSearchParams(EP.BULLETIN_DOWNLOAD, {id})
export const getDownloadAnnualReportUrl = (id) => urlWithSearchParams(EP.BULLETIN_ANNUAL_REPORT_DOWNLOAD, {id})
export const getBulletins = (locationId) => post(EP.BULLETINS, {locationId})

export const getAllMembers = () => get(EP.MEMBER_ALL)

export const getWorldMapAttribution = () => get(EP.ESRI_WORLD_MAP_SERVICE_INFO)
export const getTopoMapAttribution = () => get(EP.ESRI_TOPO_MAP_SERVICE_INFO)

export const getCNSCHeader = () => get(EP.CNSC_HEADER)
export const getFMConfig = () => get(EP.ENABLED_FEATURES)

export const doTest = () => get(EP.TEST)
