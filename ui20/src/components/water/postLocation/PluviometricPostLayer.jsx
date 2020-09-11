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
  }

  render() {
    const {intl} = this.props
    const attribution = intl.formatMessage({ id: "indicators.map.post.source" })

    return (
      <div>
        {this.props.postMapDTO.posts.map((p: PluviometricPostDTO) => {
          const color = COLOR_BLUE

          return (
            <CircleMarker
              key={p.id}
              attribution={attribution}
              center={[p.latitude, p.longitude]}
              color={color}
              fillOpacity={1}
              radius={5} >
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
