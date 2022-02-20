import { Box, Button, withStyles } from "@material-ui/core";
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridToolbarColumnsButton, GridToolbarFilterButton, GridToolbarDensitySelector } from "@material-ui/data-grid"
import React from "react";
import axios from 'axios';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import flatten from 'flat';

const styles = (theme) => ({
  gridContainer: {
    width: "100%",
  },
});

class ListView extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      rowCount: 0,
      page: 0,
      size: 25,
      loading: true,
      sortKeys: [],
      filter: {
        items: []
      },
      records: []
    };
  }

  componentDidMount() {
    this.fetchRecords();
  }

  async fetchRecords() {
    const params = {
      page: this.state.page,
      size: this.state.size,
      sortKeys: this.state.sortKeys.map((key) => key.field + ";" + key.sort).join(","),
      filters: this.state.filter.items.filter((item) => item.value != null)
        .map((filter) => filter.columnField + " " + filter.operatorValue + " " + filter.value)
        .join(",")
    }

    axios.get(this.props.api, { params })
      .then(res => {
        this.setState({ 
          records: res.data.content.map((record) => flatten(record)),
          rowCount: res.data.totalElements,
          loading: false })
      })
      .catch(err => {
          this.setState({
          error: err.response.data,
          loading: false
          })
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
            onClick={this.props.onCreate}
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
        columns={this.props.columns}
        rows={this.state.records}
        paginationMode="server"
        sortingMode="server"
        filterMode="server"
        autoHeight
        loading={this.state.loading}
        onPageChange={(page) => this.setState({ page: page, loading: true }, () => this.fetchRecords())}
        onPageSizeChange={(size) => this.setState({ pageSize: size, loading: true }, () => this.fetchRecords())}
        rowCount={this.state.rowCount}
        pageSize={this.state.size}
        page={this.state.page}
        onSortModelChange={(model) => this.setState({ sortKeys: model, loading: true }, () => this.fetchRecords())}
        onFilterModelChange={(model) => this.setState({ filter: model, loading: true }, () => this.fetchRecords())}
        components={{ Toolbar: this.renderToolbar.bind(this) }}
        error={this.state.error}
      />
    </div>;
  }
}

export default withStyles(styles)(ListView);