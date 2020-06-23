import FilterGroupedOptions from "../../../components/common/filter/FilterGroupedOptions"
import Product from "../../entities/product/Product"
import ProductType from "../../entities/product/ProductType"

export const productToOption = (p: Product) => ({
  key: p.id,
  text: p.name,
  value: p.id
})

export const productsToOptions = (products: Array<Product>) => products.map(productToOption)

export const productsToFilterGroupedOptions = (productsByTypeId: Map<number, Set<number>>,
  productTypes: Map<number, ProductType>) => {
  const groups = new Map()
  productsByTypeId.forEach((productIds: Set<Product>, key: number) => {
    groups.set(productTypes.get(key).label, productIds)
  })
  return new FilterGroupedOptions(groups)
}
