import React, {Component} from 'react'
import {Sticky,} from 'semantic-ui-react'


export default class StickyExampleActive extends Component {
  state = { active: true }

  handleToggle = () =>
    this.setState((prevState) => ({ active: !prevState.active }))

  render() {
    return (

      <Sticky active={true} context={this.props.context}>
            {this.props.children}
        </Sticky>


    )
  }
}
