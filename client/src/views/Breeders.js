import { Button } from "@material-ui/core";
import React from "react";
import ListView from "../components/ListView";
import RecordView from "../components/RecordView";

class Breeders extends React.Component {
  state = {
    record: null
  }

  columns = [
    {
      field: "id",
      headerName: "ID",
      width: 100,
      type: 'number',
      renderCell: (cell) => <Button onClick={() => this.setState({ record: { id: cell.row.id } })}>
        {cell.row.id}
      </Button>
    },
    { 
      field: "names", 
      headerName: "Names", 
      width: 500,
      section: "General Information"
    },
    { 
      field: "additionalDetails", 
      headerName: "Additional Details", 
      width: 500,
      hide: true,
      multiline: true,
      section: "General Information"
    }
  ]

  render() {
    if(this.state.record)
    {
      return <RecordView
        api="/api/breeders"
        columns={this.columns}
        record={this.state.record}
        onClose={() => this.setState({
          record: null
        })}
      />
    }
    else
    {
      return <ListView
        api="/api/breeders"
        columns={this.columns}
        onCreate={() => this.setState({
          record: {}
        })}
      />
    }
  }
}

export default Breeders;