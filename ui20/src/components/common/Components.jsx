import React, { useState} from 'react'
import {FormattedMessage} from 'react-intl';
import  {Range} from 'rc-slider';
import DatePicker from "react-datepicker";
// import { toPng} from 'html-to-image';
import download from 'downloadjs'
import {Dropdown} from 'semantic-ui-react'
import "react-datepicker/dist/react-datepicker.css";
import DropdownBreadcrumb from "./filter/DropdownBreadcrubmb"
import FilterDropDown from "./filter/FilterDropDown"

const toPng = () => {}
export const PngExport = ({id, element, name = "chart", filters = [],includes = []})=>{


  // eslint-disable-next-line no-unused-vars
  const changedNodes = []
      return (<div className="icon download masked-icon icon-download" onClick={e=>{

        var node = id ? document.getElementById(id) : document[element];

        var exportable = id ? node.getElementsByClassName("png exportable")[0] : node

        const doFilter = (node)=>{

          if (node.classList && ([...node.classList].map(l=>filters.indexOf(l) > -1).filter(n=>n).length > 0 ) && !([...node.classList].map(l=>includes.indexOf(l) > -1).filter(n=>n).length > 0 ) ) {
            return false
          }
          return true
        }


      toPng(exportable, {backgroundColor:"#FFF",filter:doFilter, style:{'border':'0px !important'}})
          .then(function (dataUrl) {
            download(dataUrl, name + '.png');

          })
          .catch(function (error) {
            console.error('oops, something went wrong!', error);
          });


      }}></div>)
}


export const TextInput = ({onChange, value, text})=>{
    return (<input type="text" placeholder={text} onChange={e=>onChange(e.target.value)} value={value}/>)
}


export const DateInput = ({onChange, value, text, locale})=>{

    return (<DatePicker
            locale={locale}

            placeholderText={text}
            selected={value}
            onChange={onChange}/>)
}


export const CustomFilterDropDown = (props) => (<FilterDropDown {...props} />)


/* ---------------------------*/
export const CustomGroupedDropDown = ({options, selected, onChange, text, disabled, single}) => {

  const [open, setOpen] = useState(false);

  const plainOptions = []
  options.forEach(o=>o.options.forEach(i=>plainOptions.push(i)))


          // eslint-disable-next-line no-unused-vars
  const selectedText = selected[0]

  const breadcrum = DropdownBreadcrumb(options, selected, text, single)

  const updateSelection = (key) => {
    var newSelection = selected.slice(0)
    if (newSelection.indexOf(key) > -1) {
      newSelection.splice(newSelection.indexOf(key), 1);
    } else {
      if (single) {
        newSelection = [key]

      } else {
      newSelection.push(key)
      }
    }
    onChange(newSelection)
  }

  const getChecked = (key) => {

    return selected.indexOf(key) > -1
  }

  const allNone = (flag) => {
    if (flag === false) {
      onChange([])
    } else {
      const newSelection = []
      options.map(o => newSelection.push(o.key))
      onChange(newSelection)
    }
  }



  return (

    <Dropdown className={disabled ? "grouped dropdown disabled" : "grouped dropdown"} fluid text={breadcrum} open={open} onOpen={() => setOpen(true)} onClose={(e) => {
      const keepOpen = !e || !e.currentTarget || !e.currentTarget.getAttribute || e.currentTarget.getAttribute("role") !== 'listbox'
      setOpen(!keepOpen)
    }}>

    <Dropdown.Menu>
    {(single == null || single === false) && <Dropdown.Header>
        <div>

          <span className="all" onClick={e=>allNone(true)}><FormattedMessage id='indicators.filters.select_all' defaultMessage="Select All"/></span>
          <span> | </span>
          <span className="none" onClick={e=>allNone(false)}><FormattedMessage id='indicators.filters.select_none' defaultMessage="Select None"/></span>
        </div>

      </Dropdown.Header>
        }


      <Dropdown.Menu scrolling="scrolling" className="filter options">

      {options.sort((a,b)=>a.group.localeCompare(b.group)).map(o=>{

          return <div>
            <Dropdown.Item>{o.group}

            {o.options.sort((a,b)=>a.text.localeCompare(b.text)).map(o =>< Dropdown.Item onClick = {e => updateSelection(o.key)} > <div className={"checkbox " + (getChecked(o.key) ? "checked" : "")}/> {o.text} </Dropdown.Item>)}


            </Dropdown.Item>

            <Dropdown.Divider/>
          </div>

      })}

      </Dropdown.Menu>

      <Dropdown.Divider/>

    </Dropdown.Menu>

  </Dropdown>)}


  /* ---------------------------*/


export const ChartTableSwitcher = (props) =>(
  <div className="chart toggler view">
    <div class="ui toggle checkbox">
      <div className={props.mode === 'chart' ? 'active' : ''}>
        <FormattedMessage id="indicators.chart.toggler.chart" defaultMessage="chart"></FormattedMessage>
      </div>
      <input type="checkbox" onChange={e => null} name="view" defaultChecked={props.mode === 'table'}/>
      <label></label>
      <div className={props.mode === 'table' ? 'active' : ''}>
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

  return (<div className={`indicator filter ${disabled ? 'disabled' : ''} options  age`}>
    <p>{text}</p>
    {
      options.map((a) => {
        return <div key={a.key} onClick={e => {if (!disabled) {updateSelection(a.key)}}} className={`item ${getChecked(a.key)
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
