import { Box, Button, withStyles } from "@material-ui/core";
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridToolbarColumnsButton, GridToolbarFilterButton, GridToolbarDensitySelector } from "@material-ui/data-grid"
import React from "react";
import axios from 'axios';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import Breeder from "../components/Breeder";

const styles = (theme) => ({
  gridContainer: {
    width: "100%",
  },
});

class Breeders extends React.Component {
  state = {
    rowCount: 0,
    pageSize: 25,
    page: 0,
    loading: true,
    breeders: [],
    sortKeys: [],
    filter: {
      items: []
    }
  }

  columns = [
    { 
      field: "names", 
      headerName: "Names", 
      width: 500, 
      renderCell: (cell) => <Breeder name={cell.row.names} id={cell.row.id}/>
    }
  ]

  componentDidMount() {
    this.fetchBreeders();
  }

  async fetchBreeders() {
    const params = {
      page: this.state.page,
      pageSize: this.state.pageSize,
      sortKeys: this.state.sortKeys.map((key) => key.field + ";" + key.sort).join(","),
      filters: this.state.filter.items.filter((item) => item.value != null)
        .map((filter) => filter.columnField + " " + filter.operatorValue + " '" + filter.value + "'")
        .join(",")
    }

    axios.get("/api/breeders", { params })
      .then(res => {
        this.setState({ 
          breeders: res.data.content,
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
        rows={this.state.breeders}
        paginationMode="server"
        sortingMode="server"
        filterMode="server"
        autoHeight
        loading={this.state.loading}
        onPageChange={(page) => this.setState({ page: page, loading: true }, () => this.fetchBreeders())}
        onPageSizeChange={(size) => this.setState({ pageSize: size, loading: true }, () => this.fetchBreeders())}
        rowCount={this.state.rowCount}
        pageSize={this.state.pageSize}
        page={this.state.page}
        onSortModelChange={(model) => this.setState({ sortKeys: model, loading: true }, () => this.fetchBreeders())}
        onFilterModelChange={(model) => this.setState({ filter: model, loading: true }, () => this.fetchBreeders())}
        components={{ Toolbar: this.renderToolbar }}
      />
      { this.state.dialog }
    </div>;
  }
}

export default withStyles(styles)(Breeders);