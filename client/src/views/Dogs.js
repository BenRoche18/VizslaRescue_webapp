import { withStyles } from "@material-ui/core";
import { DataGrid } from "@material-ui/data-grid"
import React from "react";
import axios from 'axios';

const styles = (theme) => ({
  gridContainer: {
    width: "100%",
  },
});

const columns = [
  { field: "name", headerName: "Name", width: 250 },
  { field: "breeders", headerName: "Breeders", width: 250},
  { field: "gender", headerName: "Gender", width: 150},
  { field: "dob", headerName: "Date of Birth", width: 250}
]

class Dogs extends React.Component {
  state = {
    rowCount: 0,
    pageSize: 25,
    page: 0,
    loading: true,
    dogs: [],
    sortKeys: [],
    filter: {
      items: []
    }
  }

  componentDidMount() {
    this.fetchDogs();
  }

  async fetchDogs() {
    console.log(this.state.filter)

    const params = {
      page: this.state.page,
      pageSize: this.state.pageSize,
      sortKeys: this.state.sortKeys.map((key) => key.field + ";" + key.sort).join(","),
      filters: this.state.filter.items.filter((item) => item.value != null)
        .map((filter) => filter.columnField + " " + filter.operatorValue + " '" + filter.value + "'")
        .join(",")
    }

    axios.get("/api/dogs", { params })
      .then(res => {
        this.setState({ 
          dogs: res.data.content,
          rowCount: res.data.totalElements,
          loading: false })
      })
  }

  render() {
    const { classes } = this.props;

    return <div className={classes.gridContainer}>
      <DataGrid
        columns={columns}
        rows={this.state.dogs}
        paginationMode="server"
        sortingMode="server"
        filterMode="server"
        autoHeight
        loading={this.state.loading}
        onPageChange={(page) => this.setState({ page: page, loading: true }, () => this.fetchDogs())}
        onPageSizeChange={(size) => this.setState({ pageSize: size, loading: true }, () => this.fetchDogs())}
        rowCount={this.state.rowCount}
        pageSize={this.state.pageSize}
        page={this.state.page}
        onSortModelChange={(model) => this.setState({ sortKeys: model, loading: true }, () => this.fetchDogs())}
        onFilterModelChange={(model) => this.setState({ filter: model, loading: true }, () => this.fetchDogs())}
      />
    </div>;
  }
}

export default withStyles(styles)(Dogs);