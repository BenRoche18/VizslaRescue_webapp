import React from "react";
import axios from 'axios';
import { TextField, Autocomplete } from "@material-ui/core";

class RecordAutofill extends React.Component {
  state = {
    options: [],
    search: null
  }

  async updateOptions() {
    const params = {
      page: 0,
      size: 20,
      sortKeys: "name;asc",
      filters: "name startsWith " + this.state.search
    }

    axios.get(this.props.api, { params })
    .then(res => {
      this.setState({ options: res.data.content })
    })
  }

  render() {
    return <Autocomplete
      options={this.state.options}
      getOptionLabel={(options) => options[this.props.field]}
      value={this.state.search}
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
          label={this.props.label}
          variant="outlined"
        />
      }
    />
  }
}

export default RecordAutofill;