import {Component} from "react"
import {injectIntl} from "react-intl"
import {connect} from "react-redux"

class ProductQuantity extends Component {
  static propTypes = {}

  render() {
    return 'TODO'
  }
}


const mapStateToProps = state => {
  return {
  }
}

const mapActionCreators = {
}

export default injectIntl(connect(mapStateToProps, mapActionCreators)(ProductQuantity))
