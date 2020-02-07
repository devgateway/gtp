import React, {Component, createRef, useState} from 'react'

import {FormattedMessage} from 'react-intl';
import Slider, {Range} from 'rc-slider';
import DatePicker from "react-datepicker";
import { toPng, toJpeg, toBlob, toPixelData, toSvgDataURL } from 'html-to-image';
import download from 'downloadjs'

import {
  Dropdown,
  Grid,
  Image,
  Rail,
  Ref,
  Segment,
  Sticky
} from 'semantic-ui-react'


import "react-datepicker/dist/react-datepicker.css";




export const PngExport=({id})=>{
      return (<div className="indicator chart icon download png" onClick={e=>{
        var node = document.getElementById(id);

                toPng(node.getElementsByClassName("chart container")[0])
                  .then(function (dataUrl) {
                    download(dataUrl, 'chart.png');
                  })
                  .catch(function (error) {
                    console.error('oops, something went wrong!', error);
                  });

      }}></div>)
}



export const TextInput=({onChange, value, text})=>{
    return (<input type="text" placeholder={text} onChange={e=>onChange(e.target.value)} value={value}/>)
}


export const DateInput=({onChange, value, text, locale})=>{

    return (<DatePicker
            locale={locale}

            placeholderText={text}
            selected={value}
            onChange={onChange}/>)
}



export const CustomFilterDropDown = ({options, selected, onChange, text, disabled, single}) => {

  const [open, setOpen] = useState(false);




  const breadcrum=single?(<div className="breadcrums">{selected && selected.length > 0? options[  options.map(a=>a.key).indexOf(selected[0]) ].text:text}</div>):(<div className="breadcrums">{text} {true?<span>({selected.length} of {options.length})</span>:null}</div>)


  const updateSelection = (key) => {

    var newSelection = selected.slice(0)
    if (newSelection.indexOf(key) > -1) {
      newSelection.splice(newSelection.indexOf(key), 1);
    } else {
      if(single){
        newSelection=[key]

      }else{
      newSelection.push(key)
      }
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


  return (<Dropdown className={disabled?"disabled":""} fluid text={breadcrum} open={open} onOpen={() => setOpen(true)} onClose={(e) => {
      const keepOpen = !e || !e.currentTarget || e.currentTarget.getAttribute("role") != 'listbox'
      setOpen(!keepOpen)
    }}>
    <Dropdown.Menu>
    {(single==null || single==false)&&    <Dropdown.Header>
        <div>

          <span className="all" onClick={e=>allNone(true)}><FormattedMessage id='indicator.filters.select_all' defaultMessage="Select All"/></span>
          <span> | </span>
          <span className="none" onClick={e=>allNone(false)}><FormattedMessage id='indicator.filters.select_none' defaultMessage="Select None"/></span>
        </div>

      </Dropdown.Header>
        }
      <Dropdown.Divider/>
      <Dropdown.Menu scrolling="scrolling" className="filter options">
        {
          options.map(o =>< Dropdown.Item onClick = {e => updateSelection(o.key)} > <div className={"checkbox " + (
              getChecked(o.key)
              ? "checked"
              : "")}/>
            {o.text} < /Dropdown.Item>)}
      </Dropdown.Menu>
      <Dropdown.Divider/>
    </Dropdown.Menu>

  </Dropdown>)}


export const ChartTableSwitcher = (props) =>(
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




export const OptionList = ({options, selected, onChange, text, disabled}) => {
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

  return (<div className={`indicator filter ${disabled?'disabled':''} options  age`}>
    <p>{text}</p>
    {
      options.map((a) => {
        return <div key={a.key} onClick={e => {if(!disabled) {updateSelection(a.key)}}} className={`item ${getChecked(a.key)
            ? 'active'
            : ''}`}>
          <div className="checkbox"></div>
          <div className="label">{a.text}</div>
        </div>
      })
    }
  </div>)
}

export const RangeSlider = ({max,min,step,selected,onChange,text}) => {

  return (<div className="slider container">
    <p>{text}</p>
    <div>
      <Range step={1} dots={false} value={selected} min={min} max={max} onChange={onChange}/>
      <span className="breadcrumbs min">Min: {selected[0]}</span>
      <span className="breadcrumbs max">Max: {selected[1]}
      </span>
    </div>
  </div>)
}
