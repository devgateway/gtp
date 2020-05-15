import React, {Component} from "react"
import PropTypes from "prop-types"
import {FormattedMessage} from "react-intl"
import {Dropdown} from "semantic-ui-react"
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
  }

  static defaultProps = {
    disabled: false,
    single: false,
    min: 0,
  }

  constructor(props) {
    super(props)
    this.state = {
      open: false,
    }
  }

  setOpen(open) {
    this.setState({ open })
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

  render() {
    const {options, selected, text, disabled, single, min, max} = this.props
    const {open} = this.state

    const breadcrum = DropdownBreadcrumb(options, selected, text, single)
    const allowSelectNone = !single && !min
    const allowSelectAll = !single && (!max || max >= options.length)
    const allowDeselect = !min || (!!min && selected.length > min)
    const allowSelect = single || !max || (!!max && selected.length < max)

    const isKeepOpen = (e: Event) => e && e.currentTarget && e.currentTarget.getAttribute &&
        e.currentTarget.getAttribute("role") === "listbox"

    return (
      <Dropdown
        className={disabled ? "disabled" : ""} fluid text={breadcrum}
        open={open}
        onOpen={() => this.setOpen(true)}
        onClose={(e) => this.setOpen(isKeepOpen(e))}>

        <Dropdown.Menu scrolling={false}>
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
