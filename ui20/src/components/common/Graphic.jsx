import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {PngExport} from "./Components"
import GraphicSource from "./GraphicSource"

class Graphic extends Component {
  static propTypes = {
    id: PropTypes.string.isRequired,
    titleId: PropTypes.string.isRequired,
    sourceId: PropTypes.string,
  }

  render() {
    const {id, titleId, sourceId, children, intl} = this.props;
    return (
      <div className="indicators chart section" id={id}>
        <div className="png exportable">
          <div className="indicator chart title poverty ">
            <p>
              <FormattedMessage id={titleId} />
            </p>
            <div className="indicator chart icon group">
              <PngExport
                name={intl.formatMessage({id: titleId})}
                id={id}
                filters={['filter', 'item', 'download']}
                includes={['active']}/>
            </div>
          </div>
          {children}
          {sourceId && GraphicSource(sourceId)}
        </div>
      </div>)
  }
}

const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Graphic))
