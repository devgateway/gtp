export const ENV_DEV = 'development'
export const ENV_STG = 'staging'
export const ENV_PRD = 'production'

const getEnv = () => {
  const { host } = document.location;
  if (host.indexOf('localhost') > -1) return ENV_DEV
  if (host.indexOf('dgstg') > -1) return ENV_STG
  if (host.indexOf('developmentgateway') > -1) return ENV_STG
  return ENV_PRD
}

export const ENV = getEnv();
export const IS_ENV_DEV = ENV === ENV_DEV

const LOCATION = IS_ENV_DEV ? 'http://localhost:8080' : document.location.origin
const API_ROOT = LOCATION  + '/api'
const API_GRAPHIC = API_ROOT + '/graphics'

const API_WATER = API_GRAPHIC + '/water'
export const WATER_ALL = API_WATER + '/all'
export const WATER_CONFIG = API_WATER + '/config'
export const RAINFALL = API_WATER + '/rain-level/data'
export const DRY_SEQUENCE = API_WATER + '/dry-sequence/data'
export const RAINSEASON = API_WATER + '/rain-season/data'
export const RIVER_LEVEL = API_WATER + '/river-level/data'


const API_MARKET_AND_AGRICULTURE = API_GRAPHIC + '/agriculture'
export const MARKET_AND_AGRICULTURE_ALL = API_MARKET_AND_AGRICULTURE + '/all'
export const PRODUCT_PRICES = API_MARKET_AND_AGRICULTURE + '/product-prices/data'

export const GTP_ROOT = API_ROOT + '/gtp'
export const BULLETIN_ALL = GTP_ROOT
export const BULLETIN_DOWNLOAD = GTP_ROOT + '/bulletin'
export const BULLETIN_ANNUAL_REPORT_DOWNLOAD = GTP_ROOT + '/annual-report'

const ESRI_SERVICE_INFO = 'http://services.arcgisonline.com/arcgis/rest/services/{service}?f=pjson'
export const ESRI_MAP_SERVICE_INFO = ESRI_SERVICE_INFO.replace('{service}', 'World_Street_Map/MapServer')
