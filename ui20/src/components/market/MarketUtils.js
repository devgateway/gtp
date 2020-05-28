export const MARKET_TYPE_COLOR = {
  'rural-market': '#6faa27',
  'fishing-dock': '#f07d00',
  'transformation-place': '#219bd8',
  default: '#727282'
}

export const getMarketTypeColor = (typeName: string) => MARKET_TYPE_COLOR[typeName] || MARKET_TYPE_COLOR.default
