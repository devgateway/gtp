import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage, injectIntl} from "react-intl"
import {connect} from "react-redux"
import {cssClasses} from "../../ComponentUtil"
// import {PngExport} from "../Components"
// import PngExport from "../PngExport"
import PngExport from "../PngExportDomToImage"
import GraphicSource from "./GraphicSource"

class Graphic extends Component {
  static propTypes = {
    id: PropTypes.string.isRequired,
    titleId: PropTypes.string.isRequired,
    sourceId: PropTypes.string,
    className: PropTypes.string,
  }

  render() {
    const {id, titleId, sourceId, children, intl, className} = this.props;
    return (
      <div className={cssClasses("indicators chart section", className)} id={id}>
        <div className="png exportable">
          <div className="indicator chart title">
            <p>
              <FormattedMessage id={titleId} />
            </p>
            <div className="indicator chart icon group" data-html2canvas-ignore>
              <PngExport
                name={intl.formatMessage({id: titleId})}
                id={id}
                filters={[]}
                includes={['active']}/>
            </div>
          </div>
          <div className="png exportable chart container">
            {children}
            {sourceId && GraphicSource(sourceId)}
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
