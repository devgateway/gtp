import React, {Component, createRef, useState} from 'react'

import {FormattedMessage} from 'react-intl';

import {
  Dropdown,
  Grid,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky
} from 'semantic-ui-react'

export const CustomFilterDropDown = ({options, selected, onChange, text}) => {
  const [open, setOpen] = useState(false);

  const breadcrum=(<div className="breadcrums">{text} {true?<span>({selected.length} of {options.length})</span>:null}</div>)

  const updateSelection = (key) => {
    const newSelection = selected.slice(0)
    if (newSelection.indexOf(key) > -1) {
      newSelection.splice(newSelection.indexOf(key), 1);
    } else {
      newSelection.push(key)
    }
    onChange(newSelection)
  }

  const getChecked = (key) => {
    return selected.indexOf(key) > -1
  }

  const allNone = (flag) => {
    if (flag == false) {
      onChange([])
    } else {
      const newSelection = []
      options.map(o => newSelection.push(o.key))
      onChange(newSelection)
    }
  }

  return (<Dropdown fluid="fluid" text={breadcrum} open={open} onOpen={() => setOpen(true)} onClose={(e) => {
      const keepOpen = !e || e.currentTarget.getAttribute("role") != 'listbox'
      setOpen(!keepOpen)
    }}>
    <Dropdown.Menu>
      <Dropdown.Header>
        <div>

          <span className="all" onClick={e=>allNone(true)}>Select All</span>
          <span> | </span>
          <span className="none" onClick={e=>allNone(false)}>Select None</span>
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

export const Slider = () => {}

export const ChartTableSwitcher = (props) =>


(
  <div className="chart toggler view">
    <div class="ui toggle checkbox">
      <div className={props.mode=='chart'?'active':''}>
        <FormattedMessage id="indicators.chart.toggler.chart" defaultMessage="chart"></FormattedMessage>
      </div>
      <input type="checkbox" onChange={e => null} name="view" defaultChecked={props.mode=='table'}/>
      <label></label>
      <div className={props.mode=='table'?'active':''}>
        <FormattedMessage id="indicators.chart.toggler.table" defaultMessage="table"></FormattedMessage>
      </div>
    </div>
 </div>
)
