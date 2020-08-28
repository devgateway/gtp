import React, {Component, createRef} from "react"
import PropTypes from "prop-types"

export default class TrackVisibility extends Component {
  static propTypes = {
    onVisible: PropTypes.func.isRequired,
    thresholds: PropTypes.arrayOf(PropTypes.number).isRequired,
  }

  ref = createRef()

  async componentDidMount() {
    const ascThresholds = this.props.thresholds.sort()
    const descThresholds = ascThresholds.reverse()

    const observer = new IntersectionObserver(
      ([entry]) => {
        const threshold = descThresholds.find(t => t <= entry.intersectionRatio)
        if (threshold) {
          this.props.onVisible(threshold, entry.intersectionRatio)
        }
      }, {
        root: null,
        rootMargin: '0px',
        threshold: ascThresholds,
      }
    )

    if (this.ref.current) {
      observer.observe(this.ref.current)
    }
  }

  render() {
    return <div ref={this.ref}>{this.props.children}</div>
  }

}
