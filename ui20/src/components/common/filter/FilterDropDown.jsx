import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Dropdown, Input} from "semantic-ui-react"
import DropdownBreadcrumb from "./DropdownBreadcrubmb"
import FilterGroupedOptions from "./FilterGroupedOptions"

export default class FilterDropDown extends Component {
  static propTypes = {
    options: PropTypes.array.isRequired,
    groupedOptions: PropTypes.instanceOf(FilterGroupedOptions),
    selected: PropTypes.array.isRequired,
    onChange: PropTypes.func.isRequired,
    text: PropTypes.any.isRequired,
    description: PropTypes.any,
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
    const {groupedOptions} = props
    this.state = {
      open: false,
      id: `dropdown_${Math.random()}`,
      isLocalStateChange: false,
      isGrouped: groupedOptions && groupedOptions.groups && !!groupedOptions.groups.size
    }
    this.updateSelection = this.updateSelection.bind(this)
  }

  static getDerivedStateFromProps(props, state) {
    const {isLocalStateChange, isGrouped} = state
    if (isLocalStateChange) {
      return {
        isLocalStateChange: false
      }
    }

    const {options} = props
    options.forEach(o => o.lowerText = `${o.text}`.toLowerCase())
    return {
      options,
      optionsByKey: isGrouped && FilterDropDown.optionsByKey(options),
      defaultOptions: options
    }
  }

  static optionsByKey(defaultOptions) {
    return defaultOptions.reduce((map: Map, o) => map.set(o.key, o), new Map())
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
    const {onChange} = this.props
    const {defaultOptions} = this.state
    if (flag === false) {
      onChange([])
    } else {
      onChange(defaultOptions.map(o => o.key))
    }
  }

  onSearchChange(value) {
    const {defaultOptions, isGrouped} = this.state
    value = value && value.trim().toLowerCase()

    const options = !value ? defaultOptions : defaultOptions.filter(({lowerText}) => lowerText.includes(value))
    this.setFilterState({
      options,
      optionsByKey: isGrouped && FilterDropDown.optionsByKey(options),
    })
  }

  render() {
    const {selected, text, description, disabled, single, min, max, withSearch} = this.props
    const {open, id, optionsByKey, defaultOptions, options, isGrouped} = this.state
    const groups: Map = this.props.groupedOptions && this.props.groupedOptions.groups

    const breadcrum = DropdownBreadcrumb(defaultOptions, selected, text, single)
    const allowSelectNone = !single && !min
    const allowSelectAll = !single && (!max || max >= options.length)
    const allowDeselect = !min || (!!min && selected.length > min)
    const allowSelect = single || !max || (!!max && selected.length < max)

    const isKeepOpen = (e: Event) => !!(e && e.currentTarget && e.currentTarget.getAttribute &&
        e.currentTarget.getAttribute("role") === "listbox" && e.target.id && e.target.id !== id)

    const filterOptions = FilterOptions(id, selected, this.updateSelection, allowSelect, allowDeselect)

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
          {description &&
          <Dropdown.Header>
            <div className="description">{description}</div>
          </Dropdown.Header>}
          <Dropdown.Divider/>

          {isGrouped ? GroupedFilterOptions(groups, optionsByKey, filterOptions) : filterOptions(options)}

        </Dropdown.Menu>
      </Dropdown>)
  }
}

const GroupedFilterOptions = (groups: Map<String, Set<number>>, optionsByKey, filterOptions) => {
  return Array.from(groups.keys()).sort().map(groupName => {
    const options = groups.get(groupName).map(key => optionsByKey.get(key)).filter(o => !!o)
    if (!options.length) {
      return null
    }
    return (
      <>
        <Dropdown.Header className="group-header">
          <snap>{groupName}</snap>
        </Dropdown.Header>
        <Dropdown.Divider/>

        {filterOptions(options)}
      </>
    )
  })
}

const FilterOptions = (id, selected, updateSelection, allowSelect, allowDeselect) => (options) => (
  <>
    <Dropdown.Menu scrolling className="filter options">
      {
        options.map(o => {
          const isChecked = selected.indexOf(o.key) > -1
          const isDisabled = (isChecked && !allowDeselect) || (!isChecked && !allowSelect)
          return (
            <Dropdown.Item
              id={`${id}_item_${o.key}`}
              key={o.key} disabled={isDisabled} onClick={e => updateSelection(o.key)}>
              <div className={"checkbox " + (isChecked ? "checked" : "")}/>
              {o.text}
            </Dropdown.Item>)
        })}
    </Dropdown.Menu>
    <Dropdown.Divider/>
  </>
)
