import React from "react";
import axios from 'axios';
import { TextField, Autocomplete } from "@material-ui/core";
import DateAdapter from '@mui/lab/AdapterDateFns';
import { DesktopDatePicker, LocalizationProvider } from "@mui/lab"

class Field extends React.Component {

  renderDateField() {
    return <LocalizationProvider dateAdapter={DateAdapter}>
      <DesktopDatePicker
        label={this.props.label}
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

  renderIdField() {

  }

  renderEnumField() {

  }

  renderTextField() {
    return <TextField>
      label={this.props.label}
      value={this.props.value}
      variant="outlined"
      onChange={this.props.onChange}
      InputProps={{
        readOnly: this.props.readOnly
      }}
    </TextField>
  }

  render()  {
    switch(this.props.type) {
      case "date":
        return this.renderDateField();
      default:
        return this.renderTextField();
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
