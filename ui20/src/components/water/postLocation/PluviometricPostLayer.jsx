import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {CircleMarker, Tooltip} from "react-leaflet"
import PluviometricPostDTO from "../../../modules/graphic/water/postMap/PluviometricPostDTO"
import PluviometricPostMapDTO from "../../../modules/graphic/water/postMap/PluviometricPostMapDTO"
import {COLOR_BLUE} from "../../common/map/MapUtils"

export default class PluviometricPostLayer extends Component {
  static propTypes = {
    postMapDTO: PropTypes.instanceOf(PluviometricPostMapDTO).isRequired,
    circleColor: PropTypes.string,
    circleRadius: PropTypes.number,
  }

  static defaultProps = {
    circleColor: COLOR_BLUE,
    circleRadius: 5,
  }

  render() {
    const {intl, circleColor, circleRadius} = this.props
    const attribution = intl.formatMessage({ id: "indicators.map.post.source" })

    return (
      <div>
        {this.props.postMapDTO.posts.map((p: PluviometricPostDTO) => {

          return (
            <CircleMarker
              key={p.id}
              attribution={attribution}
              center={[p.latitude, p.longitude]}
              color={circleColor}
              fillOpacity={1}
              radius={circleRadius} >
              <Tooltip className="black">
                <div className="tooltips black">
                  <div className="tooltip-title">
                    <FormattedMessage id="indicators.map.post.tooltip.post" values={{
                      post: p.label
                    }}/>
                  </div>
                  <div className="ui divider" />
                  <div className="department">
                    <FormattedMessage id="indicators.map.post.tooltip.department" values={{
                      department: p.department.name
                    }}/>
                  </div>
                </div>
              </Tooltip>
            </CircleMarker>
          )
        })}
      </div>
    );
  }
}
