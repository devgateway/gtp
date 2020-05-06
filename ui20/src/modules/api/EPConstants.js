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

const API_ROOT = IS_ENV_DEV ? 'http://localhost:8080' : document.location.origin
const API_GRAPHIC = API_ROOT + '/api/graphics'

const API_WATER = API_GRAPHIC + '/water'
export const WATER_ALL = API_WATER + '/all'
export const WATER_CONFIG = API_WATER + '/common-config'
export const RAINFALL = API_WATER + '/rain-level/data'