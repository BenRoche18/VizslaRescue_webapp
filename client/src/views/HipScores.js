import { Button } from "@material-ui/core";
import React from "react";
import ListView from "../components/ListView";
import RecordView from "../components/RecordView";

class HipScores extends React.Component {
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
      field: "brs", 
      headerName: "BRS", 
      width: 500,
      section: "General Information"
    },
    { 
      field: "date",
      headerName: "Date",
      width: 200,
      type: "date",
      section: "General Information"
    },
    { 
      field: "additionalDetails", 
      headerName: "Additional Details", 
      width: 500,
      hide: true,
      section: "General Information"
    }
  ]

  render() {
    if(this.state.record)
    {
      return <RecordView
        api="/api/hip_scores"
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
        api="/api/hip_scores"
        columns={this.columns}
        onCreate={() => this.setState({
          record: {}
        })}
      />
    }
  }
}

export default HipScores;
