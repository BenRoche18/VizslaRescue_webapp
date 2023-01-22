import { withStyles, Button, TextField, Grid } from "@material-ui/core";
import Alert from '@mui/material/Alert';
import { DataGrid } from "@material-ui/data-grid"
import React from "react";
import axios from 'axios';
import flatten from 'flat';


const styles = (theme) => ({
  
});

class Workbench extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      query: "",
      records: [],
      columns: [],
      loading: false
    };
  }

  async executeSQL() {
    axios.post("/api/workbench", this.state.query, { headers: {"Content-Type": "text/plain"} })
      .then(res => {
        this.setState({ 
          records: res.data.map((record, i) => ({...flatten(record), id: i})),
          columns: res.data.length > 0 ? Object.keys(res.data[0]).map((column) => ({field: column, width: 200 })) : [],
          loading: false,
          error: undefined
        })
      })
      .catch(err => {
          this.setState({
            error: err.response.data,
            loading: false
          })
      })
  }

  render() {
    return <Grid container spacing={2}>
      <Grid item xs={12}>
        <TextField
          label="SQL Query"
          multiline
          minRows={4}
          variant="outlined"
          value={this.state.query}
          fullWidth
          onChange={(event) => this.setState({query: event.target.value})}
        />
      </Grid>
      <Grid item xs={12}>
        <Button
          color="primary"
          variant="contained" 
          onClick={() => this.setState({loading: true}, () => this.executeSQL())}
        >
          Execute SQL
        </Button>
      </Grid>
      {this.state.error && <Grid item xs={12}>
        <Alert
          severity="error"
        >
          { this.state.error.message }
        </Alert>
      </Grid>}
      <Grid item xs={12}>
        <DataGrid
          rows={this.state.records}
          columns={this.state.columns}
          loading={this.state.loading}
          autoHeight
        />
      </Grid>
    </Grid>;
  }
}

export default withStyles(styles)(Workbench);