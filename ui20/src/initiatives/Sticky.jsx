import _ from 'lodash'
import React, { Component, createRef } from 'react'
import {
  Checkbox,
  Grid,
  Header,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky,
} from 'semantic-ui-react'

const Placeholder = () => <Image src='https://react.semantic-ui.com/images/wireframe/paragraph.png' />

export default class StickyExampleActive extends Component {
  state = { active: true }

  handleToggle = () =>
    this.setState((prevState) => ({ active: !prevState.active }))

  render() {
    const { active } = this.state

    return (

      <Sticky active={true} context={this.props.context}>
            {this.props.children}
        </Sticky>


    )
  }
}