import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"
import {Loader} from "semantic-ui-react"
import NoData from "./NoData"

const emptyOrContent = (asEmptyDiv: boolean, contentBuilder: function) => asEmptyDiv ? <div /> : contentBuilder()

class GraphicWithFallbackComponent extends Component {
  static propTypes = {
    isLoaded: PropTypes.bool,
    isLoading: PropTypes.bool,
    error: PropTypes.object,
    childPropsBuilder: PropTypes.func.isRequired,
    hasDataFunc: PropTypes.func.isRequired,
    asEmptyDiv: PropTypes.bool,
  }

  static defaultProps = {
    asEmptyDiv: false,
  }

  render() {
    const {isLoaded, isLoading, graphicFiltering, graphicFiltered, error, hasDataFunc, childPropsBuilder, children,
      asEmptyDiv} = this.props

    if (isLoading !== false || graphicFiltering || (!isLoaded && !error)) {
      return emptyOrContent(asEmptyDiv, () => <NoData><Loader active inline='centered' indeterminate /></NoData>)
    }

    if (!graphicFiltered) {
      return emptyOrContent(asEmptyDiv, () => <NoData messageId="all.data-error"/>)
    }

    const childProps = childPropsBuilder(this.props)

    if (!hasDataFunc(childProps)) {
      return emptyOrContent(asEmptyDiv, () => <NoData messageId="all.no-data"/>)
    }
    return <div>{children({...childProps, ...this.props})}</div>
  }

}


const GraphicWithFallback = (reducerRoot: string, graphicFiltering: string, graphicFiltered: string,
  childPropsBuilder: function, hasDataFunc: function) => {

  const mapStateToProps = state => {
    return {
      isLoaded: state.getIn([reducerRoot, 'isLoaded']),
      isLoading: state.getIn([reducerRoot, 'isLoading']),
      error: state.getIn([reducerRoot, 'error']),
      graphicFiltering: state.getIn([reducerRoot, graphicFiltering]),
      graphicFiltered: state.getIn([reducerRoot, graphicFiltered]),
      childPropsBuilder,
      hasDataFunc,
    }
  }

  const mapActionCreators = {
  }

  return injectIntl(connect(mapStateToProps, mapActionCreators)(GraphicWithFallbackComponent))
}

export default GraphicWithFallback
