import PropTypes from "prop-types"
import React, {Component} from "react"
import {FormattedMessage} from "react-intl"
import {Dropdown, Input, Popup, Tab} from "semantic-ui-react"
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
    withTooltips: PropTypes.bool,
  }

  static defaultProps = {
    disabled: false,
    single: false,
    min: 0,
    withSearch: false,
    withTooltips: false,
  }

  constructor(props) {
    super(props)
    const {groupedOptions} = props
    this.state = {
      open: false,
      id: `dropdown_${Math.random()}`,
      isLocalStateChange: false,
      isGrouped: groupedOptions && groupedOptions.groups && !!groupedOptions.groups.size,
      activeGroupIndex: null,
    }
    this.updateSelection = this.updateSelection.bind(this)
    this.setActiveGroupIndex = this.setActiveGroupIndex.bind(this)
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

  setActiveGroupIndex(activeGroupIndex) {
    this.setFilterState({
      activeGroupIndex
    })
  }

  render() {
    const {selected, text, description, disabled, single, min, max, withSearch, withTooltips} = this.props
    const {open, id, optionsByKey, defaultOptions, options, isGrouped, activeGroupIndex} = this.state
    const groups: Map = this.props.groupedOptions && this.props.groupedOptions.groups

    const breadcrum = DropdownBreadcrumb(defaultOptions, selected, text, single, withTooltips)
    const allowSelectNone = !single && !min
    const allowSelectAll = !single && (!max || max >= options.length)
    const allowDeselect = !min || (!!min && selected.length > min)
    const allowSelect = single || !max || (!!max && selected.length < max)

    const isKeepOpen = (e: Event) => !!(e && e.currentTarget && e.currentTarget.getAttribute &&
        e.currentTarget.getAttribute("role") === "listbox" && e.target.id && e.target.id !== id)

    const filterOptions = FilterOptions(id, selected, this.updateSelection, allowSelect, allowDeselect, withTooltips)

    return (
      <Dropdown
        id={id}
        className={disabled ? "disabled" : ""} fluid text={breadcrum}
        icon={<div className="png exportable icon icon-down-arrow" />}
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

          {isGrouped ?
            GroupedFilterOptions(groups, optionsByKey, selected, filterOptions, activeGroupIndex, this.setActiveGroupIndex)
            : filterOptions(options)}

        </Dropdown.Menu>
      </Dropdown>)
  }
}

const GroupedFilterOptions = (groups: Map<String, Set<number>>, optionsByKey, selected, filterOptions,
  activeIndex, setActiveGroupIndex) => {
  const optionsGroups = Array.from(groups.keys()).sort().map((groupName, index) => {
    const options = groups.get(groupName).map(key => optionsByKey.get(key)).filter(o => !!o)
    if (!options.length) {
      return null
    }
    const hasSelection = options.some(o => selected.includes(o.key))
    if (hasSelection && activeIndex === null) {
      activeIndex = index
    }
    return {
      menuItem: groupName,
      pane:
        <Tab.Pane key={index}>
          <Dropdown.Divider/>
          <Dropdown.Menu>
            {filterOptions(options)}
          </Dropdown.Menu>
        </Tab.Pane>
    }
  }).filter(entry => entry)
  if (activeIndex >= optionsGroups.length) {
    activeIndex = 0
  }
  return (
    <Tab
      activeIndex={activeIndex || 0}
      renderActiveOnly={false}
      onTabChange={(e, {activeIndex}) => {
        e.stopPropagation()
        setActiveGroupIndex(activeIndex)
      }}
      panes={optionsGroups}
      className="filter-group"/>
  )
}

const FilterOptions = (id, selected, updateSelection, allowSelect, allowDeselect, withTooltips) => (options) => (
  <>
    <Dropdown.Menu scrolling className="filter options">
      {
        options.map(o => {
          const isChecked = selected.indexOf(o.key) > -1
          const isDisabled = (isChecked && !allowDeselect) || (!isChecked && !allowSelect)
          const di = (
            <Dropdown.Item
              id={`${id}_item_${o.key}`}
              className="filter-item-tooltip"
              key={o.key} disabled={isDisabled}
              onClick={e => isDisabled ? e.stopPropagation() : updateSelection(o.key)}>

              <div className={"checkbox " + (isChecked ? "checked" : "")}/>
              {o.text}

            </Dropdown.Item>)
          return withTooltips ?
            (<Popup key={o.key} content={o.text} trigger={di} disabled={false} hoverable size="mini" />) : di
        })}
    </Dropdown.Menu>
    <Dropdown.Divider/>
  </>
)
