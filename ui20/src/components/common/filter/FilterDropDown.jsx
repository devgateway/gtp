import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Dropdown, Input} from "semantic-ui-react"
import DropdownBreadcrumb from "./DropdownBreadcrubmb"

export default class FilterDropDown extends Component {
  static propTypes = {
    options: PropTypes.array.isRequired,
    selected: PropTypes.array.isRequired,
    onChange: PropTypes.func.isRequired,
    text: PropTypes.any.isRequired,
    disabled: PropTypes.bool,
    single: PropTypes.bool,
    min: PropTypes.number,
    max: PropTypes.number,
    withSearch: PropTypes.bool,
  }

  static defaultProps = {
    disabled: false,
    single: false,
    min: 0,
    withSearch: false,
  }

  constructor(props) {
    super(props)
    this.state = {
      open: false,
      id: `dropdown_${Math.random()}`,
      isLocalStateChange: false,
    }
  }

  static getDerivedStateFromProps(props, state) {
    const {isLocalStateChange} = state
    if (isLocalStateChange) {
      return {
        isLocalStateChange: false
      }
    }

    const {options} = props
    options.forEach(o => o.lowerText = `${o.text}`.toLowerCase())
    return {
      options,
      defaultOptions: options
    }
  }

  setFilterState(state) {
    this.setState({...state, isLocalStateChange: true})
  }

  setOpen(open) {
    this.setFilterState({ open })
  }

  updateSelection(key) {
    const {onChange, selected, single} = this.props
    let newSelection = selected.slice(0)
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

  allNone(flag) {
    const {onChange, options} = this.props
    if (flag === false) {
      onChange([])
    } else {
      const newSelection = []
      options.map(o => newSelection.push(o.key))
      onChange(newSelection)
    }
  }

  onSearchChange(value) {
    const {defaultOptions} = this.state
    value = value && value.trim().toLowerCase()
    if (!value) {
      this.setFilterState({ options: defaultOptions })
    } else {
      this.setFilterState({options: defaultOptions.filter(({ lowerText }) => lowerText.includes(value) )})
    }
  }

  render() {
    const {selected, text, disabled, single, min, max, withSearch} = this.props
    const {open, id, options, defaultOptions} = this.state

    const breadcrum = DropdownBreadcrumb(defaultOptions, selected, text, single)
    const allowSelectNone = !single && !min
    const allowSelectAll = !single && (!max || max >= options.length)
    const allowDeselect = !min || (!!min && selected.length > min)
    const allowSelect = single || !max || (!!max && selected.length < max)

    const isKeepOpen = (e: Event) => !!(e && e.currentTarget && e.currentTarget.getAttribute &&
        e.currentTarget.getAttribute("role") === "listbox" && e.target.id && e.target.id !== id)

    return (
      <Dropdown
        id={id}
        className={disabled ? "disabled" : ""} fluid text={breadcrum}
        open={open}
        onOpen={() => this.setOpen(true)}
        onClose={(e) => this.setOpen(isKeepOpen(e))}>

        <Dropdown.Menu scrolling={false}>
          {withSearch &&
          <Input
            className='search'
            placerholder='Search...'
            onClick={(e) => {e.stopPropagation()}}
            onChange={(e: Event) => this.onSearchChange(e.target.value)} />}

          {(allowSelectNone || allowSelectAll) &&
          <Dropdown.Header>
            <div>
              {allowSelectAll && <span className="all" onClick={e => this.allNone(true)}><FormattedMessage id='indicators.filters.select_all' defaultMessage="Select All"/></span>}
              {allowSelectNone && allowSelectAll && <span> | </span>}
              {allowSelectNone && <span className="none" onClick={e => this.allNone(false)}><FormattedMessage id='indicators.filters.select_none' defaultMessage="Select None"/></span>}
            </div>
          </Dropdown.Header>}
          <Dropdown.Divider/>

          <Dropdown.Menu scrolling className="filter options">
            {
              options.map(o => {
                const isChecked = selected.indexOf(o.key) > -1
                const isDisabled = (isChecked && !allowDeselect) || (!isChecked && !allowSelect)
                return (
                  <Dropdown.Item
                    id={`${id}_item_${o.key}`}
                    key={o.key} disabled={isDisabled} onClick={e => this.updateSelection(o.key)}>
                    <div className={"checkbox " + (isChecked ? "checked" : "")}/>
                    {o.text}
                  </Dropdown.Item>)
              })}
          </Dropdown.Menu>
          <Dropdown.Divider/>

        </Dropdown.Menu>
      </Dropdown>)
  }
}
