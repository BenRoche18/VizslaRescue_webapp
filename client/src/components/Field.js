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
      filters: "names startsWith " + this.state.search
    }

    axios.get("/api/" + this.props.metadata.technicalName + "s", { params })
    .then(res => {
      this.setState({ options: res.data.content })
    })
  }

  renderEntityField() {
    return <Autocomplete
      options={this.state.options}
      getOptionLabel={(options) => options[this.props.field]}
      value={this.props.value}
      onInputChange={(event, value) => {
        if(value)
        {
          this.setState({ search: value }, this.updateOptions.bind(this))
        }
      }}
      onChange={(event, value) => { 
        if(value)
        {
          this.setState({ search: value }, this.props.onChange(value)) }
        }
      }
      renderInput={(params) =>
        <TextField
          {...params}
          label={this.props.metadata.businessName}
          variant="outlined"
        />
      }
    />
  }

  renderDateField() {
    return <LocalizationProvider dateAdapter={DateAdapter}>
      <DesktopDatePicker
        label={this.props.metadata.businessName}
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
      label={this.props.metadata.businessName}
      control={
        <Checkbox 
          value={this.props.value}
          onChange={this.props.onChange}
          InputProps={{
            readOnly: this.props.readOnly
          }}
        />
      }
    />
  }

  renderEnumField() {

  }

  renderTextField() {
    return <TextField
      label={this.props.metadata.businessName}
      value={this.props.value}
      variant="outlined"
      onChange={(event) => this.props.onChange(event.target.value)}
      InputProps={{
        readOnly: this.props.readOnly
      }} 
       multiline={this.props.metadata.multiline}
    />
  }

  render()  {
    switch(this.props.metadata.type) {
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

  // renderSection(label, fields) {
  //   const { classes } = this.props;

  //   return <div>
  //     <Typography variant="overline">
  //       {label}
  //     </Typography>
  //     <Divider className={classes.formDivider} />
  //     <Grid container spacing={2} className={classes.formSection}>
  //       { fields.map((field) => this.renderField(field)) }
  //     </Grid>
  //   </div>
  // }

  // renderField(field) {
  //   console.log(field)

  //   const component = field.type != "date"
  //     ? <TextField
  //       label={field.headerName}
  //       select={field.options}
  //       value={this.state.record[field.field]}
  //       variant="outlined"
  //       onChange={(event) => this.updateRecord(field.field, event.target.value)}
  //       InputProps={{
  //         readOnly: !this.state.canEdit || field.readOnly
  //       }}
  //       type={field.type}
  //       multiline={field.multiline}
  //     >
  //       { field.options && field.options.map((option) => <MenuItem key={option} value={option}>
  //         {option}
  //       </MenuItem>) }
  //     </TextField>
  //     : <LocalizationProvider dateAdapter={DateAdapter}>
  //       <DesktopDatePicker
  //         label={field.headerName}
  //         inputFormat="MM/dd/yyyy"
  //         value={this.state.record[field.field]}
  //         onChange={(value) => this.updateRecord(field.field, value)}
  //         readOnly={!this.state.canEdit || field.readOnly}
  //         renderInput={(params) => <TextField 
  //           {...params} 
  //           variant="outlined"
  //         />}
  //       />
  //     </LocalizationProvider>

  //   return <Grid item xs={12}>
  //     { component }
  //   </Grid>
  // }
