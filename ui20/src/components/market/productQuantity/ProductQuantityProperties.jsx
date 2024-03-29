import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import ProductQuantityConfig from "../../../modules/entities/product/quantity/ProductQuantityConfig"
import {
  anyWithIdAndLabelToOptions,
  anyWithIdAndNameToOptions,
  yearsToOptions
} from "../../../modules/graphic/common/GraphicDTO"
import * as quantityActions from "../../../redux/actions/market/quantityActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

class ProductQuantityProperties extends Component {
  static propTypes = {
    setYearFilter: PropTypes.func.isRequired,
    setProductTypeFilter: PropTypes.func.isRequired,
    setMarketFilter: PropTypes.func.isRequired,
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
    config: PropTypes.instanceOf(ProductQuantityConfig).isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    return (
      <div className="indicator chart properties">
        <ProductQuantityFilters {...this.props} />
      </div>
    )
  }

}

const ProductQuantityFilters = (props) => {
  const {setYearFilter, setProductTypeFilter, setMarketFilter, config, intl}  = props
  const {year, productTypeId, marketId} = props.filter
  return (
    <div className="indicator chart filter three-filters">
      <div className="filter item fixed">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={(years) => setYearFilter(years[0])}
          min={1} max={1} single
          selected={[year]} text={intl.formatMessage({ id: "indicators.filters.year" })} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={anyWithIdAndLabelToOptions(config.productTypes)}
          onChange={(productTypeIds) => setProductTypeFilter(productTypeIds[0])}
          min={1} max={1} single withTooltips
          selected={[productTypeId]} text={intl.formatMessage({ id: "indicators.filters.product.type" })} />
      </div>
      <div className="filter item fixed">
        <FilterDropDown
          options={anyWithIdAndNameToOptions(config.markets)}
          onChange={(marketIds) => setMarketFilter(marketIds[0])}
          min={1} max={1} single withSearch withTooltips
          selected={[marketId]} text={intl.formatMessage({ id: "indicators.filters.market" })} />
      </div>
    </div>)
}


const mapStateToProps = state => {
  return {
    agricultureConfig: state.getIn(['agriculture', 'data', 'agricultureConfig']),
    config: state.getIn(['agriculture', 'data', 'productQuantityChart', 'config']),
  }
}

const mapActionCreators = {
  setYearFilter: (year) => quantityActions.setProductQuantityFilter(['year'], year),
  setProductTypeFilter: (productTypeId) => quantityActions.setProductQuantityFilter(['productTypeId'], productTypeId),
  setMarketFilter: (marketId) => quantityActions.setProductQuantityFilter(['marketId'], marketId),
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantityProperties))

