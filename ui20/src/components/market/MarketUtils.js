export const MARKET_TYPE_COLOR = {
  'rural-market': '#F2BC00',
  'fishing-dock': '#4484BE',
  'transformation-place': '#5FC352',
  default: '#727282'
}

export const getMarketTypeColor = (typeName: string) => MARKET_TYPE_COLOR[typeName] || MARKET_TYPE_COLOR.default
