import PropTypes from 'prop-types'
import {Component} from 'react'
import {connect} from 'react-redux'

class FMCheck extends Component {
  static propTypes = {
    fmEntry: PropTypes.string.isRequired,
    onEnabledComponentBuilder: PropTypes.func.isRequired,
    onFMLoadingComponentBuilder: PropTypes.func,
    onDisabledComponentBuilder: PropTypes.func,
  }

  render() {
    const {isFMConfigLoaded, fmConfig, fmEntry, onEnabledComponentBuilder, onFMLoadingComponentBuilder,
      onDisabledComponentBuilder} = this.props
    if (!isFMConfigLoaded || !fmConfig) {
      return onFMLoadingComponentBuilder ? onFMLoadingComponentBuilder() : null
    }
    if (!fmConfig.has(fmEntry)) {
      return onDisabledComponentBuilder ? onDisabledComponentBuilder() : null
    }
    return onEnabledComponentBuilder()
  }
}

const mapStateToProps = state => {
  return {
    isFMConfigLoaded: state.getIn(['app', 'isFMConfigLoaded']),
    fmConfig: state.getIn(['app', 'data', 'fmConfig']),
  }
}

const mapActionCreators = {
}

export default connect(mapStateToProps, mapActionCreators)(FMCheck)
