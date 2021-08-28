import { Box, Button, withStyles } from "@material-ui/core";
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridToolbarColumnsButton, GridToolbarFilterButton, GridToolbarDensitySelector } from "@material-ui/data-grid"
import React from "react";
import axios from 'axios';
import Dog from "./Dog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit } from "@fortawesome/free-solid-svg-icons";

const styles = (theme) => ({
  gridContainer: {
    width: "100%",
  },
});

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

  columns = [
    { 
      field: "name", 
      headerName: "Name", 
      width: 250, 
      renderCell: (cell) => <Dog name={cell.row.name} id={cell.row.id}/>
    },
    { 
      field: "breeders",
      headerName: "Breeders",
      width: 250
    },
    { 
      field: "gender",
      headerName: "Gender",
      width: 150
    },
    { 
      field: "dob", 
      headerName: "Date of Birth", 
      width: 250,
      renderCell: (cell) => new Date(cell.row.dob).toLocaleDateString()
    }
  ]

  componentDidMount() {
    this.fetchDogs();
  }

  async fetchDogs() {
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

  renderToolbar() {
    return <GridToolbarContainer>
      <Box display="flex" style={{ width: "100%" }}>
        <Box>
          <Button 
            color="primary" 
            startIcon={<FontAwesomeIcon icon={faEdit} size="md" />}
            size="small"
          >
            Create
          </Button>
        </Box>
        <Box style={{ marginLeft: "auto" }}>
          <GridToolbarColumnsButton />
          <GridToolbarFilterButton />
          <GridToolbarDensitySelector />
          <GridToolbarExport csvOptions={{ allColumns: true, fileName: "export" }}/>
        </Box>
      </Box>
    </GridToolbarContainer>
  }

  render() {
    const { classes } = this.props;

    return <div className={classes.gridContainer}>
      <DataGrid
        columns={this.columns}
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
        components={{ Toolbar: this.renderToolbar }}
      />
      { this.state.dialog }
    </div>;
  }
}

export default withStyles(styles)(Dogs);