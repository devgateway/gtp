import * as C from '../../modules/entities/Constants'

export const MARKET_TYPE_COLOR = {
  default: '#727282'
}
MARKET_TYPE_COLOR[C.MARKET_TYPE_RURAL] = '#F2BC00'
MARKET_TYPE_COLOR[C.MARKET_TYPE_FISHING_DOCK] = '#4484BE'
MARKET_TYPE_COLOR[C.MARKET_TYPE_TRANSFORMATION_PLACE] = '#5FC352'

export const getMarketTypeColor = (typeName: string) => MARKET_TYPE_COLOR[typeName] || MARKET_TYPE_COLOR.default
