import {ResponsiveLine} from "@nivo/line"
import {TableTooltip} from '@nivo/tooltip'
import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, FormattedNumber, injectIntl} from "react-intl"
import {connect} from "react-redux"
import ProductAvgPrice from "../../../modules/entities/product/ProductAvgPrice"
import ProductPriceChartDTO from "../../../modules/graphic/market/productPrice/ProductPriceChartDTO"
import Chip from "../../common/Chip"
import * as sccJS from "../../css"
import CustomLegendSymbol, {LEGEND_SYMBOL_CIRCLE, LEGEND_SYMBOL_LINE} from "../../common/legend/CustomLegendSymbol"

class ProductPrice extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(ProductPriceChartDTO).isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    const { filter, intl } = this.props
    const data: ProductPriceChartDTO = this.props.data
    const { previousYearAverages } = data

    const colors = getColors(data.lines)
    const avgColors = getAvgMarkersColors(previousYearAverages)

    return (
      <div className="graphic-content">
        <ResponsiveLine
          enableGridY={true}
          enableGridX={false}
          margin={{ top: 50, right: 50, bottom: 75, left: 60 }}

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
          sliceTooltip={CustomSliceTooltip(filter, previousYearAverages, avgColors)}

          markers={previousYearAverages.map((avg, index) => ({
            axis: 'y',
            value: avg.average,
            lineStyle: {
              stroke: avgColors[index],
              strokeWidth: 2,
              strokeDasharray: "5,5"
            },
            // legend: intl.formatNumber(avg.average, {minimumFractionDigits: 0, maximumFractionDigits: 2}),
            legendOrientation: 'horizontal',
          }))}

          axisLeft={{
            legendPosition: 'middle',
            legend: intl.formatMessage({ id: `indicators.chart.product.price.unit.${data.product.unit.toLowerCase()}`}),
            legendOffset: -45,
            tickSize: 0,
            tickPadding: 5,
            tickRotation: 0,
          }}
          axisBottom={{
            format: "%m",
            tickValues: 'every month',
            tickSize: 10,
            legendOffset: 40,
            legendPosition: 'middle'
          }}
          curve="monotoneX"
          enablePoints={false}
          useMesh={true}
          colors={colors}
          animate={true}

          legends={[
            {
              data: data.lines.map(({id}, index) => ({
                id: index,
                label: id,
                color: colors[index],
              })),
              anchor: 'top-left',
              direction: 'row',
              justify: false,
              translateX: -35,
              translateY: -30,
              itemsSpacing: 0,
              itemWidth: 120,
              itemHeight: 20,
              itemOpacity: 0.75,
              symbolSize: 12,
              symbolShape: (legendProps) => {
                // const {isReference} = data.lines[legendProps.id].riverLevelYear
                // const type = isReference ? LEGEND_SYMBOL_LINE : LEGEND_SYMBOL_CIRCLE
                const type = LEGEND_SYMBOL_CIRCLE
                return <CustomLegendSymbol type={type} legendProps={legendProps}
                                           lineLength={sccJS.LEGEND_SYMBOL_LINE_LENGTH}/>
              },
              symbolBorderColor: 'rgba(0, 0, 0, .5)',
              // onClick: this.levelLineToggle.bind(this),
              effects: [
                {
                  on: 'hover',
                  style: {
                    itemBackground: 'rgba(0, 0, 0, .03)',
                    itemOpacity: 1
                  }
                }
              ]
            }
          ]}
          layers={['grid', 'markers', 'axes', 'areas', 'crosshair', 'lines', 'points', 'slices', 'mesh', 'legends',
            ]}
          theme={sccJS.NIVO_THEME}
        />
      </div>);
  }
}

const getColors = (lines) => {
  return lines.map((l, index) => {
    index = index % sccJS.PALLET_COLORS.length
    return sccJS.PALLET_COLORS[index]
  })
}

const getAvgMarkersColors = (avgPrices) => avgPrices.map((avg, index) =>
  sccJS.REFERENCE_COLORS[index % sccJS.REFERENCE_COLORS.length])

const CustomSliceTooltip = (filter, previousYearAverages: Array<ProductAvgPrice>, avgColors) =>
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
    serieId:
      <FormattedMessage id={`indicators.chart.product.price.average.${avg.priceType.name}`} values={{year: year - 1}} />,
    value: <FormattedNumber value={avg.average} maximumFractionDigits={0} />
  })))
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
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductPrice))
