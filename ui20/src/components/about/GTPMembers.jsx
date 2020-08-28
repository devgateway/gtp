import * as PropTypes from "prop-types"
import React, {Component} from "react"
import {Grid, GridColumn, GridRow} from "semantic-ui-react"
import * as EPConstants from "../../modules/api/EPConstants"
import Member from "../../modules/entities/member/Member"
import "./member.scss"
import MemberData from "../../modules/entities/member/MemberData"

export default class GTPMembers extends Component {
  static propTypes = {
    memberData: PropTypes.instanceOf(MemberData).isRequired,
  }

  render() {
    const memberData: MemberData = this.props.memberData
    const noImage = this.props.intl.formatMessage({ id: "all.no-image"})

    return (
      <Grid className="gtp-members">
        {memberData.members.map(m => <GTPMember key={m.id} member={m} noImage={noImage} />)}
      </Grid>
    );
  }
}

class GTPMember extends Component {
  static propTypes = {
    member: PropTypes.instanceOf(Member).isRequired,
    noImage: PropTypes.string.isRequired,
  }

  render() {
    const member: Member = this.props.member

    return (
      <GridRow columns={2} className="member-row">
        <GridColumn textAlign="center" verticalAlign="middle" className="img-col">
          <img src={`${EPConstants.MEMBER_LOGO}${member.id}`} alt={this.props.noImage}/>
        </GridColumn>
        <GridColumn textAlign="left" className="details-col">
          <div className="name">{member.name}</div>
          <p>{member.description}</p>
          <p>
            <a href={member.url}>{member.url}</a>
          </p>
        </GridColumn>
      </GridRow>
    )
  }

}
