import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {PngExport} from "../ipar/indicators/Components"

class Graphic extends Component {
  static propTypes = {
    id: PropTypes.number.isRequired,
    titleId: PropTypes.string.isRequired,
    sourceId: PropTypes.string.isRequired,
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
              <PngExport name={intl.formatMessage({id: titleId})}
                         id="anchor.indicator.water.rainfall" filters={['filter', 'item', 'download']}
                         includes={['active']}/>
            </div>
          </div>
          {children}
          <div className="source">
            <span className="source label">
              <FormattedMessage id="data.fields.source_label" defaultMessage="Source: "/>
            </span>
            {sourceId ? <FormattedMessage id={sourceId} />
              : <FormattedMessage id="data.fields.source_undefined" defaultMessage="Not specified"/>}
          </div>
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
