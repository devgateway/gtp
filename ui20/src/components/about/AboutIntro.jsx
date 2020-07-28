import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import "../common/common.scss"
import {Segment} from "semantic-ui-react"

export default class AboutIntro extends Component {

  render() {
    return (
      <div className="page-container">
        <Segment className="page-header bottom-border">
          <Segment className="title">
            <FormattedMessage id="about.title"/>
          </Segment>
        </Segment>
        <p className="page-subtitle">
          <FormattedMessage id="about.subtitle"/>
        </p>
        <p>
          <FormattedMessage id="about.description.paragraph_1"/>
        </p>
        <p className="bottom-border">
          <FormattedMessage id="about.description.paragraph_2"/>
        </p>
      </div>
    )
  }
}
