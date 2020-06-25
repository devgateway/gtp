import {ResponsiveLine} from "@nivo/line"
import {TableTooltip} from '@nivo/tooltip'
import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import ProductQuantityFilter from "../../../modules/entities/product/quantity/ProductQuantityFilter"
import ProductQuantityChartDTO from "../../../modules/graphic/market/productQuantity/ProductQuantityChartDTO"
import Chip from "../../common/graphic/Chip"
import * as utils from "../../ComponentUtil"
import * as sccJS from "../../css"
import ProductQuantityLegend from "./ProductQuantityLegend"

class ProductQuantity extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(ProductQuantityChartDTO).isRequired,
    filter: PropTypes.object.isRequired,
  }

  render() {
    const {filter, intl} = this.props
    const data: ProductQuantityChartDTO = this.props.data

    const colors = utils.getColors(data.lines.length)

    return (
      <div>
        <ProductQuantityLegend data={data} colors={colors} />
        <div key="chart" className="graphic-content">
          <ResponsiveLine
            enableGridY={true}
            enableGridX={false}
            margin={sccJS.NIVO_CHART_WITH_CUSTOM_LEGEND_MARGIN}

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
            sliceTooltip={CustomSliceTooltip(filter, data.unit, colors)}

            axisLeft={{
              legendPosition: 'middle',
              legend: intl.formatMessage({id: "indicators.chart.product.quantity.legend.y"}, {unit: data.unit}),
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
            theme={sccJS.NIVO_THEME}
          />
        </div>
      </div>
    )
  }
}

const CustomSliceTooltip = (filter: ProductQuantityFilter, unit: string, colors: Array<string>) =>
  ({slice, axis}) => {
    const { year } = filter
    const otherAxis = axis === 'x' ? 'y' : 'x';
    const dataProp = "".concat(otherAxis, "Formatted")
    const rows = slice.points.map(point => {
      const value = point.data[dataProp]
      return [
        <Chip key="chip" color={point.serieColor}/>,
        point.serieId,
        <span><strong key="value">{value}</strong> {unit}</span>,
      ];
    })
    const date:Date = slice.points[0].data.actualDate
    const month = date.getMonth() + 1
    const title = <strong>{date.getDate()} <FormattedMessage id={`all.month.${month}`} /> {year}</strong>
    return <TableTooltip title={title} rows={rows}/>
  }


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantity))
