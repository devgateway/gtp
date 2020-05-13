export const PAGE_SIZE = 10

export const YEAR = 'year'
export const ZONE = 'zone'
export const REGION = 'region'
export const DEPARTMENT = 'department'
export const POST = 'post'
export const PLANNED = 'planned'
export const ACTUAL = 'actual'
export const DIFFERENCE = 'difference'

export const COLUMNS = [ZONE, REGION, DEPARTMENT, POST, PLANNED, ACTUAL, DIFFERENCE]
export const COLUMN_MESSAGE_KEY = {}
COLUMN_MESSAGE_KEY[ZONE] = "all.zone"
COLUMN_MESSAGE_KEY[REGION] = "all.region"
COLUMN_MESSAGE_KEY[DEPARTMENT] = "all.department"
COLUMN_MESSAGE_KEY[POST] = "all.post"
COLUMN_MESSAGE_KEY[PLANNED] = "rainseason.table.planned"
COLUMN_MESSAGE_KEY[ACTUAL] = "rainseason.table.actual"
COLUMN_MESSAGE_KEY[DIFFERENCE] = "rainseason.table.difference"

export const FILTER_MESSAGE_KEY = {}
FILTER_MESSAGE_KEY[YEAR] = "indicators.filters.year"
