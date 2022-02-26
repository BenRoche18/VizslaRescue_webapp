import { Box, Button, withStyles, Typography } from "@material-ui/core";
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridToolbarColumnsButton, GridToolbarFilterButton, GridToolbarDensitySelector } from "@material-ui/data-grid"
import React from "react";
import axios from 'axios';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faEye } from "@fortawesome/free-solid-svg-icons";
import flatten from 'flat';
import { withRouter } from 'react-router-dom'

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
      records: [],
      selectedRecordId: undefined
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

    axios.get("/api/" + this.props.entity, { params })
      .then(res => {
        this.setState({ 
          records: res.data.content.map((record) => flatten(record)),
          rowCount: res.data.totalElements,
          loading: false 
        })
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
            onClick={() => this.props.history.push({ 
              search: "?create"
            })}
          >
            Create
          </Button>
          {this.state.selectedRecordId && <Button
            color="primary" 
            startIcon={<FontAwesomeIcon icon={faEye} size="md" />}
            size="small"
            onClick={() => this.props.history.push({ 
              pathname: this.props.location.pathname + "/" + this.state.selectedRecordId
            })}
          >
            View
          </Button>}
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
    const entityMetadata = this.props.metadata.find(it => it.technicalName === this.props.entity)

    if(entityMetadata) {
      const columns = entityMetadata.fields.map((fieldMetadata) => {
        return {
          field: fieldMetadata.technicalName,
          headerName: fieldMetadata.businessName,
          type: fieldMetadata.type,
          width: fieldMetadata.width
        }
      })
  
      return <div className={classes.gridContainer}>
        <DataGrid
          columns={columns}
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
          onSelectionModelChange={(selections) => this.setState({ selectedRecordId: selections.length ? selections[0] : undefined })}
        />
      </div>;
    } else {
      return <Typography>
      Requested Entity ({this.props.entity}) not found
    </Typography>
    }
  }
}

export default withRouter(withStyles(styles)(ListView));