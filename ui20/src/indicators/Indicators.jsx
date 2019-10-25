import "./indicators.scss"

import connect from 'redux-connect-decorator'
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactDOM from 'react-dom';
import React, {Component, createRef, useState} from 'react'
import Plot from 'react-plotly.js';
import {
  Dropdown,
  Grid,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky
} from 'semantic-ui-react'

import Plot from 'react-plotly.js';

const region2options = (regions) => regions
  ? regions.sort((r1, r2) => r1.name.localeCompare(r2.name)).map(r => ({'key': r.code, 'text': r.name, 'value': r.id}))
  : []
const crop2options = (crops) => crops
  ? crops.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []
const year2options = () => [2017, 2018, 2019].map(y => ({'key': y, 'text': y, 'value': y}))

const CustomFilterDropDown = ({options, text}) => {
  const [selected, setSelected] = useState([]);
  const [open, setOpen] = useState(false);

  const updateSelection = (key) => {
    const newSelection = selected.slice(0)
    if (newSelection.indexOf(key) > -1) {
      newSelection.splice(newSelection.indexOf(key), 1);
    } else {
      newSelection.push(key)
    }
    setSelected(newSelection)
  }

  const getChecked = (key) => {
    return selected.indexOf(key) > -1
  }
  return (<Dropdown fluid="fluid" text={text} open={open} onOpen={() => setOpen(true)} onClose={(e) => {
      const keepOpen = !e || e.currentTarget.getAttribute("role") != 'listbox'
      setOpen(!keepOpen)

    }}>
    <Dropdown.Menu>
      <Dropdown.Header>
        <div>
          <div className="breadcrums">{text} <span> / {selected.length} of {options.length} </span>
          </div>
          <span className="all">Select All</span> <span>|</span> <span className="none">Select None</span>
        </div>
      </Dropdown.Header>
      <Dropdown.Divider/>
      <Dropdown.Menu scrolling="scrolling" className="filter options">
        {
          options.map(o =>< Dropdown.Item onClick = {
            e => updateSelection(o.key)
          } > <div className={"checkbox " + (
              getChecked(o.key)
              ? "checked"
              : "")}/>
            {o.text} < /Dropdown.Item>)}
      </Dropdown.Menu>
      <Dropdown.Divider/>
    </Dropdown.Menu>

  </Dropdown>)

}

const MainFilter = ({regions, crops}) => (<div className="core-filters">
  <div className="main filter nav">
    <div className="filter nav item title">
      <FormattedMessage id="indicators.main_filter_title" defaultMessage="Indicator Filter"></FormattedMessage>
    </div>
    <div className="filter nav separator"></div>
    <div className="filter nav item">
      <CustomFilterDropDown text={<FormattedMessage id = "indicators.main.filter.year" defaultMessage = "Campain/Years" > </FormattedMessage>} options={year2options()}></CustomFilterDropDown>
    </div>
    <div className="filter nav item">
      <CustomFilterDropDown text={<FormattedMessage id = "indicators.main.filter.regions" defaultMessage = "Regions" > </FormattedMessage>} options={region2options(regions)}></CustomFilterDropDown>
    </div>
    <div className="filter nav item">
      <CustomFilterDropDown text={<FormattedMessage id = "indicators.main.filter.crops" defaultMessage = "Crop Type" > </FormattedMessage>} options={crop2options(crops)}></CustomFilterDropDown>
    </div>
  </div>
</div>)



const GlobalIndicators=(props)=>{

  return (
    <Grid className="indicator global numbers" columns={4} divided>
      <Grid.Row>
          {[1,2,3,4].map(n=>(
        <Grid.Column>
          <div className="indicator big number">
            2.5M
          </div>
          <div className="indicator description">
            <div className="indicator logo"><Image src="/sdg/poverty.png"></Image></div>
            <div className="indicator name">Proportion of population below the internationalpoverty line</div>
          </div>

        </Grid.Column>))}
      </Grid.Row>
      <Grid.Row>
          {[1,2,3,4].map(n=>(
        <Grid.Column>
          <div className="indicator link">
            <div className="btn">
              <div className="icon go"/>
              <a href=""><FormattedMessage id="indicator.go.chart" defaultMessage="Go to Chart"/></a>
          </div>
          </div>
        </Grid.Column>))}
      </Grid.Row>

    </Grid>)
}

class Indicators extends Component {
  render() {
    return (<div>
      <MainFilter {...this.props}></MainFilter>
      <div className="indicators content fixed ">
        <div className="indicators title">
          <span>
            <FormattedMessage id="indicators.title" defaultMessage="Indicators"></FormattedMessage>
          </span>
        </div>
        <div className="indicators global intro">
          <FormattedMessage id="indicators.global.intro" defaultMessage={`Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled`}></FormattedMessage>
        </div>

          <GlobalIndicators></GlobalIndicators>

        <Segment className="info-text">
          <div className="source-icon"></div>
          Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
        </Segment>

        <Grid columns={2} className="info-text table">
          <Grid.Row>
              {[1,2].map(n=>(
                  <Grid.Column>

                    Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.

                  </Grid.Column>))}

          </Grid.Row>

        </Grid>


          <Segment>
              <div className="indicator chart title"></div>
          </Segment>

          <Plot
                 data={[
                   {
                     x: [1, 2, 3],
                     y: [2, 6, 3],
                     type: 'scatter',
                     mode: 'lines+points',
                     marker: {color: 'red'},
                   },
                   {type: 'bar', x: [1, 2, 3], y: [2, 5, 3]},
                 ]}
                 layout={ {width: 320, height: 240, title: 'A Fancy Plot'} }
               />
      </div>
    </div>)
  }
}

const mapStateToProps = state => {

  return {
    regions: state.getIn(['data', 'items', 'region']),
    crops: state.getIn(['data', 'items', 'cropType'])
  }
}

const mapActionCreators = {};

export default injectIntl(connect(mapStateToProps, mapActionCreators)(Indicators));
