import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import ProductPriceConfig from "../../../modules/entities/product/ProductPriceConfig"
import {yearsToOptions} from "../../../modules/graphic/common/GraphicDTO"
import * as priceActions from "../../../redux/actions/market/priceActions"
import FilterDropDown from "../../common/filter/FilterDropDown"

class ProductPriceProperties extends Component {
  static propTypes = {
    setYearFilter: PropTypes.func.isRequired,
    setProductFilter: PropTypes.func.isRequired,
    setMarketFilter: PropTypes.func.isRequired,
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
    config: PropTypes.instanceOf(ProductPriceConfig).isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    return (
      <div className="indicator chart properties">
        <ProductPriceFilters {...this.props} />
      </div>
    )
  }
}

const ProductPriceFilters = (props) => {
  const {setYearFilter, setProductFilter, setMarketFilter, config, intl}  = props
  const {year, productId, marketId} = props.filter
  return (
    <div className="indicator chart filter">
      <div className="filter item">
        <FilterDropDown
          options={yearsToOptions(config.years)} onChange={(years) => setYearFilter(years[0])}
          min={1} max={1} single
          selected={[year]} text={intl.formatMessage({ id: "indicators.filters.year", defaultMessage: "Years" })} />
      </div>
    </div>
  )
}

const mapStateToProps = state => {
  return {
    agricultureConfig: state.getIn(['agriculture', 'data', 'agricultureConfig']),
    config: state.getIn(['agriculture', 'data', 'productPriceChart', 'config']),
  }
}

const mapActionCreators = {
  setYearFilter: (year) => priceActions.setProductPriceFilter(['year'], year),
  setProductFilter: (productId) => priceActions.setProductPriceFilter(['productId'], productId),
  setMarketFilter: (marketId) => priceActions.setProductPriceFilter(['marketId'], marketId),
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductPriceProperties))
