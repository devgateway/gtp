import FilterGroupedOptions from "../../../components/common/filter/FilterGroupedOptions"
import Product from "../../entities/product/Product"
import ProductType from "../../entities/product/ProductType"

export const productsToFilterGroupedOptions = (productsByTypeId: Map<number, Set<number>>,
  productTypes: Map<number, ProductType>) => {
  const groups = new Map()
  productsByTypeId.forEach((productIds: Set<Product>, key: number) => {
    groups.set(productTypes.get(key).label, productIds)
  })
  return new FilterGroupedOptions(groups)
}

export const marketsToFilterGroupedOptions = (marketIdsByTypeName: Map<string, Set<number>>) => {
  return new FilterGroupedOptions(marketIdsByTypeName)
}
