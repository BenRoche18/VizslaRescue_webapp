import React from "react";
import axios from 'axios';
import { TextField, Checkbox, FormControlLabel } from "@material-ui/core";
import { Autocomplete } from '@material-ui/lab';
import DateAdapter from '@mui/lab/AdapterDateFns';
import { DesktopDatePicker, LocalizationProvider } from "@mui/lab"

class Field extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      options: [],
      search: undefined
    }
  }

  async updateOptions() {
    const params = {
      page: 0,
      size: 20,
      sortKeys: "names;asc",
      filters: "names contains " + this.state.search
    }

    axios.get("/api/" + this.props.fieldMetadata.technicalName + "s", { params })
    .then(res => {
      this.setState({ options: res.data.content })
    })
  }

  renderEntityField() {
    const entity = this.props.metadata.find(it => it.technicalName = this.props.fieldMetadata.entity)
    const renderLabel = (it => it ? it[entity.alternativeKey] + " (" + it.id + ")" : undefined)

    if(this.props.readOnly) {
      return <TextField
        value={renderLabel(this.props.value)}
        label={this.props.fieldMetadata.businessName}
        variant="outlined"
        readOnly
      />
    } else {
      return <Autocomplete
        options={this.state.options}
        getOptionLabel={renderLabel}
        getOptionSelected={renderLabel}
        value={this.props.value}
        onInputChange={(event, value) => {
          if(value)
          {
            this.setState({ search: value }, this.updateOptions.bind(this))
          }
        }}
        onChange={(event, value) => {
          this.props.onChange(value)
        }}
        renderInput={(params) =>
          <TextField
            {...params}
            label={this.props.fieldMetadata.businessName}
            variant="outlined"
          />
        }
      />
    }
  }

  renderDateField() {
    return <LocalizationProvider dateAdapter={DateAdapter}>
      <DesktopDatePicker
        label={this.props.fieldMetadata.businessName}
        value={this.props.value}
        inputFormat="MM/dd/yyyy"
        onChange={this.props.onChange}
        readOnly={this.props.readOnly}
        renderInput={(params) => <TextField 
          {...params} 
          variant="outlined"
        />}
      />
    </LocalizationProvider>
  }

  renderBooleanField() {
    return <FormControlLabel
      label={this.props.fieldMetadata.businessName}
      control={
        <Checkbox
          disabled={this.props.readOnly}
          checked={this.props.value}
          onChange={(event, value) => this.props.onChange(value)}
        />
      }
    />
  }

  renderEnumField() {

  }

  renderTextField() {
    return <TextField
      label={this.props.fieldMetadata.businessName}
      value={this.props.value}
      variant="outlined"
      onChange={(event) => this.props.onChange(event.target.value)}
      InputProps={{
        readOnly: this.props.readOnly
      }} 
       multiline={this.props.fieldMetadata.multiline}
    />
  }

  render()  {
    switch(this.props.fieldMetadata.type) {
      case "date":
        return this.renderDateField();
      case "text":
        return this.renderTextField();
      case "boolean":
        return this.renderBooleanField();
      case "entity":
        return this.renderEntityField();
      default:
        return null;
    }
  }
}

export default Field;
