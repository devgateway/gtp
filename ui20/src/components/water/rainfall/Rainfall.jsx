import {ResponsiveBar} from "@nivo/bar"
import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import messages from "../../../translations/messages"

class Rainfall extends Component {
  static propTypes = {
    data: PropTypes.array,
    keys: PropTypes.array,
    groupMode: PropTypes.string,
    indexBy: PropTypes.string,
    colors: PropTypes.object,
  }

  componentDidMount() {
  }

  render() {
    const {data, keys, indexBy, groupMode, colors, intl, byDecadal} = this.props

    return (<div className="png exportable chart container">
      <ResponsiveBar
        data={data}
        keys={keys}
        indexBy={indexBy}
        groupMode={groupMode}
        colors={colors}
        minValue={0}
        margin={{ top: 50, right: 130, bottom: 80, left: 60 }}
        padding={0.3}
        innerPadding={3}
        fill={[]}
        borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
        axisTop={null}
        axisRight={null}
        axisBottom={{
          tickSize: 0,
          tickPadding: 5,
          tickRotation: 0,
          legend: intl.formatMessage(byDecadal ? messages.decadals : messages.months),
          legendPosition: 'middle',
          legendOffset: 52,
        }}
        axisLeft={{
          tickSize: 0,
          tickPadding: 5,
          tickRotation: 0,
          legend: intl.formatMessage(messages.rainfall),
          legendPosition: 'middle',
          legendOffset: -40
        }}
        enableLabel={false}

        tooltip={(s)=>{
          return (<div className="tooltips white">
            <div className="color" style={{backgroundColor:s.color}} />
            <div className="label">{s.indexValue}</div>
            <div className='x'>{s.id}</div>
            <div className='y' style={{'color':s.color}}>{s.value}</div>
          </div>)
        }}

        legends={[
          {
            dataFrom: 'keys',
            anchor: 'top-right',
            direction: 'row',
            justify: false,
            translateX: 120,
            translateY: -30,
            itemsSpacing: 2,
            itemWidth: 100,
            itemHeight: 20,
            itemDirection: 'right-to-left',
            itemOpacity:1,
            symbolSize: 20,
            effects: [
              {
                on: 'hover',
                style: {
                  itemOpacity: 1
                }
              }
            ]
          }
        ]}
        animate={true}
        motionStiffness={90}
        motionDamping={15}
      />
    </div>)
  }
}


const mapStateToProps = state => {
  return {

  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Rainfall))
