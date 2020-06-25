import {ResponsiveLine} from "@nivo/line"
import PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import ProductQuantityChartDTO from "../../../modules/graphic/market/productQuantity/ProductQuantityChartDTO"
import * as sccJS from "../../css"
import * as utils from "../../ComponentUtil"
import ProductQuantityLegend from "./ProductQuantityLegend"

class ProductQuantity extends Component {
  static propTypes = {
    data: PropTypes.instanceOf(ProductQuantityChartDTO).isRequired,
  }

  render() {
    const {intl} = this.props
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


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantity))
