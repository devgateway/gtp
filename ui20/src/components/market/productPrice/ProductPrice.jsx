import {ResponsiveLine} from "@nivo/line"
import {TableTooltip} from '@nivo/tooltip'
import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, FormattedNumber, injectIntl} from "react-intl"
import {connect} from "react-redux"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import PriceType from "../../../modules/entities/product/price/PriceType"
import ProductAvgPrice from "../../../modules/entities/product/price/ProductAvgPrice"
import ProductPriceChartDTO from "../../../modules/graphic/market/productPrice/ProductPriceChartDTO"
import ProductPriceLine from "../../../modules/graphic/market/productPrice/ProductPriceLine"
import Chip from "../../common/graphic/Chip"
import * as cssJS from "../../css"
import ProductPriceLegend, {getAveragePriceLabel} from "./ProductPriceLegend"

class ProductPrice extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(ProductPriceChartDTO).isRequired,
    agricultureConfig: PropTypes.instanceOf(AgricultureConfig).isRequired,
    filter: PropTypes.object.isRequired,
  }

  constructor(props) {
    super(props)
    this.state = {
      isLocalStateChange: false
    }
    this.onAveragePriceToggle = this.onAveragePriceToggle.bind(this)
  }

  static getDerivedStateFromProps(props, state) {
    const {isLocalStateChange} = state
    if (isLocalStateChange) {
      return {
        isLocalStateChange: false
      }
    }
    return {
      hideAvgPrices: new Set(),
    }
  }

  onAveragePriceToggle(priceTypeId) {
    const {hideAvgPrices} = this.state
    if (!hideAvgPrices.delete(priceTypeId)) {
      hideAvgPrices.add(priceTypeId)
    }
    this.setState({
      hideAvgPrices,
      isLocalStateChange: true,
    })
  }

  render() {
    const {filter, intl} = this.props
    const data: ProductPriceChartDTO = this.props.data
    const {previousYearAverages} = data
    const {hideAvgPrices} = this.state

    /*
    const priceTypes = Array.from(this.props.agricultureConfig.priceTypes.values())
    const colorsByPriceType: Map<number, string> = getColorsByPriceType(priceTypes)
    const colors = getColors(data.lines, colorsByPriceType)
    // one product line only =>
     */
    const colors = [cssJS.PALLET_COLORS[0]]
    /* since only retail price is used, there will be one color only, so below can be restored when many prices are used
    const avgColors = getAvgMarkersColors(previousYearAverages, colorsByPriceType)
    */
    const avgColors = [cssJS.GRAPHIC_COLOR_ORANGE]
    // TODO responsive top detection
    const chartTop = colors.length + avgColors.length < 5 ? 10 : 50

    return (
      <div>
        <div key="legend">
          <ProductPriceLegend
            filter={filter}
            data={data}
            avgColors={avgColors}
            lineColors={colors}
            onAveragePriceToggle={this.onAveragePriceToggle}/>
        </div>
        <div key="chart" className="graphic-content custom-legend">
          <ResponsiveLine
            enableGridY={true}
            enableGridX={false}
            margin={{...cssJS.NIVO_CHART_WITH_CUSTOM_LEGEND_MARGIN, top: chartTop}}

            data={data.lines}
            xScale={{
              type: 'time',
              format: '%Y-%m-%d',
              precision: 'day',
            }}
            xFormat="time:%Y-%m-%d"
            yScale={{
              type: 'linear',
              stacked: false,
            }}
            enableSlices='x'
            sliceTooltip={CustomSliceTooltip(filter, previousYearAverages, avgColors, hideAvgPrices)}

            markers={previousYearAverages.map((avg, index) => ({
              axis: 'y',
              value: avg.average,
              lineStyle: {
                stroke: avgColors[index],
                strokeWidth: hideAvgPrices.has(avg.priceTypeId) ? 0 : 2,
                strokeDasharray: "5,5"
              },
              legendOrientation: 'horizontal',
            }))}

            axisLeft={{
              legendPosition: 'middle',
              legend: intl.formatMessage({id: "indicators.chart.product.price.legend.y"}, {unit: data.product.unit}),
              legendOffset: -45,
              tickSize: 0,
              tickPadding: 5,
              tickRotation: 0,
            }}
            axisBottom={{
              format: (date: Date) => intl.formatMessage({id: `all.month.${date.getMonth() + 1}`}),
              tickValues: 'every month',
              tickSize: 10,
              legendOffset: 40,
              legendPosition: 'middle'
            }}
            curve="monotoneX"
            enablePoints={true}
            useMesh={true}
            colors={colors}
            animate={true}

            layers={['grid', 'markers', 'axes', 'areas', 'crosshair', 'lines', 'points', 'slices', 'mesh', 'legends',
            ]}
            theme={cssJS.NIVO_THEME}
          />
        </div>
      </div>);
  }
}

// eslint-disable-next-line no-unused-vars
const getColorsByPriceType = (priceTypes: Array<PriceType>) => priceTypes.sort(PriceType.localeCompare)
  .reduce((map: Map, pt, index) => {
    return map.set(pt.id, cssJS.PALLET_COLORS[index % cssJS.PALLET_COLORS.length])
  }, new Map())

// eslint-disable-next-line no-unused-vars
const getColors = (lines, colorsByPriceType: Map<number, string>) =>
  lines.map((l: ProductPriceLine) => colorsByPriceType.get(l.priceType.id))

// eslint-disable-next-line no-unused-vars
const getAvgMarkersColors = (avgPrices, colorsByPriceType: Map<number, string>) =>
  avgPrices.map((avg: ProductAvgPrice) => colorsByPriceType.get(avg.priceType.id))

const CustomSliceTooltip = (filter, previousYearAverages: Array<ProductAvgPrice>, avgColors: Array<string>,
  hideAvgPrices: Set<number>) =>
  ({slice, axis}) => {
  const { year } = filter
  const otherAxis = axis === 'x' ? 'y' : 'x';
  const dataProp = "".concat(otherAxis, "Formatted")
  const sliceData = slice.points.map((point) => ({
    color: point.serieColor,
    serieId: `${point.serieId} ${year}`,
    value: point.data[dataProp]
  })).concat(previousYearAverages.map((avg, index) => ({
    color: avgColors[index],
    serieId: getAveragePriceLabel(avg, year - 1),
    value: <FormattedNumber value={avg.average} maximumFractionDigits={0} />,
    hide: hideAvgPrices.has(avg.priceTypeId)
  })).filter(sd => !sd.hide))
  const rows = sliceData.map((sd) => {
    return [
      <Chip key="chip" color={sd.color}/>,
      sd.serieId,
      <span><strong key="value">{sd.value}</strong> CFA</span>,
    ];
  })
  const date:Date = slice.points[0].data.actualDate
  const month = date.getMonth() + 1
  const title = <strong>{date.getDate()} <FormattedMessage id={`all.month.${month}`} /></strong>
  return <TableTooltip title={title} rows={rows}/>
}


const mapStateToProps = state => {
  return {
    agricultureConfig: state.getIn(['agriculture', 'data', 'agricultureConfig'])
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductPrice))
