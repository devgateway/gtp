import React, { useEffect ,Component,useState, createRef} from 'react'
import {loadPartners} from '../modules/Data'
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import Sticky from './Sticky'
import {
  Dropdown,
  Grid,
  Image,
  Ref,
  Rail,
  Segment,
  Container,
  Label,
  Menu,
  Input
} from 'semantic-ui-react'

import './partners.scss'

const contextRef = createRef()

const Partners = ({intl, onLoad , groups , partners}) => {

  useEffect(() => {
    if (onLoad){
        onLoad(intl.locale)
    }

  }, [])
return (<div className="partners container">
    <div className="partners title">
      <p>
        <FormattedMessage id="partners.page.title" defaultMessage="Partners"></FormattedMessage>
      </p>
    </div>

    <div className="partners description">
      <p>  <FormattedMessage id='partners.page.description' defaultMessage="This page displays the list of partners that collaborated to this initiative. Partner organizations have been grouped by type to ease up the search functionality."/></p>
    </div>

    
    <Container fluid>
    <Ref innerRef={contextRef}>

    <Grid columns={2} divided padded fluid>

        <Grid.Column floated='left' width={5} ref={contextRef}>

          <Sticky context={contextRef}>
          {groups && <ListMenu className="ui sidebar" groups={groups} onChangeSelection={e=>null}/>}
          </Sticky>
        </Grid.Column>

        <Grid.Column floated='right' width={11}>
          <Container className=''>

            {groups && <ListItems groups={groups} onChangeSelection={e=>null}/>}

          </Container>
        </Grid.Column>

      </Grid>
      </Ref>
</Container>

    </div>)
}


const ListItems=({groups,onChangeSelection})=>{
 const [active, setActive] = useState(groups[0].name);
 const [selected, setSelected] = useState(groups?groups[0].partners[0].id:null);



  return (<Container fluid>
          {groups && groups.map(g=>{

              return (
                <Container fluid>

                    {g.partners.map(p=>{
                        return (
                          <Container  fluid className="partners details">
                            <Grid padded fluid size={2}>

                                    <Grid.Row id={"_partner_"+p.id}>
                                        <Grid.Column  width={11}>
                                        {p.base64&&  <a href={p.url} className="ui image medium padding5">
                                            <img src={`data:image/png;base64,${p.base64}`}/>
                                          </a>}
                                        </Grid.Column>
                                        <Grid.Column  width={5} className="align right link">
                                          <a href={p.url}>{p.url}</a>
                                        </Grid.Column>
                                    </Grid.Row>
¡
                                      <Grid.Row>
                                        <Grid.Column >
                                            <Label basic className="name">{p.name}</Label>
                                            <div  className="description" dangerouslySetInnerHTML={{__html: p.description}}></div>
                                          </Grid.Column>

                                      </Grid.Row>

                                      <Grid.Row>
                                        <Grid.Column >

                                            <div  className="info" dangerouslySetInnerHTML={{__html: p.contactInfo}}></div>
                                            {p.url}
                                          </Grid.Column>

                                      </Grid.Row>
                                </Grid>

                          </Container>
                        )

                    })}

                </Container>
                )

          })}
        </Container>)
}



const ListMenu=({groups,onChangeSelection})=>{
 const [active, setActive] = useState(groups[0].id);
 const [selected, setSelected] = useState(groups?groups[0].partners[0].name:null);

 const goTo=(id)=>{
   document.getElementById("_partner_"+id)
   .scrollIntoView({behavior:  "smooth",block:    "start"});
 }

  return (
    <Menu vertical fixed fluid className="menu level1">
    {groups && groups.map(g=>{
      return (<Menu.Item fluid name='inbox' active={active === g.id} onClick={(e)=>{

        setActive(g.id)
        setSelected(g.partners[0].name)
        onChangeSelection(g.partners[0].id)
        goTo(g.partners[0].id)

      }}>
        <Label color='teal'>{g.partners.length}</Label>
          <p>{g.name}</p>

          {active === g.id &&<Menu vertical fluid className="menu level2">
          { g.partners.map(p=>(

            <Menu.Item fluid active={selected === p.name} onClick={(e)=>{
              goTo(p.id)
              setSelected(p.name)
              onChangeSelection(p.id)
              e.stopPropagation()
            }}>
            <p>{p.name}</p>
            </Menu.Item>
          ))}

        </Menu>}
      </Menu.Item>)
    })}
    </Menu>
  )
}

const mapStateToProps = (state, ownProps) => {
  const partners=state.getIn(['data','partners','data'])
  const groups=state.getIn(['data','partners','groups'])

  return {
    partners,
    groups
  }
}


const mapActionCreators = {
  onLoad:loadPartners
};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Partners));
