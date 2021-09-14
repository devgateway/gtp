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
export const RAINFALL_MAP = API_WATER + '/rain-map/data'
export const DRY_SEQUENCE = API_WATER + '/dry-sequence/data'
export const RAINSEASON = API_WATER + '/rain-season/data'
export const RIVER_LEVEL = API_WATER + '/river-level/data'

const API_MARKET_AND_AGRICULTURE = API_GRAPHIC + '/agriculture'
export const MARKET_AND_AGRICULTURE_ALL = API_MARKET_AND_AGRICULTURE + '/all'
export const PRODUCT_PRICES = API_MARKET_AND_AGRICULTURE + '/product-prices/data'
export const PRODUCT_QUANTITIES = API_MARKET_AND_AGRICULTURE + '/product-quantities/data'

const API_LIVESTOCK = API_GRAPHIC + '/livestock'
export const LIVESTOCK_ALL = API_LIVESTOCK + '/all'
export const DISEASE_QUANTITY = API_LIVESTOCK + '/disease-quantity/data'

export const GTP_ROOT = API_ROOT + '/gtp'
export const BULLETIN_ALL = GTP_ROOT + '/materials/all'
export const BULLETINS = GTP_ROOT + '/materials/data'
export const BULLETIN_DOWNLOAD = GTP_ROOT + '/bulletin'
export const BULLETIN_ANNUAL_REPORT_DOWNLOAD = GTP_ROOT + '/annual-report'
export const MEMBER_ALL = GTP_ROOT + '/members'
export const MEMBER_LOGO = GTP_ROOT + '/member/logo?id='

const ESRI_SERVICE_INFO = 'https://services.arcgisonline.com/arcgis/rest/services/{service}?f=pjson'
export const ESRI_WORLD_MAP_SERVICE_INFO = ESRI_SERVICE_INFO.replace('{service}', 'World_Street_Map/MapServer')
export const ESRI_TOPO_MAP_SERVICE_INFO = ESRI_SERVICE_INFO.replace('{service}', 'World_Topo_Map/MapServer')

const APP = API_ROOT + '/app'
export const CNSC_HEADER = APP + '/cnsc-header'
export const CNSC_HEADER_LOGO = APP + '/cnsc-header/logo'

const FM = API_ROOT + '/fm'
export const ENABLED_FEATURES = FM + '/enabled-features'

export const TEST = LOCATION + '/dummy'
